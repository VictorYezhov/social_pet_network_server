package com.server_for_spn.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;



import javax.persistence.*;
import java.util.List;

/**
 * Created by Victor on 05.08.2018.
 */

@Entity
public class Friends {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private User side1;

    @ManyToOne
    private User side2;

    @OneToMany(mappedBy = "friends", fetch = FetchType.LAZY)
    private List<Message> messages;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getSide1() {
        return side1;
    }

    public void setSide1(User side1) {
        this.side1 = side1;
    }

    public User getSide2() {
        return side2;
    }

    public void setSide2(User side2) {
        this.side2 = side2;
    }

    public List<Message> getMessages() {
        return messages;
    }

    public void setMessages(List<Message> messages) {
        this.messages = messages;
    }
}
