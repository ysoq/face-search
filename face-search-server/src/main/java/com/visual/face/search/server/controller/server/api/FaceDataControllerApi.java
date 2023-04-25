package com.visual.face.search.server.controller.server.api;

import com.visual.face.search.server.domain.common.ResponseInfo;
import com.visual.face.search.server.domain.request.FaceDataReqVo;
import com.visual.face.search.server.domain.response.FaceDataRepVo;

public interface FaceDataControllerApi {

    /**
     * 创建一个人脸数据
     * @param face 人脸的定义信息
     * @return  是否创建成功
     */
    public ResponseInfo<FaceDataRepVo> create(FaceDataReqVo face);


    /**
     *根据条件删除人脸数据
     * @param namespace         命名空间
     * @param collectionName    集合名称
     * @param sampleId          样本ID
     * @param faceId            人脸ID
     * @return  是否删除成功
     */
    public ResponseInfo<Boolean> delete(String namespace, String collectionName, String sampleId, String faceId);

}
