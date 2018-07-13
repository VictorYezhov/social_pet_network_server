package com.server_for_spn.dto;

public class UserInformationForm {
    private String phoneNumber;
    private String place;

    public UserInformationForm(String phoneNumber, String place) {
        this.phoneNumber = phoneNumber;
        this.place = place;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }


}
