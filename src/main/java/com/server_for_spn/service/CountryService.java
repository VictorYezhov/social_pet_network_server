package com.server_for_spn.service;

import com.server_for_spn.entity.Country;

import java.util.List;

public interface CountryService {

    void save(Country country);

    List<Country> findAll();

    Country findOne(Long id);

    void delete(Long  id);

    void update(Country country);
}
