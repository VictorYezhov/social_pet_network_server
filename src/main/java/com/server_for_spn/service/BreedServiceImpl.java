package com.server_for_spn.service;

import com.server_for_spn.dao.BreedDAO;
import com.server_for_spn.entity.Breed;
import com.server_for_spn.enums.PetType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by Victor on 28.06.2018.
 */
@Service
public class BreedServiceImpl implements BreedService {

    @Autowired
    private BreedDAO breedDAO;


    @Override
    public void save(Breed breed) {
        breedDAO.save(breed);
    }

    @Override
    public List<Breed> findAll() {
        return breedDAO.findAll();
    }

    @Override
    public Breed findOne(Long id) {
        return breedDAO.getOne(id);
    }

    @Override
    public void delete(Long id) {
        breedDAO.deleteById(id);
    }

    @Override
    public void update(Breed breed) {
        breedDAO.save(breed);
    }

    @Override
    public List<Breed> findAllByType(PetType petType) {
        return breedDAO.findAllByType(petType);
    }
}
