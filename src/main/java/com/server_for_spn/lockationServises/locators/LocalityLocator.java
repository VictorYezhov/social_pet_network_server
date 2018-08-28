package com.server_for_spn.lockationServises.locators;

import com.server_for_spn.lockationServises.CacheFactory;
import com.server_for_spn.lockationServises.models.LocationLevel;
import com.server_for_spn.lockationServises.models.LocationResponse;
import com.server_for_spn.lockationServises.models.CoordinatesInfo;
import com.server_for_spn.lockationServises.models.UserAddress;
import net.jodah.expiringmap.ExpiringMap;
import org.ehcache.Cache;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * Created by Victor on 17.08.2018.
 */
public class LocalityLocator extends AbstractLocator {

    private Cache<Long, CoordinatesInfo> region;


    private static LocationResponse locationResponseFail = new LocationResponse(false, "LocalityLocator: Sub Locality  null");
    private static LocationResponse locationResponseOK = new LocationResponse(true, "OK");


    private Map<Long, Long> ids;


    public LocalityLocator(String name) {
        super(name);
        region = CacheFactory.createCacheForUsers(name);
        ids = ExpiringMap.builder()
                .expiration(10, TimeUnit.SECONDS)
                .build();
    }

    @Override
    public Map<Long, CoordinatesInfo> getUsersInThisLocation() {
        Map<Long, CoordinatesInfo> coordinatesMap = new HashMap<>();
        coordinatesMap.putAll(region.getAll(ids.keySet()));
        if(!subLocators.isEmpty()){
            subLocators.forEach((k, v) -> coordinatesMap.putAll(v.getUsersInThisLocation()));
        }
        return coordinatesMap;
    }

    @Override
    public LocationLevel getLocatorLevel() {
        return LocationLevel.LOCALITY;
    }

    @Override
    public LocationResponse addUserToLocation(UserAddress userAddress) {

        if(userAddress.getmSubLocality()==null){
            CoordinatesInfo coordinatesInfo = new CoordinatesInfo();
            if(!userAddress.ismHasLatitude() || !userAddress.ismHasLongitude()){
                System.out.println("LocalityLocator: Lat or Long  is null");
                return locationResponseFail;
            }
            coordinatesInfo.setLongitude(userAddress.getmLongitude());
            coordinatesInfo.setLatitude(userAddress.getmLatitude());
            coordinatesInfo.setAttitude(userAddress.getAttitude());
            region.put(userAddress.getUserId(), coordinatesInfo);
            ids.put(userAddress.getUserId(), userAddress.getUserId());
            return locationResponseOK;
        }else  {
            Locator locator = subLocators.get(userAddress.getmSubLocality());
            if(locator!=null){
                return locator.addUserToLocation(userAddress);
            }
            locator = new SubLocalityLocator(userAddress.getmSubLocality());
            subLocators.put(userAddress.getmSubLocality(), locator);
            return locator.addUserToLocation(userAddress);
        }
    }


    @Override
    public Map<Long, CoordinatesInfo> getUsersNearMe(UserAddress userAddress) {
        if(userAddress.getmSubLocality() != null && subLocators.containsKey(userAddress.getmSubLocality())){
            return subLocators.get(userAddress.getmSubLocality()).getUsersNearMe(userAddress);
        }
        if (userAddress.getmSubLocality() == null){
            return region.getAll(ids.keySet());
        }
        if(userAddress.getmSubLocality() != null){
            addUserToLocation(userAddress);
            subLocators.get(userAddress.getmSubLocality()).getUsersNearMe(userAddress);
        }
        return null;
    }

    @Override
    public String snapShoot() {
        StringBuilder builder = new StringBuilder();
        builder.append("\nLocality: ");
        builder.append(locatorName);
        if(!ids.isEmpty()){
            builder.append("\nUsers:");
            getUsersInThisLocation().forEach((k, v) -> {builder.append("\n");builder.append(k); builder.append("\n");
            builder.append("lat: ");
            builder.append(v.getLatitude());
            builder.append("long: ");
            builder.append(v.getLongitude());});
        }else {
            subLocators.forEach((k, v) -> {builder.append(v.snapShoot()); builder.append("\n");});
        }

        return builder.toString();
    }
}
