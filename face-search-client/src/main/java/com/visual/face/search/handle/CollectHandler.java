package com.visual.face.search.handle;

import java.util.Map;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

import com.visual.face.search.common.Api;
import com.visual.face.search.http.HttpClient;
import com.visual.face.search.model.*;


public class CollectHandler extends BaseHandler<CollectHandler>{

    /**实例对象**/
    private final static Map<String, CollectHandler> ins = new ConcurrentHashMap<>();

    /**
     * 构建集合对象
     * @param serverHost        服务地址
     * @param namespace         命名空间
     * @param collectionName    集合名称
     * @return
     */
    public static CollectHandler build(String serverHost, String namespace, String collectionName){
        String key = serverHost+"|_|"+namespace + "|_|" + collectionName;
        if(!ins.containsKey(key)){
            synchronized (CollectHandler.class){
                if(!ins.containsKey(key)){
                    ins.put(key, new CollectHandler().setServerHost(serverHost)
                            .setNamespace(namespace).setCollectionName(collectionName));
                }
            }
        }
        return ins.get(key);
    }

    /**
     * 创建一个集合，
     * @param collect 集合的定义信息
     * @return  是否创建成功
     */
    public Response<Boolean> createCollect(Collect collect){
        CollectReq collectReq = CollectReq
                .build(this.namespace, this.collectionName)
                .setCollectionComment(collect.getCollectionComment())
                .setMaxDocsPerSegment(collect.getMaxDocsPerSegment())
                .setSampleColumns(collect.getSampleColumns())
                .setFaceColumns(collect.getFaceColumns())
                .setShardsNum(collect.getShardsNum())
                .setStorageFaceInfo(collect.getStorageFaceInfo())
                .setStorageEngine(collect.getStorageEngine());
        return HttpClient.post(Api.getUrl(this.serverHost, Api.collect_create), collectReq);
    }

    /**
     *根据命名空间，集合名称删除集合
     * @return  是否删除成功
     */
    public Response<Boolean> deleteCollect(){
        MapParam param = MapParam.build()
                .put("namespace", namespace)
                .put("collectionName", collectionName);
        return HttpClient.get(Api.getUrl(this.serverHost, Api.collect_delete), param);
    }

    /**
     *根据命名空间，集合名称查看集合信息
     * @return  集合信息
     */
    public Response<CollectRep> getCollect(){
        MapParam param = MapParam.build()
                .put("namespace", namespace)
                .put("collectionName", collectionName);
        return HttpClient.get(Api.getUrl(this.serverHost, Api.collect_get), param);
    }

    /**
     *根据命名空间查看集合列表
     * @return  集合列表
     */
    public Response<List<CollectRep>> collectList(){
        MapParam param = MapParam.build().put("namespace", namespace);
        return HttpClient.get(Api.getUrl(this.serverHost, Api.collect_list), param);
    }

}
