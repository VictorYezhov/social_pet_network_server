package com.server_for_spn.controllers;

import com.server_for_spn.entity.Breed;
import com.server_for_spn.enums.PetType;
import com.server_for_spn.service.BreedService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

/**
 * Created by Victor on 27.07.2018.
 */
@RestController
public class TestController {


    @Autowired
    private BreedService breedService;

    @GetMapping("/api/test")
    public String test(){

//        boolean flag = true;
//        try (BufferedReader br = new BufferedReader(new FileReader("dog_breeds.txt"))) {
//            String line;
//            while ((line = br.readLine()) != null) {
//                if(flag) {
//                    Breed breed = new Breed();
//                    breed.setName(line);
//                    breed.setType(PetType.DOG);
//                    breedService.save(breed);
//                    System.out.println(line);
//                    flag = false;
//                }else {
//                    flag = true;
//                }
//            }
//        }catch (FileNotFoundException e){
//            return "FILE NOT FOUND";
//        }catch (IOException e){
//            return "IOEXEPTION";
//        }
        return breedService.findAll().toString();
    }


}
