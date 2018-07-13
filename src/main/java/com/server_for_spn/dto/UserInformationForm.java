package com.server_for_spn.dto;

public class UserInformationForm {
    private String phoneNumber;
    private String place;
    private String email;
    private String name;

    public UserInformationForm(String phoneNumber, String place, String email, String name) {
        this.phoneNumber = phoneNumber;
        this.place = place;
        this.email = email;
        this.name = name;
    }

    public UserInformationForm(String phoneNumber, String place, String email) {
        this.phoneNumber = phoneNumber;
        this.place = place;
        this.email = email;
    }

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


    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
