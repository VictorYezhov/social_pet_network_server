package com.server_for_spn.controllers;

import com.server_for_spn.dto.CountryDTO;
import com.server_for_spn.entity.Country;
import com.server_for_spn.service.CountryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Victor on 03.07.2018.
 */
@RestController
public class CountryController {

    @Autowired
    private CountryService countryService;




    @GetMapping("/loadCountryList")
    public List<CountryDTO> loadCountryList(){

        System.out.println("Load Countries Request");
        List<Country> countries = countryService.findAll();
        List<CountryDTO> countryDTOS = new ArrayList<>();
        for(Country c:countries){
            countryDTOS.add(new CountryDTO(c.getId(), c.getCode(),c.getName()));
        }
        return countryDTOS;
    }




}
