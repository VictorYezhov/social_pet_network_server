package com.server_for_spn.dao;

import com.server_for_spn.entity.User;
import com.server_for_spn.entity.Weight;
import com.server_for_spn.enums.MassUnit;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by Victor on 28.06.2018.
 */
public interface WeightDAO extends JpaRepository<Weight, Long> {
    Weight findByMassAndMassUnit(Double mass, MassUnit massUnit);
}
