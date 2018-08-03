package com.server_for_spn.dto;

import com.server_for_spn.entity.City;
import com.server_for_spn.entity.Country;

/**
 * Created by Victor on 01.07.2018.
 */
public class RegistrationForm {

    private String name;
    private String surname;
    private String phone;
    private CityDTO city;
    private String email;
    private String password;
    private PetDTO pet;

    public RegistrationForm() {
    }

    public RegistrationForm(String name, String surname, String phone, CityDTO city, String email, String password, PetDTO pet) {
        this.name = name;
        this.surname = surname;
        this.phone = phone;
        this.city = city;
        this.email = email;
        this.password = password;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public PetDTO getPet() {
        return pet;
    }

    public void setPet(PetDTO pet) {
        this.pet = pet;
    }
}
