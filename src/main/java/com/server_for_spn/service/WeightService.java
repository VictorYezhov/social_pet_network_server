package com.server_for_spn.service;

import com.server_for_spn.entity.Weight;

import java.util.List;

public interface WeightService {
    void save(Weight w);

    List<Weight> findAll();

    Weight findOne(Long id);

    void delete(Long  id);

    void update(Weight w);
}
