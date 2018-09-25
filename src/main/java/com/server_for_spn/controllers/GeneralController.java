package com.server_for_spn.controllers;

import com.server_for_spn.dao.ExceptionsDao;
import com.server_for_spn.entity.Exceptions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by Victor on 21.09.2018.
 */
@RestController
public class GeneralController {





    @Autowired
    private ExceptionsDao exceptionsDao;



    @PostMapping("/recordExeptionInfo")
    public ResponseEntity<?> recordException(@RequestBody Exceptions body){
        System.out.println(body.toString());
        exceptionsDao.save(body);
        return new ResponseEntity<>(HttpStatus.OK);
    }



}
