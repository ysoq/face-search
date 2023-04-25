package com.visual.face.search.handle;

import com.alibaba.fastjson.TypeReference;
import com.visual.face.search.common.Api;
import com.visual.face.search.http.HttpClient;
import com.visual.face.search.model.*;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class SampleHandler extends BaseHandler<SampleHandler> {

    /**实例对象**/
    private final static Map<String, SampleHandler> ins = new ConcurrentHashMap<>();

    /**
     * 构建样本对象
     * @param serverHost        服务地址
     * @param namespace         命名空间
     * @param collectionName    集合名称
     * @return
     */
    public static SampleHandler build(String serverHost, String namespace, String collectionName){
        String key = serverHost+"|_|"+namespace + "|_|" + collectionName;
        if(!ins.containsKey(key)){
            synchronized (SampleHandler.class){
                if(!ins.containsKey(key)){
                    ins.put(key, new SampleHandler().setServerHost(serverHost)
                            .setNamespace(namespace).setCollectionName(collectionName));
                }
            }
        }
        return ins.get(key);
    }

    /**
     * 创建一个样本，
     * @param sample 样本的定义信息
     * @return  是否创建成功
     */
    public Response<Boolean> createSample(Sample sample){
        SampleReq sampleReq = SampleReq
                .build(this.namespace, this.collectionName)
                .setSampleId(sample.getSampleId())
                .setSampleData(sample.getSampleData());
        return HttpClient.post(Api.getUrl(this.serverHost, Api.sample_create), sampleReq);
    }

    /**
     * 更新一个样本，
     * @param sample 样本的定义信息
     * @return  是否创建成功
     */
    public Response<Boolean> updateSample(Sample sample){
        SampleReq sampleReq = SampleReq
                .build(this.namespace, this.collectionName)
                .setSampleId(sample.getSampleId())
                .setSampleData(sample.getSampleData());
        return HttpClient.post(Api.getUrl(this.serverHost, Api.sample_update), sampleReq);
    }

    /**
     *根据条件删除样本
     * @return  是否删除成功
     */
    public Response<Boolean> deleteSample(String sampleId){
        MapParam param = MapParam.build()
                .put("namespace", namespace)
                .put("collectionName", collectionName)
                .put("sampleId", sampleId);
        return HttpClient.get(Api.getUrl(this.serverHost, Api.sample_delete), param);
    }

    /**
     *根据条件查看样本
     * @return  样本
     */
    public Response<SampleRep> getSample(String sampleId){
        MapParam param = MapParam.build()
                .put("namespace", namespace)
                .put("collectionName", collectionName)
                .put("sampleId", sampleId);
        return HttpClient.get(Api.getUrl(this.serverHost, Api.sample_get), param, new TypeReference<Response<SampleRep>>(){});
    }

    /**
     * 根据查询信息查看样本列表
     * @param offset            起始记录
     * @param limit             样本数目
     * @param order             排列方式。包括asc（升序）和desc（降序）
     * @return
     */
    public Response<List<SampleRep>> sampleList(Integer offset, Integer limit, Order order){
        MapParam param = MapParam.build()
                .put("namespace", namespace)
                .put("collectionName", collectionName)
                .put("offset", offset)
                .put("limit", limit)
                .put("order", order.name());
        return HttpClient.get(Api.getUrl(this.serverHost, Api.sample_list), param, new TypeReference<Response<List<SampleRep>>>(){});
    }
}
