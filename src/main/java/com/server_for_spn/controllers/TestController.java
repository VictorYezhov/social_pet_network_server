package com.server_for_spn.controllers;


import com.google.api.services.storage.Storage;
import com.server_for_spn.dao.TestDAO;
import com.server_for_spn.dto.RegistrationForm;
import com.server_for_spn.entity.Breed;
import com.server_for_spn.entity.TestEntity;
import com.server_for_spn.enums.NotificationType;
import com.server_for_spn.enums.PetType;
import com.server_for_spn.service.BreedService;
import com.server_for_spn.service.FCMService;

import org.apache.ignite.Ignite;
import org.apache.ignite.IgniteCache;
import org.apache.ignite.Ignition;
import org.apache.ignite.cache.CacheEntry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;
import java.util.concurrent.ExecutionException;

/**
 * Created by Victor on 27.07.2018.
 */
@RestController
public class TestController {


    @Autowired
    private BreedService breedService;

    @Autowired
    FCMService fcmService;

    @Autowired
    TestDAO  testDAO;


    /**
     * Only for some tests
     * @return
     */
    @GetMapping("/api/test")
    public  Map<Long, TestEntity> test(){

        StringBuilder builder = new StringBuilder();




        Ignite ignite = Ignition.start();
        IgniteCache<Long, TestEntity > region = ignite.getOrCreateCache("myCache");


        List<TestEntity> testEntities = new ArrayList<>();
        for(int i =0; i< 1000; i++){
            TestEntity testEntity = new TestEntity();
            testEntity.setId((long)i);
            testEntity.setText("text"+i);
            testEntities.add(testEntity);
        }
        int i=0;

        long timeInputStartInMemory = System.currentTimeMillis();
        Set<Long> ids = new HashSet<>();
        for (TestEntity r:testEntities
             ) {
            ids.add(r.getId());
            region.put(r.getId(), r);
        }
        long timeInputEndInMemory = System.currentTimeMillis();



        long timeGettingOneInMemoryStart = System.currentTimeMillis();


        long timeGettingOneInMemoryEnd = System.currentTimeMillis();


        TestEntity str = region.get(456L);


        Map<Long, TestEntity> test =  region.getAll(ids);



       System.out.println(str);







        return test;
    }


}
