package com.visual.face.search.model;

public class KeyValue {

    private String key;
    private Object value;

    public KeyValue(){}

    public KeyValue(String key, Object value) {
        this.key = key;
        this.value = value;
    }

    public static KeyValue build(String key, String value){
        return new KeyValue(key, value);
    }

    public static KeyValue build(String key, Boolean value){
        return new KeyValue(key, value);
    }

    public static KeyValue build(String key, Integer value){
        return new KeyValue(key, value);
    }

    public static KeyValue build(String key, Float value){
        return new KeyValue(key, value);
    }

    public static KeyValue build(String key, Double value){
        return new KeyValue(key, value);
    }

    public static KeyValue build(String key, Object value){
        return new KeyValue(key, value);
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public Object getValue() {
        return value;
    }

    public void setValue(Object value) {
        this.value = value;
    }

    public String toStringValue(){
        if(null == value){
            return null;
        }
        if(value instanceof String){
            return value.toString();
        }else{
            return String.valueOf(value);
        }
    }

    public Boolean toBooleanValue(){
        if(null == value){
            return null;
        }
        if(value instanceof Boolean){
            return (Boolean)value;
        }else{
            return Boolean.parseBoolean(String.valueOf(value));
        }
    }

    public Integer toIntegerValue(){
        if(null == value){
            return null;
        }
        if(value instanceof Integer){
            return (Integer)value;
        }else{
            return Integer.parseInt(String.valueOf(value));
        }
    }

    public Float toFloatValue(){
        if(null == value){
            return null;
        }
        if(value instanceof Float){
            return (Float)value;
        }else{
            return Float.parseFloat(String.valueOf(value));
        }
    }

    public Double toDoubleValue(){
        if(null == value){
            return null;
        }
        if(value instanceof Double){
            return (Double)value;
        }else{
            return Double.parseDouble(String.valueOf(value));
        }
    }

}
