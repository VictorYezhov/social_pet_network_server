package com.server_for_spn.service;

import com.server_for_spn.dao.CountryDAO;
import com.server_for_spn.entity.Country;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CountryServiceImpl implements CountryService {
    @Autowired
    CountryDAO countryDAO;

    @Override
    public void save(Country country) {
        countryDAO.save(country);
    }

    @Override
    public List<Country> findAll() {
        return countryDAO.findAll();
    }

    @Override
    public Country findOne(Long id) {
        return countryDAO.getOne(id);
    }

    @Override
    public void delete(Long id) {
        countryDAO.deleteById(id);
    }

    @Override
    public void update(Country country) {
        countryDAO.save(country);
    }
}
