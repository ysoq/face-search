package com.visual.face.search.model;

import java.util.HashMap;

public class MapParam extends HashMap<String, Object> {

    public static MapParam build(){
        return new MapParam();
    }


    public MapParam put(String key, Object value){
        super.put(key, value);
        return this;
    }

}
