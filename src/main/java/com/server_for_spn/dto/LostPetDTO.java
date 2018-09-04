package com.server_for_spn.dto;

import com.server_for_spn.entity.LostPet;
import com.server_for_spn.entity.Pet;
import com.server_for_spn.entity.User;

/**
 * Created by Victor on 04.09.2018.
 */
public class LostPetDTO {

    private Long userId;
    private Long petId;
    private String userName;
    private String userSurname;
    private String petName;
    private String breed;
    private String district;


    public LostPetDTO(LostPet lostPet, User owner) {
        userId = owner.getId();

        petId = lostPet.getLostPetId();
        userName = owner.getName();
        userSurname = owner.getFamilyName();
        Pet pet = null;

        for (Pet p :
             owner.getPetList()) {
            if(p.getId().equals(lostPet.getLostPetId()))
                pet = p;
        }
        if(pet != null) {
            petName = pet.getName();
            breed = pet.getBreed().getName();
        }
        district = lostPet.getSubLocality();

    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getPetId() {
        return petId;
    }

    public void setPetId(Long petId) {
        this.petId = petId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserSurname() {
        return userSurname;
    }

    public void setUserSurname(String userSurname) {
        this.userSurname = userSurname;
    }

    public String getPetName() {
        return petName;
    }

    public void setPetName(String petName) {
        this.petName = petName;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }


    public String getBreed() {
        return breed;
    }

    public void setBreed(String breed) {
        this.breed = breed;
    }


}
