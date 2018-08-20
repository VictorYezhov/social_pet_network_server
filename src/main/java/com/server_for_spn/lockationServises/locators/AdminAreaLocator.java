package com.server_for_spn.lockationServises.locators;

import com.server_for_spn.lockationServises.models.Coordinates;
import com.server_for_spn.lockationServises.models.LocationLevel;
import com.server_for_spn.lockationServises.models.LocationResponse;
import com.server_for_spn.lockationServises.models.UserAddress;

import java.util.Map;

/**
 * Created by Victor on 17.08.2018.
 */
public class AdminAreaLocator extends AbstractLocator  {


    private static LocationResponse locationResponse = new LocationResponse(false, "AdminAreaLocator :Sub Admin Area and Locality null");


    public AdminAreaLocator(String locatorName) {
        super(locatorName);
    }

    @Override
    public LocationLevel getLocatorLevel() {
        return LocationLevel.ADMIN_AREA;
    }

    @Override
    public LocationResponse addUserToLocation(UserAddress userAddress) {
        if(userAddress.getmSubAdminArea()!=null){
            Locator locator = subLocators.get(userAddress.getmSubAdminArea());
            if(locator!=null){
                return locator.addUserToLocation(userAddress);
            }
            locator = new SubAdminAreaLocator(userAddress.getmSubAdminArea());
            addSubLocator(userAddress.getmSubAdminArea(), locator);
            return locator.addUserToLocation(userAddress);
        }else if(userAddress.getmLocality() != null) {
            Locator locator = subLocators.get(userAddress.getmLocality());
            if(locator != null)
                return locator.addUserToLocation(userAddress);
            locator = new LocalityLocator(userAddress.getmLocality());
            subLocators.put(userAddress.getmLocality(), locator);
            return locator.addUserToLocation(userAddress);
        }
        return locationResponse;
    }

    @Override
    public Map<Long, Coordinates> getUsersNearMe(UserAddress userAddress) {
        Locator locator = null;
        if(userAddress.getmSubAdminArea()!= null)
            locator = subLocators.get(userAddress.getmSubAdminArea());
        if(locator != null) {
            System.out.println("Getting users from: "+userAddress.getmSubAdminArea());
            return locator.getUsersNearMe(userAddress);
        }
        if(userAddress.getmLocality()!= null)
        locator = subLocators.get(userAddress.getmLocality());
        if(locator != null) {
            System.out.println("Getting users from: "+userAddress.getmLocality());
            return locator.getUsersNearMe(userAddress);
        }
        System.out.println("Getting users from: "+locatorName+" null");
        return null;

    }

}
