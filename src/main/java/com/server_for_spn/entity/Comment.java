package com.server_for_spn.entity;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Timestamp time;
    private Long userWhoLeaveAComment;
    private String text;

    @ManyToOne
    @JoinColumn(name = "photo_id")
    private Photo photo;

    public Comment() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Timestamp getTime() {
        return time;
    }

    public void setTime(Timestamp time) {
        this.time = time;
    }

    public Long getUserWhoLeaveAComment() {
        return userWhoLeaveAComment;
    }

    public void setUserWhoLeaveAComment(Long userWhoLeaveAComment) {
        this.userWhoLeaveAComment = userWhoLeaveAComment;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Photo getPhoto() {
        return photo;
    }

    public void setPhoto(Photo photo) {
        this.photo = photo;
    }
}
