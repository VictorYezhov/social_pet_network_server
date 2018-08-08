package com.server_for_spn.dto;

import com.server_for_spn.entity.City;

public class CityDTO {

    private Long id;
    private String name;
    private CountryDTO country;

    public CityDTO(){

    }

    public CityDTO(City city){
        this.id = city.getId();
        this.name = city.getName();

        CountryDTO countryDTO = new CountryDTO(city.getCountry());
        this.country = countryDTO;
    }

    public CityDTO(String name, CountryDTO country) {
        this.name = name;
        this.country = country;
    }

    public CityDTO(Long id, String name, CountryDTO country) {
        this.id = id;
        this.name = name;
        this.country = country;
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

    public CountryDTO getCountry() {
        return country;
    }

    public void setCountry(CountryDTO country) {
        this.country = country;
    }
}
