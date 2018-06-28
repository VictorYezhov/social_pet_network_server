package com.server_for_spn.service;

import com.server_for_spn.dao.BreedDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by Victor on 28.06.2018.
 */
@Service
public class BreedServiceImpl {

    @Autowired
    BreedDAO breedDAO;


}
