package com.visual.face.search;

import com.visual.face.search.handle.CollectHandler;
import com.visual.face.search.handle.FaceHandler;
import com.visual.face.search.handle.SampleHandler;
import com.visual.face.search.handle.SearchHandler;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class FaceSearch {
    /**服务地址**/
    private String serverHost;
    /**命名空间**/
    private String namespace;
    /**集合名称**/
    private String collectionName;
    /**实例对象**/
    private final static Map<String, FaceSearch> ins = new ConcurrentHashMap<>();

    /**
     * 构建集合对象
     * @param serverHost        服务地址
     * @param namespace         命名空间
     * @param collectionName    集合名称
     * @return
     */
    private FaceSearch(String serverHost, String namespace, String collectionName){
        this.serverHost = serverHost;
        this.namespace = namespace;
        this.collectionName = collectionName;
    }

    /**
     * 构建集合对象
     * @param serverHost        服务地址
     * @param namespace         命名空间
     * @param collectionName    集合名称
     * @return
     */
    public static FaceSearch build (String serverHost,String namespace, String collectionName){
        String key = serverHost+"|_|"+namespace + "|_|" + collectionName;
        if(!ins.containsKey(key)){
            synchronized (FaceSearch.class){
                if(!ins.containsKey(key)){
                    ins.put(key, new FaceSearch(serverHost, namespace, collectionName));
                }
            }
        }
        return ins.get(key);
    }

    /**
     * 集合操作对象
     * @return CollectHandler
     */
    public CollectHandler collect(){
        return CollectHandler.build(serverHost, namespace, collectionName);
    }

    /**
     * 样本操作对象
     * @return SampleHandler
     */
    public SampleHandler sample(){
        return SampleHandler.build(serverHost, namespace, collectionName);
    }

    /**
     * 人脸操作对象
     * @return FaceHandler
     */
    public FaceHandler face(){
        return FaceHandler.build(serverHost, namespace, collectionName);
    }

    /**
     * 人脸搜索
     * @return FaceHandler
     */
    public SearchHandler search(){
        return SearchHandler.build(serverHost, namespace, collectionName);
    }

}
