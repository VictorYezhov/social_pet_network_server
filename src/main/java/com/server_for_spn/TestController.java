package com.server_for_spn;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by Victor on 28.06.2018.
 */
@RestController
public class TestController {


    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @GetMapping("/test")
    public String test()
    {
        System.out.println(bCryptPasswordEncoder.encode("vicyezh1998"));
        return "test\n";
    }


    @GetMapping("/secureTest")
    public String secureTest(){
        return "you have succesfully loggined\n";
    }
}
