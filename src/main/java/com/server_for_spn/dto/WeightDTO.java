package com.server_for_spn.dto;

import com.server_for_spn.enums.MassUnit;

public class WeightDTO {

    private Double mass;
    private MassUnit massUnit;

    public WeightDTO() {
    }

    public WeightDTO(Double mass, MassUnit massUnit) {
        this.mass = mass;
        this.massUnit = massUnit;
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
}
