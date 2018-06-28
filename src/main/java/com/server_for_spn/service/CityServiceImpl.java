package com.server_for_spn.service;

import com.server_for_spn.dao.CityDAO;
import com.server_for_spn.entity.City;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CityServiceImpl implements CityService {

    @Autowired
    private CityDAO cityDAO;

    @Override
    public void save(City city) {
        cityDAO.save(city);
    }

    @Override
    public List<City> findAll() {
        return cityDAO.findAll();
    }

    @Override
    public City findOne(Long id) {
        return cityDAO.getOne(id);
    }

    @Override
    public void delete(Long id) {
        cityDAO.deleteById(id);
    }

    @Override
    public void update(City city) {
        cityDAO.save(city);
    }
}
