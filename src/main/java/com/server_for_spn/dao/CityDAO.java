package com.server_for_spn.dao;

import com.server_for_spn.entity.City;
import com.server_for_spn.entity.Country;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CityDAO extends JpaRepository<City, Long> {


    City findFirstByNameAndAndCountry(String name, Country country);

}
