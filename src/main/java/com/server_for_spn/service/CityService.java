package com.server_for_spn.service;

import com.server_for_spn.entity.City;
import com.server_for_spn.entity.Country;

import java.util.List;

public interface CityService {
    void save(City city);

    List<City> findAll();

    City findOne(Long id);

    void delete(Long  id);

    void update(City city);

    City findByNameAndCountry(String name, Country country);
}
