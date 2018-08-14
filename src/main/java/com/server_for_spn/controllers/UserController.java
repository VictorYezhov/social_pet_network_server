package com.server_for_spn.controllers;

import com.google.gson.Gson;
import com.server_for_spn.dto.*;
import com.server_for_spn.entity.FriendShipRequest;
import com.server_for_spn.entity.Friends;
import com.server_for_spn.entity.Pet;
import com.server_for_spn.entity.User;
import com.server_for_spn.service.FriendShipRequestService;
import com.server_for_spn.service.FriendShipService;
import com.server_for_spn.service.NotificationService;
import com.server_for_spn.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import sun.rmi.runtime.Log;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Date;
import java.util.List;
import java.util.logging.Logger;

/**
 * Created by Victor on 01.07.2018.
 */
@RestController
public class UserController {

    @Autowired
    private UserService userService;


    /**
     * Registration of new Users
     * @param registrationForm
     * @return
     */
    @PostMapping("/registration")
    public ResponseEntity<String> registration(@RequestBody RegistrationForm registrationForm){
        if(registrationForm == null)
            return new ResponseEntity<>( "registrationForm is null", HttpStatus.BAD_REQUEST);;
        userService.registration(registrationForm);
        return new ResponseEntity<>( "OK", HttpStatus.ACCEPTED);
    }


    @GetMapping("/loginFailed")
    public String loginFail(){
        return "login fail";
    }


    /**
     * We must update FCM token every time user login
     * !! SO THIS METHOD MUST BE CALLED EVERY LOGIN ACTION !!!
     * UpdateTokenForm contains Token and userId ENCODED IN BASE64 FORMAT WITH NO_WRAP flag :
     * Encoder flag bit to omit all line terminators (i.e., the output
     * will be on one long line).
     * So we need to decode it first
     * @param updateTokenForm
     * @param authentication
     * @return
     */
    @PostMapping("/updateUsersToken")
    public ResponseEntity<String> updateToken(@RequestBody UpdateTokenForm updateTokenForm, Authentication authentication){

        updateTokenForm.setId(new String(Base64.getDecoder().decode(updateTokenForm.getId())));
        updateTokenForm.setToken(new String(Base64.getDecoder().decode(updateTokenForm.getToken())));
        User u = userService.findOne(Long.decode(updateTokenForm.getId()));
        System.out.println("NEW TOKEN: "+updateTokenForm.getToken());
        if(!authentication.getPrincipal().toString().equals(u.getEmail())){
            return new ResponseEntity<>("CONFLICT", HttpStatus.CONFLICT);
        }

        u.setFcmToken(updateTokenForm.getToken());
        userService.update(u);
        return new ResponseEntity<>( "OK", HttpStatus.ACCEPTED);
    }

    /**
     * Getting all needed information after user login.
     * @param id
     * @param authentication
     * @return
     */
    @PostMapping("/getAllInformationAboutUserAndPets")
    public ResponseEntity<InformationOfUserAndHisPet> sendInfoToClient(@RequestBody Long id, Authentication authentication){
        InformationOfUserAndHisPet info = new InformationOfUserAndHisPet();
        User u = userService.findOne(id);
        if(!authentication.getPrincipal().toString().equals(u.getEmail())){
            return new ResponseEntity<>(info, HttpStatus.CONFLICT);

        }
        u.getUserState().setLastActiveTime(new Timestamp(System.currentTimeMillis()));

        info.setName(u.getName());
        info.setSurname(u.getFamilyName());
        info.setPhone(u.getPhoneNumber());

        CityDTO cityDTO = new CityDTO(u.getCity());

        info.setCity(cityDTO);

        List<PetDTO> petDTOList = new ArrayList<>();

        for (Pet pet: u.getPetList()) {
            PetDTO petDTO = new PetDTO(pet);
            petDTOList.add(petDTO);
        }

        info.setPet(petDTOList);

        return new ResponseEntity<>(info, HttpStatus.ACCEPTED);
    }



    @PostMapping("/online")
    public ResponseEntity<String>  online(@RequestParam("id")Long id,
                                          Authentication authentication){

        User u = userService.findOne(id);
        System.out.println("USER: "+u.getEmail()+" ONLINE");
        if(u.getEmail().equals(authentication.getPrincipal().toString())){
            u.getUserState().setLastActiveTime(new Timestamp(System.currentTimeMillis()));
            userService.save(u);
            return new ResponseEntity<>("OK", HttpStatus.OK);
        }
        return new ResponseEntity<>("BAD", HttpStatus.BAD_REQUEST);
    }

}
