package com.visual.face.search.handle;

import com.alibaba.fastjson.TypeReference;
import com.visual.face.search.common.Api;
import com.visual.face.search.http.HttpClient;
import com.visual.face.search.model.*;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class CompareHandler {

    /**服务地址**/
    protected String serverHost;
    /**实例对象**/
    private final static Map<String, CompareHandler> ins = new ConcurrentHashMap<>();

    public String getServerHost() {
        return serverHost;
    }

    public CompareHandler setServerHost(String serverHost) {
        this.serverHost = serverHost;
        return this;
    }

    /**
     * 构建集合对象
     * @param serverHost        服务地址
     * @return
     */
    public static CompareHandler build(String serverHost){
        String key = serverHost;
        if(!ins.containsKey(key)){
            synchronized (CollectHandler.class){
                if(!ins.containsKey(key)){
                    ins.put(key, new CompareHandler().setServerHost(serverHost));
                }
            }
        }
        return ins.get(key);
    }

    /**
     * 人脸比对
     * @param compare 集合的定义信息
     * @return  是否创建成功
     */
    public Response<CompareRep> faceCompare(Compare compare){
        CompareReq compareReq = CompareReq.build()
                .setImageBase64A(compare.getImageBase64A())
                .setImageBase64B(compare.getImageBase64B())
                .setFaceScoreThreshold(compare.getFaceScoreThreshold())
                .setNeedFaceInfo(compare.getNeedFaceInfo());
        return HttpClient.post(Api.getUrl(this.serverHost, Api.visual_compare), compareReq, new TypeReference<Response<CompareRep>>() {});
    }

}
