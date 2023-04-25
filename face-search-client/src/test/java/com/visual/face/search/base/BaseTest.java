package com.visual.face.search.base;

import java.io.File;
import java.util.Map;
import java.util.TreeMap;

public abstract class BaseTest {

    public static Map<String, String> getImagePathMap(String imagePath){
        Map<String, String> map = new TreeMap<>();
        File file = new File(imagePath);
        if(file.isFile()){
            map.put(file.getName(), file.getAbsolutePath());
        }else if(file.isDirectory()){
            for(File tmpFile : file.listFiles()){
                map.putAll(getImagePathMap(tmpFile.getPath()));
            }
        }
        return map;
    }

}
