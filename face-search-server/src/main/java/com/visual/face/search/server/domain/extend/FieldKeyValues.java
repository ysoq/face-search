package com.visual.face.search.server.domain.extend;

import java.util.ArrayList;
import java.util.Arrays;

public class FieldKeyValues extends ArrayList<FieldKeyValue>{

    public static FieldKeyValues build(){
        return new FieldKeyValues();
    }

    public FieldKeyValues add(FieldKeyValue ...keyValue){
        this.addAll(Arrays.asList(keyValue));
        return this;
    }

}
