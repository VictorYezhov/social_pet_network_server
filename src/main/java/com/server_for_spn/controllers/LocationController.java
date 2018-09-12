package com.server_for_spn.controllers;

import antlr.DumpASTVisitor;
import com.server_for_spn.dao.LostPetDAO;
import com.server_for_spn.dto.LostPetDTO;
import com.server_for_spn.entity.*;
import com.server_for_spn.fcm_notifications.SosNotifier;
import com.server_for_spn.lockationServises.LocationService;
import com.server_for_spn.lockationServises.models.CoordinatesInfo;
import com.server_for_spn.lockationServises.models.LocationResponse;
import com.server_for_spn.lockationServises.models.UserAddress;
import com.server_for_spn.service.CityService;
import com.server_for_spn.service.CountryService;
import com.server_for_spn.service.PetService;
import com.server_for_spn.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Victor on 16.08.2018.
 */
@RestController
public class LocationController {


    @Autowired
    private UserService userService;

    @Autowired
    private LocationService locationService;

    @Autowired
    private LostPetDAO lostPetDAO;

    @Autowired
    private CityService cityService;

    @Autowired
    private CountryService countryService;


    @Autowired
    private SosNotifier sosNotifier;

    @Autowired
    private PetService  petService;


    @PostMapping("/updateUserPosition")
    public ResponseEntity<?> updateUserPosition(@RequestBody UserAddress userAddress,
                                                @RequestParam("id") Long id,
                                                @RequestParam("attitude")byte attitude,
                                                Authentication authentication){
        User user = userService.findOne(id);
        if(!user.getEmail().equals(authentication.getPrincipal().toString())){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        userAddress.setUserId(id);
        userAddress.setAttitude(attitude);
        LocationResponse locationResponse = locationService.saveLocation(userAddress);
        if(!locationResponse.isState()){
            System.out.println(locationResponse.getMessage());
        }

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/getUsersNearMe")
    public ResponseEntity<Map<Long, CoordinatesInfo>> getUsersNearMe(@RequestBody UserAddress userAddress,
                                                                     @RequestParam("id") Long id,
                                                                     Authentication authentication) throws InterruptedException {

        Map<Long, CoordinatesInfo> coordinatesMap =locationService.getUsersNearMe(userAddress);
        User user = userService.findOne(id);
        userAddress.setUserId(id);
        if(!authentication.getPrincipal().toString().equals(user.getEmail())){
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
        if(coordinatesMap != null){
            return new ResponseEntity<>(coordinatesMap, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

    }





    //TODO Delete in production
    @GetMapping("/api/getSnapshoot")
    public String snapshoot(){
        return locationService.realTimeSnapShoot();
    }




    @PostMapping("/loadMapInfo")
    public Map<String, String> getMapInfoAboutUser(@RequestParam("id") Long id){
        User u = userService.findOne(id);

        Map<String, String> params = new HashMap<>();
        params.put("owner", u.getName()+" "+u.getFamilyName());

        String petName = null;
        String petBreed = null;
        String petSex = null;
        String petAge = null;

        for(Pet p: u.getPetList()){
            if(p.getId().equals(u.getUserState().getCurrentPetChoose())){
                petName = p.getName();
                petAge = String.valueOf(p.getAge());
                petBreed = p.getBreed().getName();
                petSex = p.getSex().name();
            }
        }
        params.put("petName", petName);
        params.put("petBreed", petBreed);
        params.put("petSex", petSex);
        params.put("petAge", petAge);



        return params;
    }




    @PostMapping("/sos")
    public ResponseEntity<?> sos(@RequestParam("id") Long userId,
                                 @RequestParam("petId") Long petId,
                                 @RequestBody UserAddress userAddress,
                                 Authentication authentication){
        User u = userService.findOne(userId);
        if(!u.getEmail().equals(authentication.getPrincipal().toString())){
            return new ResponseEntity<>("Authentication Fail", HttpStatus.BAD_REQUEST);
        }

        Pet pet = null;

        for (Pet p:
             u.getPetList()) {
            if(p.getId().equals(petId))
                pet  = p;
        }
        if(pet == null){
            return new ResponseEntity<>("Pet not find", HttpStatus.BAD_REQUEST);
        }

        Country  country =  countryService.findByName(userAddress.getmCountryName());
        if(country == null){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        if(userAddress.getmLocality() == null){
            return  new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        City city = cityService.findByNameAndCountry(userAddress.getmLocality(), country);
        if(city == null){
            city = new City();
            city.setCountry(country);
            city.setName(userAddress.getmLocality());
            cityService.save(city);
        }
        LostPet  lostPet = lostPetDAO.findFirstByCityAndLostPetId(city, pet.getId());
        if(lostPet != null){
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }

        lostPet = new LostPet();
        lostPet.setCity(city);
        lostPet.setLostPetId(pet.getId());
        if(userAddress.getmSubLocality() != null){
            lostPet.setSubLocality(userAddress.getmSubLocality());
        }
        lostPetDAO.save(lostPet);
//        city.getLostPets().add(lostPet);
        cityService.update(city);
        sosNotifier.sendNotification(pet, city);


        return new ResponseEntity<>(HttpStatus.ACCEPTED);
    }


    @PostMapping("/getLostDogs")
    public ResponseEntity<List<LostPetDTO>> getLostPets(@RequestBody UserAddress userAddress){


        Country  country =  countryService.findByName(userAddress.getmCountryName());
        if(country == null){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        if(userAddress.getmLocality() == null){
            return  new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        City city = cityService.findByNameAndCountry(userAddress.getmLocality(), country);
        if(city == null){
            city = new City();
            city.setCountry(country);
            city.setName(userAddress.getmLocality());
            cityService.save(city);
        }
        List<LostPetDTO> lostPetDTOS = new ArrayList<>();
        List<LostPet> lostPets = lostPetDAO.findAllByCity(city);

        for (LostPet lostPet:
             lostPets) {
            User owner = petService.findOne(lostPet.getLostPetId()).getUser();
            lostPetDTOS.add(new LostPetDTO(lostPet, owner));
        }

        return new ResponseEntity<>(lostPetDTOS, HttpStatus.OK);

    }

    @PostMapping("/userFoundHisPet")
    public String userFoundHisPet(@RequestParam("pet_id") Long petID){
        LostPet lostPet = lostPetDAO.findByLostPetId(petID);
        lostPetDAO.delete(lostPet);
        return "OK";
    }




}
