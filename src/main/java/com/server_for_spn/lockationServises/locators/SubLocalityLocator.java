package com.server_for_spn.lockationServises.locators;

import com.server_for_spn.lockationServises.CacheFactory;
import com.server_for_spn.lockationServises.models.LocationLevel;
import com.server_for_spn.lockationServises.models.LocationResponse;
import com.server_for_spn.lockationServises.models.Coordinates;
import com.server_for_spn.lockationServises.models.UserAddress;
import net.jodah.expiringmap.ExpiringMap;
import org.ehcache.Cache;

import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * Created by Victor on 17.08.2018.
 */
public class SubLocalityLocator extends AbstractLocator {
    private Cache<Long, Coordinates> region;
    private Map<Long, Long> ids;

    private static LocationResponse locationResponseFail = new LocationResponse(false, "SubLocalityLocator: Lat or Long  is null");

    private static LocationResponse locationResponseOK = new LocationResponse(true, "OK");

    public SubLocalityLocator(String name) {
        super(name);
        region = CacheFactory.createCacheForUsers(name);
        ids = ExpiringMap.builder()
                .expiration(10, TimeUnit.SECONDS)
                .build();
    }

    @Override
    public Map<Long, Coordinates> getUsersInThisLocation() {
        return region.getAll(ids.keySet());
    }

    @Override
    public LocationLevel getLocatorLevel() {
        return LocationLevel.SUB_LOCALITY;
    }

    @Override
    public Locator addSubLocator(String name, Locator locator) {
        return null;
    }

    @Override
    public LocationResponse addUserToLocation(UserAddress userAddress) {
        Coordinates coordinates = new Coordinates();
        if(!userAddress.ismHasLatitude() || !userAddress.ismHasLongitude()){
            System.out.println("LocalityLocator: Lat or Long  is null");
            return locationResponseFail;
        }
        coordinates.setLatitude(userAddress.getmLatitude());
        coordinates.setLongitude(userAddress.getmLatitude());
        region.put(userAddress.getUserId(), coordinates);
        ids.put(userAddress.getUserId(),userAddress.getUserId());
        return locationResponseOK;
    }

    @Override
    public Map<Long, Coordinates> getUsersNearMe(UserAddress userAddress) {
        System.out.println("Returning users from: "+ locatorName);
        return region.getAll(ids.keySet());
    }

    @Override
    public String snapShoot() {
        StringBuilder builder = new StringBuilder();
        builder.append("\nLocality: ");
        builder.append(locatorName);
        if(!ids.isEmpty()){
            builder.append("\nUsers:{");
            getUsersInThisLocation().forEach((k, v) -> {builder.append("user id: ");builder.append(k); builder.append("\n");
                builder.append("lat: ");
                builder.append(v.getLatitude());
                builder.append("long: ");
                builder.append(v.getLongitude());
            builder.append("\n");});
        }
        builder.append("}\n");
        return builder.toString();
    }
}
