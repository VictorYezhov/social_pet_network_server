package com.server_for_spn.service;

import com.server_for_spn.dao.WeightDAO;
import com.server_for_spn.entity.Weight;
import com.server_for_spn.enums.MassUnit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class WeightServiceImpl implements WeightService {
    @Autowired
    private WeightDAO weightDAO;

    @Override
    public void save(Weight w) {
        weightDAO.save(w);
    }

    @Override
    public List<Weight> findAll() {
        return weightDAO.findAll();
    }

    @Override
    public Weight findOne(Long id) {
        return weightDAO.getOne(id);
    }

    @Override
    public void delete(Long id) {
        weightDAO.deleteById(id);
    }

    @Override
    public void update(Weight w) {
        weightDAO.save(w);
    }

    @Override
    public Weight findOneByMassAndMassUnit(Double mass, MassUnit massUnit) {
        return weightDAO.findByMassAndMassUnit(mass, massUnit);
    }
}
