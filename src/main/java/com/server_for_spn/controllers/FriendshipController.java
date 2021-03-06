package com.server_for_spn.controllers;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.server_for_spn.dto.FriendInfo;
import com.server_for_spn.dto.InfoAboutUserFriendShipRequest;
import com.server_for_spn.entity.*;
import com.server_for_spn.service.FriendShipRequestService;
import com.server_for_spn.service.FriendShipService;
import com.server_for_spn.fcm_notifications.NotificationService;
import com.server_for_spn.service.PetService;
import com.server_for_spn.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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
    private PetService petService;

    @Autowired
    @Qualifier("friendshipRequestNotifier")
    private NotificationService friendshipRequestNotifier;

    @Autowired
    @Qualifier("deleteFriendNotifier")
    private NotificationService deleteFriendNotifier;

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
            userTo.additionalField = friendShipRequest.getId();
            friendshipRequestNotifier.sendNotification(userFrom,userTo);
            return new ResponseEntity<>( "OK", HttpStatus.ACCEPTED);
        }
        return new ResponseEntity<>("CONFLICT", HttpStatus.CONFLICT);
    }

    @PostMapping("/persist")
    public ResponseEntity<String> persisted(@RequestParam("requestId") Long id,
                                            @RequestParam("userID") Long userId,
                                            Authentication authentication){
        User requester = userService.findOne(userId);
        if(!authentication.getPrincipal().toString().equals(requester.getEmail())){
            return new ResponseEntity<>("BAD",HttpStatus.BAD_REQUEST);
        }
        FriendShipRequest friendShipRequest = friendShipRequestService.findOne(id);

        if(!requester.getId().equals(friendShipRequest.getAcceptorId())){
            return new ResponseEntity<>("BAD",HttpStatus.BAD_REQUEST);
        }
        friendShipRequest.setPersistedOnClientSide(true);
        friendShipRequestService.update(friendShipRequest);

        return new ResponseEntity<>("OK",HttpStatus.OK);
    }

    @PostMapping("/getUnPersistentFriendShipRequests")
    public ResponseEntity<List<InfoAboutUserFriendShipRequest>> getUnPersistentFriendShipRequests(@RequestParam("id") Long id,
                                                                                                  Authentication authentication){
        User u = userService.findOne(id);
        if(!u.getEmail().equals(authentication.getPrincipal().toString())){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        List<FriendShipRequest> requests = friendShipRequestService.findAllForAcceptor(id);
        return new ResponseEntity<>(convertToInfoAboutUserFriendShipRequest(requests), HttpStatus.OK);
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
    public ResponseEntity<FriendInfo> acceptFriendshipInvitation(@RequestParam("acceptor") Long acceptorId,
                                                              @RequestParam("requester")Long requesterId,
                                                              @RequestParam("state") boolean state,
                                                              Authentication authentication){

        FriendInfo newFriend = new FriendInfo();

        if(state){
            FriendShipRequest friendShipRequest = friendShipRequestService.findByAcceptorAndRequester(acceptorId, requesterId);
            User acceptor = userService.findOne(friendShipRequest.getAcceptorId());
            if(!authentication.getPrincipal().toString().equals(acceptor.getEmail())){
                return new ResponseEntity<>( newFriend, HttpStatus.CONFLICT);
            }
            User requester = userService.findOne(friendShipRequest.getRequesterId());
            Friends friends = new Friends();
            friends.setSide1(acceptor);
            friends.setSide2(requester);
            friendShipService.save(friends);
            acceptedFriendshipNotifier.sendNotification(acceptor, requester);
            friendShipRequestService.delete(friendShipRequest.getId());

            newFriend.setName(requester.getName());
            newFriend.setSurname(requester.getFamilyName());
            newFriend.setId(requester.getId());
            newFriend.setLastActiveTime(requester.getUserState().getLastActiveTime());
            Pet pet = getCurrentPetChoise(requester);
            if(pet != null) {
                newFriend.setPetName(pet.getName());
                newFriend.setPetBreedName(pet.getBreed().getName());
            }
    }
        return new ResponseEntity<>( newFriend, HttpStatus.ACCEPTED);
    }

    @PostMapping("/addToFriendsWhenOneUserFoundPetOfAnotherUser")
    public ResponseEntity<FriendInfo> addToFriendsWhenOneUserFoundPetOfAnotherUser(
            @RequestParam("user_who_found_pet") Long userWhoFoundPetID,
            @RequestParam("user_who_lost_pet") Long userWhoLostPetID){
        User userWhoFoundPet = userService.findOne(userWhoFoundPetID);
        User userWhoLostPet = userService.findOne(userWhoLostPetID);

        Friends friendship = new Friends();
        friendship.setSide1(userWhoFoundPet);
        friendship.setSide2(userWhoLostPet);
        friendShipService.save(friendship);

        acceptedFriendshipNotifier.sendNotification(userWhoFoundPet, userWhoLostPet);

        FriendInfo newFriend = new FriendInfo();

        newFriend.setName(userWhoLostPet.getName());
        newFriend.setSurname(userWhoLostPet.getFamilyName());
        newFriend.setId(userWhoLostPet.getId());
        newFriend.setLastActiveTime(userWhoLostPet.getUserState().getLastActiveTime());
        Pet pet = getCurrentPetChoise(userWhoLostPet);
        if(pet != null) {
            newFriend.setPetName(pet.getName());
            newFriend.setPetBreedName(pet.getBreed().getName());
        }

        return new ResponseEntity<>( newFriend, HttpStatus.ACCEPTED);
    }



    @PostMapping("/getUsersFriends")
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    public ResponseEntity<Set<FriendInfo>> getUsetFriendsInfo(@RequestParam("id")Long id,
                                                               Authentication authentication){
        User requester = userService.findOne(id);
        if(!requester.getEmail().equals(authentication.getPrincipal().toString())){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        Set<FriendInfo> friendsInfo = new HashSet<>();
        FriendInfo info;
        for (Friends f: requester.getFriends()) {
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

        System.out.println("OK");

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

    private List<InfoAboutUserFriendShipRequest> convertToInfoAboutUserFriendShipRequest(List<FriendShipRequest> requests){

        List<InfoAboutUserFriendShipRequest> infoAboutUserFriendShipRequests = new ArrayList<>();
        for (FriendShipRequest f:
             requests) {
            User requester = userService.findOne(f.getRequesterId());
            InfoAboutUserFriendShipRequest info = new InfoAboutUserFriendShipRequest();
            info.setId(requester.getId());
            info.setName(requester.getName());
            info.setSurname(requester.getFamilyName());
            info.setCity(requester.getCity().getName());
            info.setCountry(requester.getCity().getCountry().getName());

            Pet userPet = null;
            for (Pet p:
                    requester.getPetList()) {
                if(p.getId().equals(requester.getUserState().getCurrentPetChoose())){
                    userPet = p;
                }
            }
            String petName;
            String petBreed;
            if(userPet == null){
                petName ="Not Set";
                petBreed = "Not Set";
            }else {
                petName = userPet.getName();
                petBreed = userPet.getBreed().getName();
            }

            info.setPetName(petName);
            info.setPetBreed(petBreed);
            info.setRequestId(f.getId());
            infoAboutUserFriendShipRequests.add(info);

        }
        return infoAboutUserFriendShipRequests;

    }

    @PostMapping("/allFriendshipRequestsIsDeletedFromCache")
    private ResponseEntity<String> changePersistingOnClientSide(@RequestParam("id") Long id){
        List<FriendShipRequest> friendShipRequestList = friendShipRequestService.findAllByAcceptorId(id);

        for (FriendShipRequest friendshiprequest:
             friendShipRequestList) {
            friendshiprequest.setPersistedOnClientSide(false);
            friendShipRequestService.update(friendshiprequest);
        }

        return new ResponseEntity<>("Ok", HttpStatus.ACCEPTED);
    }

    @PostMapping("/getInfoAboutNewFriend")
    private FriendInfo sendNewFriendToTheClient(@RequestParam("new_friend_id") Long id){
        User user = userService.findOne(id);
        FriendInfo newFriend = new FriendInfo();

        newFriend.setId(user.getId());
        newFriend.setName(user.getName());
        newFriend.setSurname(user.getFamilyName());

        Pet pet =  petService.findOne(user.getUserState().getCurrentPetChoose());

        newFriend.setPetName(pet.getName());
        newFriend.setPetBreedName(pet.getBreed().getName());

        newFriend.setLastActiveTime(user.getUserState().getLastActiveTime());

        return newFriend;
    }

    @PostMapping("/getFriendWhoAreNotInPhoneCache")
    private List<FriendInfo> sendToClientAllFriendWhoAreNotThere(@RequestParam("list_friend_ids") List<Long> listOfIds,
                                                                 @RequestParam("user_id") Long id){


        List<FriendInfo> friendWhoAreNotInPhoneCache = new ArrayList<>();

        for (Friends f: userService.findOne(id).getFriends()) {

            User friend;

            if(!id.equals(f.getSide1().getId())){
                friend = f.getSide1();
            }else {
                friend = f.getSide2();
            }

            if(!listOfIds.contains(friend.getId())){
                FriendInfo newFriend = new FriendInfo();

                newFriend.setId(friend.getId());
                newFriend.setName(friend.getName());
                newFriend.setSurname(friend.getFamilyName());

                Pet pet =  petService.findOne(friend.getUserState().getCurrentPetChoose());

                newFriend.setPetName(pet.getName());
                newFriend.setPetBreedName(pet.getBreed().getName());

                newFriend.setLastActiveTime(friend.getUserState().getLastActiveTime());

                friendWhoAreNotInPhoneCache.add(newFriend);

            }

        }

        return friendWhoAreNotInPhoneCache;
    }

    @PostMapping("/deleteUserFromFriendList")
    private String deleteUserFromFriendList(@RequestParam("userId") Long userID, @RequestParam("friendId") Long friendID){

        User user = userService.findOne(userID);
        User friend = userService.findOne(friendID);
        Friends friendship = friendShipService.findBySide1AndSide2(user, friend);
        if(friendship == null){
            friendship = friendShipService.findBySide1AndSide2(friend, user);
        }

        friendShipService.delete(friendship.getId());


        deleteFriendNotifier.sendNotification(user, friend);

        return "OK";
    }

    @PostMapping("/getListOfFriendIDsWhoDeleteUserFromFriends")
    private List<Long> getListOfFriendIDsWhoDeleteUserFromFriends(@RequestParam("list_friend_ids") List<Long> listOfIds,
                                                              @RequestParam("user_id") Long userID){


        List<Long> friendWhoDeleteUserFromFriend = new ArrayList<>();
        List<Friends> friends = userService.findOne(userID).getFriends();
        //userService.findOne(id).getFriends()
        for (Friends f: friends) {


            User friend;

            if(!userID.equals(f.getSide1().getId())){
                friend = f.getSide1();
            }else {
                friend = f.getSide2();
            }

            if(!listOfIds.contains(friend.getId())){
                friendWhoDeleteUserFromFriend.add(friend.getId());
            }


        }

        return friendWhoDeleteUserFromFriend;


    }


}
