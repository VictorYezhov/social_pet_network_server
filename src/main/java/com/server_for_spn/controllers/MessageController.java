package com.server_for_spn.controllers;

import com.server_for_spn.dao.FriendsDAO;
import com.server_for_spn.dto.MessageDTO;
import com.server_for_spn.entity.Friends;
import com.server_for_spn.entity.Message;
import com.server_for_spn.service.MessageService;
import com.server_for_spn.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
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

        Friends friends = friendsDAO.findBySide1AndSide2(userService.findOne(id),userService.findOne(messageDTO.getUser_to()));

        if(friends == null){
            friends = friendsDAO.findBySide1AndSide2(userService.findOne(messageDTO.getUser_to()),userService.findOne(id));
        }

        message.setFriends(friends);

        message.setTimestamp(new Timestamp(time));

        messageService.save(message);

        return "Ok";
    }
}
