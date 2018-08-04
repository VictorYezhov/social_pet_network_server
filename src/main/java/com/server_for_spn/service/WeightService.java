package com.server_for_spn.service;

import com.server_for_spn.entity.Weight;
import com.server_for_spn.enums.MassUnit;

import java.util.List;

public interface WeightService {
    void save(Weight w);

    List<Weight> findAll();

    Weight findOne(Long id);

    void delete(Long  id);

    void update(Weight w);

    Weight findOneByMassAndMassUnit(Double mass, MassUnit massUnit);
}
