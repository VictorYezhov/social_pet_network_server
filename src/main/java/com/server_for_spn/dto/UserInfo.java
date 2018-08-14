package com.server_for_spn.dto;

import com.server_for_spn.entity.City;
import com.server_for_spn.entity.Pet;
import com.server_for_spn.entity.User;

import java.util.ArrayList;
import java.util.List;

public class UserInfo {
    private Long id;
    private Long pet_id;
    private String name;
    private String surname;
    private String phone;
    private String email;
    private CityDTO city;
    private List<PetDTO> pets;

    public UserInfo(User user) {
        this.id = user.getId();
        this.pet_id = user.getUserState().getCurrentPetChoose();
        this.name = user.getName();
        this.surname = user.getFamilyName();
        this.phone = user.getPhoneNumber();
        this.email = user.getEmail();

        this.city = new CityDTO(user.getCity());

        List<PetDTO> petDTOList = new ArrayList<>();
        for (Pet p:
             user.getPetList()) {
            petDTOList.add(new PetDTO(p));
        }

        this.pets = petDTOList;
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

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public CityDTO getCity() {
        return city;
    }

    public void setCity(CityDTO city) {
        this.city = city;
    }

    public List<PetDTO> getPets() {
        return pets;
    }

    public void setPets(List<PetDTO> pets) {
        this.pets = pets;
    }

    public Long getPet_id() {
        return pet_id;
    }

    public void setPet_id(Long pet_id) {
        this.pet_id = pet_id;
    }
}
