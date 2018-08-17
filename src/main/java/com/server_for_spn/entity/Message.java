package com.server_for_spn.entity;

import com.server_for_spn.dto.MessageDTO;
import javax.persistence.*;
import java.sql.Timestamp;

@Entity
public class Message {

   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "friends_id")
    private Friends friends;

    private Timestamp timestamp;

    private String messageText;

    private Long userTo;

    private boolean isRead;

    public Message() {
    }

    public Message(MessageDTO messageDTO) {
        this.messageText = messageDTO.getMessage();
        this.userTo = messageDTO.getUser_to();
        this.isRead = messageDTO.isRead();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Friends getFriends() {
        return friends;
    }

    public void setFriends(Friends friends) {
        this.friends = friends;
    }

    public Timestamp getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Timestamp timestamp) {
        this.timestamp = timestamp;
    }


    public Long getUserTo() {
        return userTo;
    }

    public void setUserTo(Long userTo) {
        this.userTo = userTo;
    }


    public boolean isRead() {
        return isRead;
    }

    public void setRead(boolean read) {
        isRead = read;
    }

    public String getMessageText() {
        return messageText;
    }

    public void setMessageText(String messageText) {
        this.messageText = messageText;
    }
}
