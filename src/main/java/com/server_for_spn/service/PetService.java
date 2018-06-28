package com.server_for_spn.service;

import com.server_for_spn.entity.Pet;

import java.util.List;

public interface PetService {
    void save(Pet pet);

    List<Pet> findAll();

    Pet findOne(Long id);

    void delete(Long  id);

    void update(Pet pet);
}
