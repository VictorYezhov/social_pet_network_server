package com.server_for_spn.lockationServises.models;

import java.io.Serializable;

/**
 * Created by Victor on 17.08.2018.
 */
public class Coordinates implements Serializable {


    private double latitude;
    private double longitude;


    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

}
