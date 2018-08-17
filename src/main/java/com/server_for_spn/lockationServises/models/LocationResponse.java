package com.server_for_spn.lockationServises.models;

/**
 * Created by Victor on 17.08.2018.
 */
public class LocationResponse {

    private boolean state;
    private String message;


    public LocationResponse(boolean state, String message) {
        this.state = state;
        this.message = message;
    }

    public boolean isState() {
        return state;
    }

    public void setState(boolean state) {
        this.state = state;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
