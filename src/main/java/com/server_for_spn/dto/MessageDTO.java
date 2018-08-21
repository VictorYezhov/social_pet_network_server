package com.server_for_spn.dto;

import com.server_for_spn.entity.Message;

import java.sql.Timestamp;

public class MessageDTO {
    private Long id;
    private String message;
    private Timestamp timestamp;
    private Long friendsId;
    private Long userTo;
    private boolean read;

    public MessageDTO() {
    }

    public MessageDTO(Message m) {
        this.id = m.getId();
        this.message = m.getMessageText();
        this.timestamp = m.getTimestamp();
        this.friendsId = m.getFriends().getId();
        this.userTo = m.getUserTo();
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

    public Long getUserTo() {
        return userTo;
    }

    public void setUserTo(Long userTo) {
        this.userTo = userTo;
    }

    public boolean isRead() {
        return read;
    }

    public void setRead(boolean read) {
        this.read = read;
    }

    public Long getFriendsId() {
        return friendsId;
    }

    public void setFriendsId(Long friendsId) {
        this.friendsId = friendsId;
    }

    public Long getUserFrom() {
        return userFrom;
    }

    public void setUserFrom(Long userFrom) {
        this.userFrom = userFrom;
    }

    private Long userFrom;
}
