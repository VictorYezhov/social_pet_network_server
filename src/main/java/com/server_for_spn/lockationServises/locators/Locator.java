package com.server_for_spn.lockationServises.locators;

import com.server_for_spn.lockationServises.models.LocationLevel;
import com.server_for_spn.lockationServises.models.LocationResponse;
import com.server_for_spn.lockationServises.models.Coordinates;
import com.server_for_spn.lockationServises.models.UserAddress;

import java.util.Map;

/**
 * Created by Victor on 17.08.2018.
 */
public interface Locator {
    LocationLevel getLocatorLevel();
    Locator getSubLocation(String name);
    Map<Long, Coordinates> getUsersInThisLocation();
    LocationResponse addUserToLocation(UserAddress userAddress);
    long getAmountOfUsersInThisLocation();
    Locator addSubLocator(String name, Locator locator);

    String snapShoot();

}
