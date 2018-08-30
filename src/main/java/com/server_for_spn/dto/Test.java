package com.server_for_spn.dto;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Victor on 29.08.2018.
 */
public class Test {
    private Map<String, String>  map;

    public Test(Map<String, String> stringStringMap){
        map = stringStringMap;
    }
    public Map<String, String> getMap() {
        return map;
    }

    public void setMap(Map<String, String> map) {
        this.map = map;
    }
}
