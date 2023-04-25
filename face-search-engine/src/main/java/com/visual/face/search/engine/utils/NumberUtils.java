package com.visual.face.search.engine.utils;

import java.text.NumberFormat;
import java.text.ParseException;
import java.util.List;

public class NumberUtils {

    public static  Number getNumber(Object value) {
        if (value != null) {
            if (value instanceof Number) {
                return (Number)value;
            }
            if (value instanceof String) {
                try {
                    String text = (String)value;
                    return NumberFormat.getInstance().parse(text);
                } catch (ParseException var4) {

                }
            }
        }
        return null;
    }

    public static Float getFloat(Object value) {
        Number answer = getNumber(value);
        if (answer == null) {
            return null;
        } else {
            return answer instanceof Float ? (Float)answer : answer.floatValue();
        }
    }


    public static Float[] getFloatArray(Object values) {
        if(null != values){
            if(values.getClass().isArray()){
                return getFloatArray((Object[]) values);
            }else if(values instanceof List){
                return getFloatArray((List)values);
            }else{
                throw new RuntimeException("type error for:"+values.getClass());
            }
        }else{
            return null;
        }
    }

    public static Float[] getFloatArray(List<Object> values) {
        if(null != values){
            Float[] floats = new Float[values.size()];
            for(int i=0; i<floats.length; i++){
                floats[i] = getFloat(values.get(i));
            }
            return floats;
        }else{
            return null;
        }
    }

    public static Float[] getFloatArray(Object[] values) {
        if(null != values){
            Float[] floats = new Float[values.length];
            for(int i=0; i<floats.length; i++){
                floats[i] = getFloat(values[i]);
            }
            return floats;
        }else{
            return null;
        }
    }

}
