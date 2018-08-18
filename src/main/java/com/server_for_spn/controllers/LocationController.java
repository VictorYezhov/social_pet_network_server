package com.server_for_spn.controllers;

import com.server_for_spn.entity.User;
import com.server_for_spn.lockationServises.LocationService;
import com.server_for_spn.lockationServises.models.LocationResponse;
import com.server_for_spn.lockationServises.models.UserAddress;
import com.server_for_spn.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import javax.persistence.GeneratedValue;
import java.util.concurrent.TimeUnit;

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
                                                Authentication authentication){
        User user = userService.findOne(id);
        if(!user.getEmail().equals(authentication.getPrincipal().toString())){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        userAddress.setUserId(id);
        long start = System.currentTimeMillis();
        LocationResponse locationResponse = locationService.saveLocation(userAddress);
        long end  = System.currentTimeMillis();
        System.out.println("-------------------------------------------------------------------------");
        System.out.println("DURATION:\n"+ TimeUnit.MILLISECONDS.toSeconds(end - start)+" seconds");
        System.out.println((end - start) +" mils");
        System.out.println("RESPONSE: "+locationResponse.getMessage());
        System.out.println("-------------------------------------------------------------------------");
        return new ResponseEntity<>(HttpStatus.OK);
    }


    //TODO Delete in production
    @GetMapping("/api/getSnapshoot")
    public String snapshoot(){
        return locationService.realTimeSnapShoot();
    }



}
