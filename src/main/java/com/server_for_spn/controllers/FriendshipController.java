package com.server_for_spn.controllers;

import com.server_for_spn.entity.FriendShipRequest;
import com.server_for_spn.entity.Friends;
import com.server_for_spn.entity.User;
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



    @PostMapping("/newFriendShipRequest")
    private ResponseEntity<String> newFriendShipRequest(@RequestParam("from")Long idFrom,
                                                        @RequestParam("to") Long idTo,
                                                        Authentication authentication){

        if(!userService.checkExistenceById(idFrom) || !userService.checkExistenceById(idTo)){
            return new ResponseEntity<>("CONFLICT", HttpStatus.CONFLICT);
        }

        User userFrom = userService.findOne(idFrom);
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
            Friends friends = new Friends();
            friends.setSide1(acceptor);
            friends.setSide2(requester);
            friendShipService.save(friends);
            acceptedFriendshipNotifier.sendNotification(acceptor, requester);
            friendShipRequestService.delete(friendShipRequest.getId());
    }
        return new ResponseEntity<>( "OK", HttpStatus.ACCEPTED);
    }
}
