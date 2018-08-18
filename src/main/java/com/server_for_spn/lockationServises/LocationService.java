package com.server_for_spn.lockationServises;

import com.server_for_spn.lockationServises.locators.CountryLevelLocator;
import com.server_for_spn.lockationServises.locators.Locator;
import com.server_for_spn.lockationServises.models.Coordinates;
import com.server_for_spn.lockationServises.models.LocationResponse;
import com.server_for_spn.lockationServises.models.UserAddress;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by Victor on 17.08.2018.
 */
@Service
@Scope(value = "singleton")
public class LocationService {


    private static Map<String, Locator> countries = new ConcurrentHashMap<>();



    public LocationResponse saveLocation(UserAddress userAddress){

        if(countries.containsKey(userAddress.getmCountryCode())){
           return countries.get(userAddress.getmCountryCode()).addUserToLocation(userAddress);
        }

        Locator locator = new CountryLevelLocator(userAddress.getmCountryName());
        countries.put(userAddress.getmCountryCode(), locator);
        return locator.addUserToLocation(userAddress);
    }


    public Map<Long, Coordinates> getUsersNearMe(UserAddress userAddress){
        if(countries.containsKey(userAddress.getmCountryCode())){
            return countries.get(userAddress.getmCountryCode()).getUsersNearMe(userAddress);
        }
        Locator locator = new CountryLevelLocator(userAddress.getmCountryName());
        countries.put(userAddress.getmCountryCode(), locator);
        locator.addUserToLocation(userAddress);
        return locator.getUsersNearMe(userAddress);
    }





    public String realTimeSnapShoot(){

        StringBuilder builder = new StringBuilder();


        for (Map.Entry<String, Locator> l:
             countries.entrySet()) {
            builder.append("----------------------------");
            builder.append(l.getValue().snapShoot());
        }


       return builder.toString();

    }








}
