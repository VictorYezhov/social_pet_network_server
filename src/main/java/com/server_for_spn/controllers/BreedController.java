package com.server_for_spn.controllers;

import com.server_for_spn.dto.BreedDTO;
import com.server_for_spn.entity.Breed;
import com.server_for_spn.enums.PetType;
import com.server_for_spn.service.BreedService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.persistence.Enumerated;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Victor on 27.07.2018.
 */
@RestController
public class BreedController {


    @Autowired
    private BreedService breedService;


    /**
     * Fetch petTypes from server
     * @return
     */
    @GetMapping("/api/getPetTypes")
    private PetType[] getAllPetTypes(){
        return PetType.values();
    }


    /**
     * Used to fetch breeds of specified pet type (cat, dog, etc)
     * @param petType
     * @return
     */
    @GetMapping("/api/getBreedsForType")
    private List<BreedDTO> getAllBreadsForType(@RequestParam PetType petType){
        System.out.println("GET BREEDS FOR TYPE REQUEST");
        List<Breed> breeds = breedService.findAllByType(petType);
        List<BreedDTO> breedDTOS = new ArrayList<>();
        for (Breed b:
             breeds) {
            breedDTOS.add(new BreedDTO(b));
        }
        return breedDTOS;
    }




}
