package com.server_for_spn.dto;

public class InfoAboutUserFriendShipRequest {
    private Long id;
    private String name;
    private String surname;
    private CityDTO city;

    public InfoAboutUserFriendShipRequest(Long id, String name, String surname, CityDTO city) {
        this.id = id;
        this.name = name;
        this.surname = surname;
        this.city = city;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public CityDTO getCity() {
        return city;
    }

    public void setCity(CityDTO city) {
        this.city = city;
    }
}
