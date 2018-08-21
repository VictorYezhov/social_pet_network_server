package com.server_for_spn.dto;

import com.server_for_spn.entity.User;

import java.sql.Timestamp;

public class Contact {
    private Long friendId;
    private Timestamp timestamp;
    private String name;
    private String surname;
    private String lastMessage;

    public Contact(Long friendId, String name, String surname, String lastMessage) {
        this.friendId = friendId;
        this.name = name;
        this.surname = surname;
        this.lastMessage = lastMessage;
    }

    public Contact(Long friendId, Timestamp timestamp, String name, String surname, String lastMessage) {
        this.friendId = friendId;
        this.timestamp = timestamp;
        this.name = name;
        this.surname = surname;
        this.lastMessage = lastMessage;
    }

    public Contact(User user){
        this.friendId = user.getId();
        this.name = user.getName();
        this.surname = user.getFamilyName();
    }

    public Long getFriendId() {
        return friendId;
    }

    public void setFriendId(Long friendId) {
        this.friendId = friendId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Timestamp getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Timestamp timestamp) {
        this.timestamp = timestamp;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getLastMessage() {
        return lastMessage;
    }

    public void setLastMessage(String lastMessage) {
        this.lastMessage = lastMessage;
    }
}
