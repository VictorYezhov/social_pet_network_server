package com.server_for_spn.lockationServises.locators;

import com.server_for_spn.lockationServises.models.LocationLevel;
import com.server_for_spn.lockationServises.models.LocationResponse;
import com.server_for_spn.lockationServises.models.UserAddress;

/**
 * Created by Victor on 17.08.2018.
 */
public class CountryLevelLocator extends AbstractLocator {



    private static LocationResponse locationResponse = new LocationResponse(false, "CountryLevelLocator: Admin Area null");

    public CountryLevelLocator(String locatorName) {
        super(locatorName);
    }

    @Override
    public LocationLevel getLocatorLevel() {
        return LocationLevel.COUNTRY;
    }

    @Override
    public LocationResponse addUserToLocation(UserAddress userAddress) {
        if(userAddress.getmAdminArea()!= null){
            Locator locator = getSubLocation(userAddress.getmAdminArea());
            if(locator != null){
                return locator.addUserToLocation(userAddress);
            }
            locator = new AdminAreaLocator(userAddress.getmAdminArea());
            addSubLocator(userAddress.getmAdminArea(), locator);
            return locator.addUserToLocation(userAddress);
        }
        return locationResponse;
    }


}
