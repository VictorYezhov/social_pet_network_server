package com.server_for_spn.controllers;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.server_for_spn.dto.FriendInfo;
import com.server_for_spn.entity.*;
import com.server_for_spn.service.FriendShipRequestService;
import com.server_for_spn.service.FriendShipService;
import com.server_for_spn.service.NotificationService;
import com.server_for_spn.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.jws.soap.SOAPBinding;
import java.sql.Timestamp;
import java.util.*;

/**
 * Created by Victor on 05.08.2018.
 */
@RestController
public class FriendshipController {



    @Autowired
    private UserService userService;

    @Autowired
    private FriendShipService friendShipService;

    @Autowired
    private FriendShipRequestService friendShipRequestService;

    @Autowired
    @Qualifier("friendshipRequestNotifier")
    private NotificationService friendshipRequestNotifier;

    @Autowired
    @Qualifier("friendshipAcceptedNotifier")
    private NotificationService acceptedFriendshipNotifier;


    /**
     * Add to friends Request functionality
     * Send`s push notification to user whom request is addressed
     * @param idFrom - requester id
     * @param idTo - acceptor id
     * @param authentication  spring provided authentication details
     * @return
     */
    @PostMapping("/newFriendShipRequest")
    private ResponseEntity<String> newFriendShipRequest(@RequestParam("from")Long idFrom,
                                                        @RequestParam("to") Long idTo,
                                                        Authentication authentication){

        if(!userService.checkExistenceById(idFrom) || !userService.checkExistenceById(idTo)){
            return new ResponseEntity<>("CONFLICT", HttpStatus.CONFLICT);
        }

        User userFrom = userService.findOne(idFrom);
        userFrom.getUserState().setLastActiveTime(new Timestamp(System.currentTimeMillis()));
        if(!userFrom.getEmail().equals(authentication.getPrincipal().toString())){
            return new ResponseEntity<>("CONFLICT", HttpStatus.CONFLICT);
        }
        User userTo = userService.findOne(idTo);

        System.out.println(userTo);


        if(!friendShipService.checkExistence(userFrom, userTo)
                && !friendShipRequestService.checkExistence(userFrom,userTo)){
            FriendShipRequest friendShipRequest = new FriendShipRequest();
            friendShipRequest.setRequesterId(userFrom.getId());
            friendShipRequest.setAcceptorId(userTo.getId());
            friendShipRequestService.save(friendShipRequest);
            friendshipRequestNotifier.sendNotification(userFrom,userTo);
            return new ResponseEntity<>( "OK", HttpStatus.ACCEPTED);
        }
        return new ResponseEntity<>("CONFLICT", HttpStatus.CONFLICT);
    }


    /**
     * Acceptance of friendship function
     * Sends push notification To requester of friendship
     * @param acceptorId - person who confirms friendship
     * @param requesterId - person  who requested friendship
     * @param state - confirmation or rejection of friendship request
     * @param authentication spring provided authentication details
     * @return
     */

    @PostMapping("/acceptFriendshipInvitation")
    private ResponseEntity<String> acceptFriendshipInvitation(@RequestParam("acceptor") Long acceptorId,
                                                              @RequestParam("requester")Long requesterId,
                                                              @RequestParam("state") boolean state,
                                                              Authentication authentication){
        if(state){
            FriendShipRequest friendShipRequest = friendShipRequestService.findByAcceptorAndRequester(acceptorId, requesterId);
            User acceptor = userService.findOne(friendShipRequest.getAcceptorId());
            if(!authentication.getPrincipal().toString().equals(acceptor.getEmail())){
                return new ResponseEntity<>( "CONFLICT", HttpStatus.CONFLICT);
            }
            User requester = userService.findOne(friendShipRequest.getRequesterId());
            acceptor.getUserState().setLastActiveTime(new Timestamp(System.currentTimeMillis()));
            Friends friends = new Friends();
            friends.setSide1(acceptor);
            friends.setSide2(requester);
            friendShipService.save(friends);
            acceptedFriendshipNotifier.sendNotification(acceptor, requester);
            friendShipRequestService.delete(friendShipRequest.getId());
    }
        return new ResponseEntity<>( "OK", HttpStatus.ACCEPTED);
    }



    @PostMapping("/getUsersFriends")
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private ResponseEntity<List<FriendInfo>> getUsetFriendsInfo(@RequestParam("id")Long id,
                                                               Authentication authentication){
        User requester = userService.findOne(id);
        if(!requester.getEmail().equals(authentication.getPrincipal().toString())){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        List<FriendInfo> friendsInfo = new ArrayList<>();


        FriendInfo info;
        for (Friends f: requester.getMyFriends()) {
            info = new FriendInfo();
            User friend = getFriend(f, requester);
            info.setName(friend.getName());
            info.setSurname(friend.getFamilyName());
            info.setId(friend.getId());
            info.setLastActiveTime(friend.getUserState().getLastActiveTime());
            Pet pet = getCurrentPetChoise(friend);
            if(pet != null) {
                info.setPetName(pet.getName());
                info.setPetBreedName(pet.getBreed().getName());
            }

            friendsInfo.add(info);
        }

        return new ResponseEntity<>(friendsInfo, HttpStatus.OK);
    }


    private User getFriend(Friends friends, User requester){

        if(requester.getId().equals(friends.getSide1().getId()))
            return friends.getSide2();
        else
            return friends.getSide1();

    }

    private Pet getCurrentPetChoise(User user){

        for (Pet p:
             user.getPetList()) {
            if (p.getId().equals(user.getUserState().getCurrentPetChoose())) {
                return p;
            }
        }
        return null;
    }




}
