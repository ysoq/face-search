package com.visual.face.search.engine.utils;

import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;

public class VectorUtils {

    public static List<Float> convertVector(float[] vectors){
        List<Float> list = new ArrayList<>(vectors.length);
        for(int i=0; i< vectors.length; i++){
            list.add(vectors[i]);
        }
        return list;
    }

    public static List<List<Float>> convertVector(float[][] vectors){
        List<List<Float>> list = new ArrayList<>(vectors.length);
        for(int i=0; i< vectors.length; i++){
            list.add(convertVector(vectors[i]));
        }
        return list;
    }

    public static ByteBuffer convertByteBuffer(String str){
        byte [] bytes = str.getBytes();
        int byteCount = bytes.length;
        ByteBuffer vector = ByteBuffer.allocate(byteCount);
        for (int i = 0; i < byteCount; ++i) {
            vector.put(bytes[i]);
        }
        return vector;
    }

}
