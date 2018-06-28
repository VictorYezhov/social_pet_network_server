package com.server_for_spn.service;

import com.server_for_spn.entity.Breed;
import com.server_for_spn.entity.City;

import java.util.List;

/**
 * Created by Victor on 28.06.2018.
 */
public interface BreedService {

    void save(Breed breed);

    List<Breed> findAll();

    Breed findOne(Long id);

    void delete(Long  id);

    void update(Breed breed);


}
