package com.server_for_spn.dto;

import com.server_for_spn.entity.City;

import java.util.List;

public class InformationOfUserAndHisPet {
    private List<PetDTO> pet;
    private String name;
    private String surname;
    private String phone;
    private CityDTO city;

    public InformationOfUserAndHisPet() {
    }

    public InformationOfUserAndHisPet(List<PetDTO> pet, String name, String surname, String phone, CityDTO city) {
        this.pet = pet;
        this.name = name;
        this.surname = surname;
        this.phone = phone;
        this.city = city;
    }

    public List<PetDTO> getPet() {
        return pet;
    }

    public void setPet(List<PetDTO> pet) {
        this.pet = pet;
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

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public CityDTO getCity() {
        return city;
    }

    public void setCity(CityDTO city) {
        this.city = city;
    }
}
