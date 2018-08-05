package com.server_for_spn.controllers;

import com.server_for_spn.dto.RegistrationForm;
import com.server_for_spn.dto.UpdateTokenForm;
import com.server_for_spn.dto.UserInformationForm;
import com.server_for_spn.entity.FriendShipRequest;
import com.server_for_spn.entity.Friends;
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

import java.util.Base64;

/**
 * Created by Victor on 01.07.2018.
 */
@RestController
public class UserController {

    @Autowired
    private UserService userService;


    @PostMapping("/registration")
    public String registration(@RequestBody RegistrationForm registrationForm){
        if(registrationForm == null)
            return "NULL";
        userService.registration(registrationForm);
        return "OK";
    }


    @GetMapping("/loginFailed")
    public String loginFail(){
        return "login fail";
    }




    @PostMapping("/get_user_information")
    public UserInformationForm sendUserInformation(@RequestBody String id){
        System.out.println(id);
        System.out.println(id.split(":")[1].split("\"")[1]);
        User user = userService.findOne(Long.parseLong(id.split(":")[1].split("\"")[1]));
        String place = user.getCity().getName().toString() + ", " + user.getCity().getCountry().getName().toString();
        return new UserInformationForm(user.getPhoneNumber(), place, user.getEmail(), user.getName() + " " + user.getFamilyName());
    }


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
        u.setDeviceOS(updateTokenForm.getDeviceOS());

        userService.update(u);

        return new ResponseEntity<>( "OK", HttpStatus.ACCEPTED);


    }

}
