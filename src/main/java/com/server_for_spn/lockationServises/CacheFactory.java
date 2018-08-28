package com.server_for_spn.lockationServises;

import com.server_for_spn.lockationServises.models.CoordinatesInfo;
import org.ehcache.Cache;
import org.ehcache.CacheManager;
import org.ehcache.config.builders.CacheConfigurationBuilder;
import org.ehcache.config.builders.CacheManagerBuilder;
import org.ehcache.config.builders.ExpiryPolicyBuilder;
import org.ehcache.config.builders.ResourcePoolsBuilder;
import org.ehcache.config.units.MemoryUnit;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.time.Duration;

/**
 * Created by Victor on 17.08.2018.
 */
@Service
@Scope(value = "singleton")
public class CacheFactory {


    private  static CacheManager cacheManager;



    @PostConstruct
    public void init(){
        cacheManager =  CacheManagerBuilder.newCacheManagerBuilder().build(true);
    }


    public  static Cache<Long, CoordinatesInfo> createCacheForUsers(String nameOfLocality){
        return cacheManager.createCache(nameOfLocality+"  users", CacheConfigurationBuilder.newCacheConfigurationBuilder(Long.class, CoordinatesInfo.class,
                ResourcePoolsBuilder.newResourcePoolsBuilder()
                        .offheap(15, MemoryUnit.MB))
                .withExpiry(ExpiryPolicyBuilder.timeToIdleExpiration(Duration.ofSeconds(10))));
    }


}
