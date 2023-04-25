package com.visual.face.search.server.utils;

import java.util.List;
import java.util.ArrayList;
import java.util.concurrent.*;

public class VTableCache {

    private static ConcurrentMap<String, Long> vectorTables = new ConcurrentHashMap<>();

    //超过10分钟的就清除了
    static {
        ScheduledExecutorService executor = Executors.newSingleThreadScheduledExecutor();
        executor.scheduleAtFixedRate(() -> {
            vectorTables.entrySet().removeIf(entry ->  System.currentTimeMillis() - entry.getValue() > 10 * 60 * 1000);
        }, 0, 60, TimeUnit.SECONDS);
    }

    public static void put(String vectorTable){
        vectorTables.put(vectorTable, System.currentTimeMillis());
    }

    public static boolean remove(String vectorTable){
        if(vectorTables.containsKey(vectorTable)){
            return null != vectorTables.remove(vectorTable);
        }else{
            return false;
        }
    }

    public static List<String> getVectorTables(){
        return new ArrayList<>(vectorTables.keySet());
    }

}
