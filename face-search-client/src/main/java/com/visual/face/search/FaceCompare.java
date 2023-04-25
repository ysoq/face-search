package com.visual.face.search;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import com.visual.face.search.handle.CompareHandler;

public class FaceCompare {
    /**服务地址**/
    private String serverHost;
    /**实例对象**/
    private final static Map<String, FaceCompare> ins = new ConcurrentHashMap<>();

    /**
     * 构建集合对象
     * @param serverHost        服务地址
     * @return
     */
    private FaceCompare(String serverHost){
        this.serverHost = serverHost;
    }

    /**
     * 构建集合对象
     * @param serverHost        服务地址
     * @return
     */
    public static FaceCompare build (String serverHost){
        String key = serverHost;
        if(!ins.containsKey(key)){
            synchronized (FaceCompare.class){
                if(!ins.containsKey(key)){
                    ins.put(key, new FaceCompare(serverHost));
                }
            }
        }
        return ins.get(key);
    }
    /**
     * 人脸比对操作对象
     * @return CollectHandler
     */
    public CompareHandler compare(){
        return CompareHandler.build(serverHost);
    }


}
