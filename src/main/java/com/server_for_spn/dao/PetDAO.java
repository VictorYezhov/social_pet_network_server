package com.server_for_spn.dao;

import com.server_for_spn.entity.Pet;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PetDAO extends JpaRepository<Pet, Long> {

    Pet findByTagNumber(String tagNumber);
}
