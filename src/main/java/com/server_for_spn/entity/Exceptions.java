package com.server_for_spn.entity;

import javax.persistence.*;

/**
 * Created by Victor on 21.09.2018.
 */
@Entity
public class Exceptions {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(columnDefinition = "TEXT")
    private String error;
    private Long idFrom;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public Long getIdFrom() {
        return idFrom;
    }

    public void setIdFrom(Long idFrom) {
        this.idFrom = idFrom;
    }

    @Override
    public String toString() {
        return "Exceptions{" +
                "error='" + error + '\'' +
                ", idFrom=" + idFrom +
                '}';
    }
}
