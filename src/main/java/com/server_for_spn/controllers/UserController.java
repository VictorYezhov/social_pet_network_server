package com.server_for_spn.controllers;

import com.server_for_spn.dto.RegistrationForm;
import com.server_for_spn.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by Victor on 01.07.2018.
 */
@RestController
public class UserController {

    @Autowired
    private UserService userService;


    @GetMapping
    public String registration(@RequestBody RegistrationForm registrationForm){
        return userService.registration(registrationForm);
    }




}
