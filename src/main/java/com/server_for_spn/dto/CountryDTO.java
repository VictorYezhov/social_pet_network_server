package com.server_for_spn.dto;

import com.server_for_spn.entity.Country;

/**
 * Created by Victor on 03.07.2018.
 */
public class CountryDTO {

    private Long id;
    private String code;
    private String name;

    public CountryDTO() {
    }

    public CountryDTO(Country country){
        this.id = country.getId();
        this.code = country.getCode();
        this.name = country.getName();
    }

    public CountryDTO(Long id, String code, String name) {
        this.id = id;
        this.code = code;
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
