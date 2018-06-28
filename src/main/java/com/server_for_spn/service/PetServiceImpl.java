package com.server_for_spn.service;

import com.server_for_spn.dao.PetDAO;
import com.server_for_spn.entity.Pet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PetServiceImpl implements PetService {
    @Autowired
    PetDAO petDAO;

    @Override
    public void save(Pet pet) {
        petDAO.save(pet);
    }

    @Override
    public List<Pet> findAll() {
        return petDAO.findAll();
    }

    @Override
    public Pet findOne(Long id) {
        return petDAO.getOne(id);
    }

    @Override
    public void delete(Long id) {
        petDAO.deleteById(id);
    }

    @Override
    public void update(Pet pet) {
        petDAO.save(pet);
    }
}
