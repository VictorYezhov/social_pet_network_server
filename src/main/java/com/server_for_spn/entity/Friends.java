package com.server_for_spn.entity;

import com.sun.xml.internal.bind.v2.model.core.ID;

import javax.persistence.*;

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
}
