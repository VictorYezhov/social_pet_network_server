package com.server_for_spn.dao;

import com.server_for_spn.entity.Country;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CountryDAO extends JpaRepository<Country, Long> {

    Country findFirstByName(String name);

}
