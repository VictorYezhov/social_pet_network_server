package com.server_for_spn.lockationServises.locators;

import com.server_for_spn.lockationServises.models.CoordinatesInfo;
import com.server_for_spn.lockationServises.models.LocationLevel;
import com.server_for_spn.lockationServises.models.LocationResponse;
import com.server_for_spn.lockationServises.models.UserAddress;

import java.util.Map;

/**
 * Created by Victor on 17.08.2018.
 */
public class SubAdminAreaLocator extends AbstractLocator implements Locator {

    private static LocationResponse locationResponse = new LocationResponse(false, "SubAdminAreaLocator: Locality null");


    public SubAdminAreaLocator(String locatorName) {
        super(locatorName);
    }

    @Override
    public LocationLevel getLocatorLevel() {
        return LocationLevel.SUB_ADMIN_AREA;
    }

    @Override
    public LocationResponse addUserToLocation(UserAddress userAddress) {
        if(userAddress.getmLocality() == null){
            return locationResponse;
        }else {
            Locator locator = subLocators.get(userAddress.getmLocality());
            if(locator != null){
                return locator.addUserToLocation(userAddress);
            }
            locator = new LocalityLocator(userAddress.getmLocality());
            subLocators.put(userAddress.getmLocality(),locator);
            return  locator.addUserToLocation(userAddress);
        }
    }



    @Override
    public Map<Long, CoordinatesInfo> getUsersNearMe(UserAddress userAddress) {
        Locator locator = subLocators.get(userAddress.getmLocality());
        if(locator != null){
            return locator.getUsersNearMe(userAddress);
        }
        return null;

    }

}
