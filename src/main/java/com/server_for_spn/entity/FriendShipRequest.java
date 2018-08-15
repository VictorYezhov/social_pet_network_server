package com.server_for_spn.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 * Created by Victor on 05.08.2018.
 */

@Entity
public class FriendShipRequest {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    private Long requesterId;

    private Long acceptorId;

    private boolean persistedOnClientSide;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getRequesterId() {
        return requesterId;
    }

    public void setRequesterId(Long requesterId) {
        this.requesterId = requesterId;
    }

    public Long getAcceptorId() {
        return acceptorId;
    }

    public void setAcceptorId(Long acceptorId) {
        this.acceptorId = acceptorId;
    }

    public boolean isPersistedOnClientSide() {
        return persistedOnClientSide;
    }

    public void setPersistedOnClientSide(boolean persistedOnClientSide) {
        this.persistedOnClientSide = persistedOnClientSide;
    }
}
