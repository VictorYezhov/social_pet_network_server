package com.server_for_spn.dto;



/**
 * Created by Victor on 05.08.2018.
 */
public class UpdateTokenForm {
    private String id;
    private String token;


    public void setId(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public String getToken() {
        return token;
    }


    public void setToken(String token) {
        this.token = token;
    }
}
