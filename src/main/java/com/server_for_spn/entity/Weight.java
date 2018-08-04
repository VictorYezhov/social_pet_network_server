package com.server_for_spn.entity;

import com.server_for_spn.dto.WeightDTO;
import com.server_for_spn.enums.MassUnit;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Victor on 28.06.2018.
 */
@Entity
public class Weight {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Double mass;
    @Enumerated
    private  MassUnit massUnit;

    @OneToMany(mappedBy = "weight", fetch = FetchType.LAZY)
    private List<Pet> petList;

    public Weight() {
        petList = new ArrayList<>();
    }

    public Weight(WeightDTO weightDTO) {
        this.mass = weightDTO.getMass();
        this.massUnit = weightDTO.getMassUnit();
        petList = new ArrayList<>();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getMass() {
        return mass;
    }

    public void setMass(Double mass) {
        this.mass = mass;
    }

    public MassUnit getMassUnit() {
        return massUnit;
    }

    public void setMassUnit(MassUnit massUnit) {
        this.massUnit = massUnit;
    }

    public List<Pet> getPetList() {
        return petList;
    }

    public void setPetList(List<Pet> petList) {
        this.petList = petList;
    }
}
