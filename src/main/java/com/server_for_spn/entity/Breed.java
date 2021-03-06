package com.server_for_spn.entity;

import com.server_for_spn.enums.PetType;

import javax.persistence.*;
import java.util.List;

/**
 * Created by Victor on 28.06.2018.
 */
@Entity
public class Breed {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated
    private PetType type;

    private String name;

    @OneToMany(mappedBy = "breed", fetch = FetchType.LAZY)
    private List<Pet> petList;

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

    public List<Pet> getPetList() {
        return petList;
    }

    public void setPetList(List<Pet> petList) {
        this.petList = petList;
    }
}
