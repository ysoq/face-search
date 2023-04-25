package com.visual.face.search.handle;

import com.visual.face.search.common.Api;
import com.visual.face.search.http.HttpClient;
import com.visual.face.search.model.*;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class FaceHandler extends BaseHandler<FaceHandler>{

    /**实例对象**/
    private final static Map<String, FaceHandler> ins = new ConcurrentHashMap<>();

    /**
     * 构建样本对象
     * @param serverHost        服务地址
     * @param namespace         命名空间
     * @param collectionName    集合名称
     * @return
     */
    public static FaceHandler build(String serverHost, String namespace, String collectionName){
        String key = serverHost+"|_|"+namespace + "|_|" + collectionName;
        if(!ins.containsKey(key)){
            synchronized (FaceHandler.class){
                if(!ins.containsKey(key)){
                    ins.put(key, new FaceHandler().setServerHost(serverHost)
                            .setNamespace(namespace).setCollectionName(collectionName));
                }
            }
        }
        return ins.get(key);
    }

    /**
     * 创建一个人脸数据
     * @param face 人脸的定义信息
     * @return  是否创建成功
     */
    public Response<FaceRep> createFace(Face face){
        FaceReq faceReq = FaceReq
                .build(this.namespace, this.collectionName)
                .setSampleId(face.getSampleId())
                .setImageBase64(face.getImageBase64())
                .setFaceData(face.getFaceData())
                .setFaceScoreThreshold(face.getFaceScoreThreshold())
                .setMinConfidenceThresholdWithThisSample(face.getMinConfidenceThresholdWithThisSample())
                .setMaxConfidenceThresholdWithOtherSample(face.getMaxConfidenceThresholdWithOtherSample());
        return HttpClient.post(Api.getUrl(this.serverHost, Api.face_create), faceReq);
    }


    /**
     *根据条件删除人脸数据
     * @param sampleId          样本ID
     * @param faceId            人脸ID
     * @return  是否删除成功
     */
    public Response<Boolean> deleteFace(String sampleId, String faceId){
        MapParam param = MapParam.build()
                .put("namespace", namespace)
                .put("collectionName", collectionName)
                .put("sampleId", sampleId)
                .put("faceId", faceId);
        return HttpClient.get(Api.getUrl(this.serverHost, Api.face_delete), param);
    }

}
