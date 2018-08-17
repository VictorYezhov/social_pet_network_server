package com.server_for_spn.dto;

import com.server_for_spn.entity.Message;

import java.sql.Timestamp;

public class MessageDTO {
    private Long id;
    private String message;
    private Timestamp timestamp;
    private Long friends_id;
    private Long user_to;
    private boolean read;

    public MessageDTO() {
    }

    public MessageDTO(Message m) {
        this.id = m.getId();
        this.message = m.getMessageText();
        this.timestamp = m.getTimestamp();
        this.friends_id = m.getFriends().getId();
        this.user_to = m.getUserTo();
        this.read = m.isRead();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Timestamp getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Timestamp timestamp) {
        this.timestamp = timestamp;
    }

    public Long getUser_to() {
        return user_to;
    }

    public void setUser_to(Long user_to) {
        this.user_to = user_to;
    }

    public boolean isRead() {
        return read;
    }

    public void setRead(boolean read) {
        this.read = read;
    }

    public Long getFriends_id() {
        return friends_id;
    }

    public void setFriends_id(Long friends_id) {
        this.friends_id = friends_id;
    }
}
