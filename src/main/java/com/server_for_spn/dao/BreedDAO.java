package com.server_for_spn.dao;

import com.server_for_spn.entity.Breed;
import com.server_for_spn.entity.User;
import com.server_for_spn.enums.PetType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Created by Victor on 28.06.2018.
 */
public interface BreedDAO extends JpaRepository<Breed, Long> {

    List<Breed> findAllByType(PetType petType);
}
