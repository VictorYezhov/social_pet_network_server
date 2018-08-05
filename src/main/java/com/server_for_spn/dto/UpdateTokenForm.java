package com.server_for_spn.dto;

import com.server_for_spn.enums.DeviceOS;

/**
 * Created by Victor on 05.08.2018.
 */
public class UpdateTokenForm {
    private String id;
    private String token;
    private DeviceOS deviceOS;

    public void setId(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public String getToken() {
        return token;
    }

    public DeviceOS getDeviceOS() {
        return deviceOS;
    }

    public void setDeviceOS(DeviceOS deviceOS) {
        this.deviceOS = deviceOS;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
