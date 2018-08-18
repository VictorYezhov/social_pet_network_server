package com.server_for_spn.controllers;



import com.google.api.services.storage.Storage;


import com.server_for_spn.dto.RegistrationForm;
import com.server_for_spn.entity.Breed;
import com.server_for_spn.entity.TestEntity;
import com.server_for_spn.enums.NotificationType;
import com.server_for_spn.enums.PetType;
import com.server_for_spn.service.BreedService;
import com.server_for_spn.service.FCMService;

import org.ehcache.Cache;
import org.ehcache.CacheManager;
import org.ehcache.config.builders.CacheConfigurationBuilder;
import org.ehcache.config.builders.CacheManagerBuilder;
import org.ehcache.config.builders.ExpiryPolicyBuilder;
import org.ehcache.config.builders.ResourcePoolsBuilder;
import org.ehcache.config.units.MemoryUnit;
import org.ehcache.expiry.Expirations;
import org.ehcache.sizeof.impl.JvmInformation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.Duration;
import java.util.*;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

/**
 * Created by Victor on 27.07.2018.
 */
@RestController
public class TestController {


    @Autowired
    private BreedService breedService;


    @Autowired
    FCMService fcmService;



    /**
     * Only for some tests
     * @return
     */
    @GetMapping("/api/test")
    public  String test(){

        StringBuilder builder = new StringBuilder();



        CacheManager cacheManager = CacheManagerBuilder.newCacheManagerBuilder().withCache("preConfigured",
                CacheConfigurationBuilder.newCacheConfigurationBuilder(Long.class, TestEntity.class,
                        ResourcePoolsBuilder.newResourcePoolsBuilder()
                                .offheap(2000, MemoryUnit.MB))
                        .withExpiry(ExpiryPolicyBuilder.timeToIdleExpiration(Duration.ofSeconds(10)))
        )
                .build(true);
        Cache<Long, TestEntity> region =
                cacheManager.getCache("preConfigured", Long.class, TestEntity.class);

        TestEntity testEntity = new TestEntity();
        testEntity.setId(1L);
        testEntity.setText("text");

        region.put(1L, testEntity);

        TestEntity first = region.get(1L);

        TestEntity second = region.get(1L);

        builder.append("first:\n");
        builder.append(first.getText());
        builder.append("\nsecond:\n");
        builder.append(second.getText());
        return builder.toString();


//        Set<Long> ids = new HashSet<>();
//
//        List<TestEntity> testEntities = new ArrayList<>();
//        for(int i =0; i< 1000; i++){
//            TestEntity testEntity = new TestEntity();
//            testEntity.setId((long)i);
//            ids.add((long)i);
//            testEntity.setText("text"+i);
//            testEntities.add(testEntity);
//        }
//        int i=0;
//
//        long timeInputStartInMemory = System.currentTimeMillis();
//        for (TestEntity r:testEntities
//             ) {
//            region.put(r.getId(), r);
//        }
//        long timeInputEndInMemory = System.currentTimeMillis();
//
//        long timeInputStartInDISK = System.currentTimeMillis();
//        for (TestEntity r:testEntities
//                ) {
//            testDAO.save(r);
//        }
//        long timeInputEndInDISK = System.currentTimeMillis();
//
//        TestEntity t = new TestEntity();
//        t.setId(1001L);
//        t.setText("TEXT1001");
//        long timeInputStartInMemoryOne = System.currentTimeMillis();
//        region.put(1001L, t);
//        long timeInputEndInMemoryOne = System.currentTimeMillis();
//
//        long timeInputStartInDISKOne = System.currentTimeMillis();
//        testDAO.save(t);
//        long timeInputEndInDISKOne = System.currentTimeMillis();
//
//
//        long timeGettingOneInMemoryStart = System.currentTimeMillis();
//
//        TestEntity fromMemory = region.get(656L);
//
//        long timeGettingOneInMemoryEnd = System.currentTimeMillis();
//
//        long timeGettingOneInDISKStart = System.currentTimeMillis();
//
//        TestEntity fromDISK =  testDAO.findById(656L).get();
//
//        long timeGettingOneInDISKEnd = System.currentTimeMillis();
//
//        long timeGettingALLInMemoryStart = System.currentTimeMillis();
//
//       Map<Long, TestEntity> elems =region.getAll(ids);
//
//        long timeGettingALLInMemoryEnd = System.currentTimeMillis();
//
//        long timeGettingALLInDISKStart = System.currentTimeMillis();
//
//       List<TestEntity> elemsfromDisk = testDAO.findAll();
//
//        long timeGettingALLInDISKEnd = System.currentTimeMillis();
//
//
//
//        builder.append("Time inserting 1000 elements to Ehcashe:\n");
//        builder.append(TimeUnit.MILLISECONDS.toSeconds(timeInputEndInMemory-timeInputStartInMemory));
//        builder.append(" seconds\n");
//        builder.append(timeInputEndInMemory-timeInputStartInMemory);
//        builder.append(" mils\n\n");
//
//        builder.append("Time inserting 1000 elements to MySQL:\n");
//        builder.append(TimeUnit.MILLISECONDS.toSeconds(timeInputEndInDISK-timeInputStartInDISK));
//        builder.append(" seconds\n");
//        builder.append(timeInputEndInDISK-timeInputStartInDISK);
//        builder.append(" mils\n\n");
//
//
//        builder.append("Time inserting 1 elements to Ehcashe:\n");
//        builder.append(TimeUnit.MILLISECONDS.toSeconds(timeInputEndInMemoryOne-timeInputStartInMemoryOne));
//        builder.append(" seconds\n");
//        builder.append(timeInputEndInMemoryOne-timeInputStartInMemoryOne);
//        builder.append(" mils\n\n");
//
//
//        builder.append("Time inserting 1 elements to MySQL:\n");
//        builder.append(TimeUnit.MILLISECONDS.toSeconds(timeInputEndInDISKOne-timeGettingOneInMemoryStart));
//        builder.append(" seconds\n");
//        builder.append(timeInputEndInDISKOne-timeGettingOneInMemoryStart);
//        builder.append(" mils\n\n");
//
//
//
//        builder.append("Time to get 1 elements from Ehcashe:\n");
//        builder.append(TimeUnit.MILLISECONDS.toSeconds(timeGettingOneInMemoryEnd-timeInputStartInMemoryOne));
//        builder.append(" seconds\n");
//        builder.append(timeGettingOneInMemoryEnd-timeInputStartInMemoryOne);
//        builder.append(" mils\n\n");
//
//
//        builder.append("Time to get 1 elements from mySQL:\n");
//        builder.append(TimeUnit.MILLISECONDS.toSeconds(timeGettingOneInDISKEnd-timeGettingOneInDISKStart));
//        builder.append(" seconds\n");
//        builder.append(timeGettingOneInDISKEnd-timeGettingOneInDISKStart);
//        builder.append(" mils\n\n");
//
//        builder.append("Time to get 1000 elements from Ehcashe:\n");
//        builder.append(TimeUnit.MILLISECONDS.toSeconds(timeGettingALLInMemoryEnd-timeGettingALLInMemoryStart));
//        builder.append(" seconds\n");
//        builder.append(timeGettingALLInMemoryEnd-timeGettingALLInMemoryStart);
//        builder.append(" mils\n\n");
//
//
//        builder.append("Time to get 1000 elements from mySQL:\n");
//        builder.append(TimeUnit.MILLISECONDS.toSeconds(timeGettingALLInDISKEnd-timeGettingALLInDISKStart));
//        builder.append(" seconds\n");
//        builder.append(timeGettingALLInDISKEnd-timeGettingALLInDISKStart);
//        builder.append(" mils\n\n");
//        return builder.toString();


    }


}
