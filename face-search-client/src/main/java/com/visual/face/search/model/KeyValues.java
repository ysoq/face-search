package com.visual.face.search.model;

import java.util.ArrayList;
import java.util.Arrays;

public class KeyValues extends ArrayList<KeyValue>{

    public static KeyValues build(){
        return new KeyValues();
    }

    public KeyValues add(KeyValue...keyValue){
        this.addAll(Arrays.asList(keyValue));
        return this;
    }

    public String getString(String key){
        for(KeyValue keyValue : this){
            if(key.equalsIgnoreCase(keyValue.getKey())){
                return keyValue.toStringValue();
            }
        }
        return null;
    }

    public Boolean getBoolean(String key){
        for(KeyValue keyValue : this){
            if(key.equalsIgnoreCase(keyValue.getKey())){
                return keyValue.toBooleanValue();
            }
        }
        return null;
    }

    public Integer getInteger(String key){
        for(KeyValue keyValue : this){
            if(key.equalsIgnoreCase(keyValue.getKey())){
                return keyValue.toIntegerValue();
            }
        }
        return null;
    }

    public Float getFloat(String key){
        for(KeyValue keyValue : this){
            if(key.equalsIgnoreCase(keyValue.getKey())){
                return keyValue.toFloatValue();
            }
        }
        return null;
    }

    public Double getDouble(String key){
        for(KeyValue keyValue : this){
            if(key.equalsIgnoreCase(keyValue.getKey())){
                return keyValue.toDoubleValue();
            }
        }
        return null;
    }

    public Object getObject(String key){
        for(KeyValue keyValue : this){
            if(key.equalsIgnoreCase(keyValue.getKey())){
                return keyValue.getValue();
            }
        }
        return null;
    }

}
