package com.server_for_spn.controllers;

import com.server_for_spn.dto.RegistrationForm;
import com.server_for_spn.dto.UserInformationForm;
import com.server_for_spn.entity.User;
import com.server_for_spn.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

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
        System.out.println(registrationForm.toString());
        return userService.registration(registrationForm);
        //return  registrationForm.toString();
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

}
