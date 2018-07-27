package com.server_for_spn.dto;

import com.server_for_spn.entity.Breed;
import com.server_for_spn.enums.PetType;

/**
 * Created by Victor on 27.07.2018.
 */
public class BreedDTO {

    private Long id;

    private PetType type;

    private String name;

    public BreedDTO(Breed breed) {
        id = breed.getId();
        type = breed.getType();
        name = breed.getName();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public PetType getType() {
        return type;
    }

    public void setType(PetType type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
