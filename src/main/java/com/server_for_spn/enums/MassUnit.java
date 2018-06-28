package com.server_for_spn.enums;

/**
 * Created by Victor on 28.06.2018.
 */
public enum  MassUnit {
    KG("kg"), POUNDS("lb");


    private String name;

    MassUnit(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
