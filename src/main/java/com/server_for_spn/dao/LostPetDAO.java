package com.server_for_spn.dao;

import com.server_for_spn.entity.City;
import com.server_for_spn.entity.LostPet;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Created by Victor on 03.09.2018.
 */
public interface LostPetDAO extends JpaRepository<LostPet, Long> {

    LostPet findFirstByCityAndLostPetId(City city, Long lostPetId);
    List<LostPet> findAllByCity(City city);
    LostPet findByLostPetId(Long id);
}
