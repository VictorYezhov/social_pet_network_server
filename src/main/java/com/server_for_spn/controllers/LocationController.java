package com.server_for_spn.controllers;

import com.server_for_spn.entity.User;
import com.server_for_spn.lockationServises.LocationService;
import com.server_for_spn.lockationServises.models.CoordinatesInfo;
import com.server_for_spn.lockationServises.models.LocationResponse;
import com.server_for_spn.lockationServises.models.UserAddress;
import com.server_for_spn.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * Created by Victor on 16.08.2018.
 */
@RestController
public class LocationController {


    @Autowired
    UserService userService;

    @Autowired
    private LocationService locationService;



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



}
