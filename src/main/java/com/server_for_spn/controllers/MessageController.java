package com.server_for_spn.controllers;

import com.server_for_spn.dao.FriendsDAO;
import com.server_for_spn.dto.MessageDTO;
import com.server_for_spn.entity.Friends;
import com.server_for_spn.entity.Message;
import com.server_for_spn.entity.User;
import com.server_for_spn.fcm_notifications.NotificationService;
import com.server_for_spn.service.FriendShipService;
import com.server_for_spn.service.MessageService;
import com.server_for_spn.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@RestController
public class MessageController {

    @Autowired
    private MessageService messageService;

    @Autowired
    private UserService userService;

    @Autowired
    private FriendsDAO friendsDAO;

    @Autowired
    private FriendShipService friendShipService;

    @Autowired
    @Qualifier("messageNotifier")
    private NotificationService messageNotifier;

    @PostMapping("/getAllMessages")
    public List<MessageDTO> sendAllMessageOfCurrentPeople(@RequestParam("user_id") Long user1,
                                                          @RequestParam("friend_id") Long user2){

        Friends friends = friendsDAO.findBySide1AndSide2(userService.findOne(user1),userService.findOne(user2));

        if(friends == null){
            friends = friendsDAO.findBySide1AndSide2(userService.findOne(user2),userService.findOne(user1));
        }

        List<MessageDTO> messageDTOList = new ArrayList<>();
        List<Message> messages = friends.getMessages();

        for (Message m: messages) {
            messageDTOList.add(new MessageDTO(m));
        }

        return messageDTOList;
    }

    @PostMapping("/sendMessage")
    public String saveMessageInDb(@RequestBody MessageDTO messageDTO,
                                  @RequestParam("id") Long id,
                                  @RequestParam("time") Long time){
        Message message = new Message(messageDTO);

        User user = userService.findOne(id);
        User friend = userService.findOne(messageDTO.getUserTo());
        Friends friends = friendsDAO.findBySide1AndSide2(user,friend);

        if(friends == null){
            friends = friendsDAO.findBySide1AndSide2(friend,user);
        }

        message.setFriends(friends);

        message.setTimestamp(new Timestamp(time));

        messageService.save(message);

        user.additionalField = message.getMessageText();
        friend.additionalField = friends.getId();
        messageNotifier.sendNotification(user, friend);

        return "Ok";
    }


    @PostMapping("/getUnReadMessagesAmount")
    public Integer getUnReadMessagesAmount(@RequestParam("id") Long id, Authentication authentication){

        User u = userService.findOne(id);
        if(!authentication.getPrincipal().toString().equals(u.getEmail())){
            System.out.println("Security breakUp");
            return null;
        }

        return messageService.countInreadedMessagesForUser(id);
    }



    @PostMapping("/makeLastMessageRead")
    public String makeMessageRead(@RequestParam("friends_id") Long id, @RequestParam("user_id") Long userId){
        Friends friends = friendShipService.findOne(id);
        List<Message> messages = messageService.findAllUnreadMessages(friends.getId(), userId);

        for (Message message:
                messages) {
            message.setRead(true);
            messageService.update(message);
        }

        return "Ok";
    }

    @PostMapping("/getAllUnreadMessages")
    public List<MessageDTO> sendAllUnreadMessages(@RequestParam("user_1") Long user1,
                                        @RequestParam("user_2") Long user2){

        List<MessageDTO> messageDTOList = new ArrayList<>();
        Friends friends = friendsDAO.findBySide1AndSide2(userService.findOne(user1),userService.findOne(user2));

        if(friends == null){
            friends = friendsDAO.findBySide1AndSide2(userService.findOne(user2),userService.findOne(user1));
        }

        List<Message> messages = messageService.findAllUnreadMessages(friends.getId(), user2);

        for (Message message:
             messages) {
            MessageDTO messageDTO = new MessageDTO(message);
            messageDTOList.add(messageDTO);
        }

        return messageDTOList;
    }
}
