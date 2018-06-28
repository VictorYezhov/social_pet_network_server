package com.server_for_spn.dao;

import com.server_for_spn.entity.Breed;
import com.server_for_spn.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by Victor on 28.06.2018.
 */
public interface BreedDAO extends JpaRepository<Breed, Long> {
}
