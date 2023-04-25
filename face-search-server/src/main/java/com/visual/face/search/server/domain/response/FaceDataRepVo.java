package com.visual.face.search.server.domain.response;

import com.visual.face.search.server.domain.base.FaceDataVo;
import io.swagger.annotations.ApiModelProperty;

public class FaceDataRepVo extends FaceDataVo<FaceDataRepVo> {
    /**人脸ID**/
    @ApiModelProperty(value="人脸ID",name="faceId", position = 11,required = true)
    private String faceId;
    /**人脸人数质量**/
    @ApiModelProperty(value="人脸人数质量",name="faceScore", position = 12,required = true)
    private Float faceScore;

    /**
     * 构建集合对象
     * @param namespace         命名空间
     * @param collectionName    集合名称
     * @param sampleId          样本ID
     * @return
     */
    public static FaceDataRepVo build(String namespace, String collectionName, String sampleId, String faceId){
        return new FaceDataRepVo()
                .setNamespace(namespace).setCollectionName(collectionName)
                .setSampleId(sampleId).setFaceId(faceId);
    }

    public String getFaceId() {
        return faceId;
    }

    public FaceDataRepVo setFaceId(String faceId) {
        this.faceId = faceId;
        return this;
    }

    public Float getFaceScore() {
        return faceScore;
    }

    public FaceDataRepVo setFaceScore(Float faceScore) {
        this.faceScore = faceScore;
        return this;
    }

}
