package com.server_for_spn;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by Victor on 28.06.2018.
 */
@RestController
public class TestController {



    @GetMapping("/test")
    public String test(){
        return "test\n";
    }


    @GetMapping("/secureTest")
    public String secureTest(){
        return "you have succesfully loggined\n";
    }
}
