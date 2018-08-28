package com.server_for_spn.lockationServises.locators;

import com.server_for_spn.lockationServises.models.CoordinatesInfo;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by Victor on 17.08.2018.
 */
public  abstract class AbstractLocator implements Locator {

    protected Map<String, Locator> subLocators = new ConcurrentHashMap<>();
    protected String locatorName;

    public AbstractLocator(String locatorName) {
        this.locatorName = locatorName;
    }

    @Override
    public Locator getSubLocation(String name) {
        return subLocators.get(name);
    }

    @Override
    public Locator addSubLocator(String name, Locator locator) {
        if(!subLocators.containsKey(name)) {
            subLocators.put(name, locator);
            return locator;
        }else {
            return subLocators.get(name);
        }
    }


    @Override
    public Map<Long, CoordinatesInfo> getUsersInThisLocation() {
        Map<Long, CoordinatesInfo> coordinatesMap = new HashMap<>();
        subLocators.forEach((k, v) -> coordinatesMap.putAll(v.getUsersInThisLocation()));
        return coordinatesMap;
    }

    @Override
    public long getAmountOfUsersInThisLocation() {
        final long[] amount = {0};
        subLocators.forEach((k, v) -> amount[0] += v.getAmountOfUsersInThisLocation());
        return amount[0];
    }

    @Override
    public String snapShoot() {
        StringBuilder builder = new StringBuilder();
        builder.append("\n");
        builder.append(getLocatorLevel());
        builder.append(": ");
        builder.append(locatorName);
        subLocators.forEach((k, v) -> {builder.append(v.snapShoot()); builder.append("\n");});
        return builder.toString();
    }

}
