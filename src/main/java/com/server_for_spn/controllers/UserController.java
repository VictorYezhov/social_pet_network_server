package com.server_for_spn.controllers;

import com.google.gson.Gson;
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


    /**
     * Registration of new Users
     * @param registrationForm
     * @return
     */
    @PostMapping("/registration")
    public String registration(@RequestBody RegistrationForm registrationForm){
        if(registrationForm == null)
            return "NULL";
        Gson gson = new Gson();
        String jsonInString = gson.toJson(registrationForm);
        System.out.println(jsonInString);
        userService.registration(registrationForm);
        return "OK";
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

}
