package com.visual.face.search.server.domain.extend;

import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

public class SimpleFaceVo implements Serializable {

    /**人脸ID**/
    @ApiModelProperty(value="人脸ID",name="faceId", position = 1,required = true)
    private String faceId;
    /**人脸扩展的额外数据**/
    @ApiModelProperty(value="人脸扩展的额外数据",name="faceData", position = 2,required = false)
    private FieldKeyValues faceData;
    /**人脸人数质量**/
    @ApiModelProperty(value="人脸分数",name="faceScore", position = 3,required = true)
    private Float faceScore;

    public static SimpleFaceVo build(String faceId){
        return new SimpleFaceVo().setFaceId(faceId);
    }

    public String getFaceId() {
        return faceId;
    }

    public SimpleFaceVo setFaceId(String faceId) {
        this.faceId = faceId;
        return this;
    }

    public FieldKeyValues getFaceData() {
        return faceData;
    }

    public SimpleFaceVo setFaceData(FieldKeyValues faceData) {
        this.faceData = faceData;
        return this;
    }

    public Float getFaceScore() {
        return faceScore;
    }

    public SimpleFaceVo setFaceScore(Float faceScore) {
        this.faceScore = faceScore;
        return this;
    }
}
