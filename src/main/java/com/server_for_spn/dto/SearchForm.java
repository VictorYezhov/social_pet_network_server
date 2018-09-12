package com.server_for_spn.dto;

import com.server_for_spn.enums.PetType;

/**
 * Created by Victor on 12.09.2018.
 */
public class SearchForm {

    private PetType petType;
    private String name;
    private String breed;
    private String ownersName;
    private String userCity;
    private String userCountry;

    public SearchForm() {
    }

    public String getUserCountry() {
        return userCountry;
    }

    public void setUserCountry(String userCountry) {
        this.userCountry = userCountry;
    }

    public PetType getPetType() {
        return petType;
    }

    public void setPetType(PetType petType) {
        this.petType = petType;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBreed() {
        return breed;
    }

    public void setBreed(String breed) {
        this.breed = breed;
    }


    public String getOwnersName() {
        return ownersName;
    }

    public void setOwnersName(String ownersName) {
        this.ownersName = ownersName;
    }

    public String getUserCity() {
        return userCity;
    }

    public void setUserCity(String userCity) {
        this.userCity = userCity;
    }

    @Override
    public String toString() {
        return "SearchForm{" +
                "petType=" + petType +
                ", name='" + name + '\'' +
                ", breed='" + breed + '\'' +
                ", ownersName='" + ownersName + '\'' +
                ", userCity='" + userCity + '\'' +
                ", userCountry='" + userCountry + '\'' +
                '}';
    }
}
