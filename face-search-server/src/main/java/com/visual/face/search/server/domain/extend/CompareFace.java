package com.visual.face.search.server.domain.extend;

import io.swagger.annotations.ApiModelProperty;

public class CompareFace {

    /**人脸质量分数**/
    @ApiModelProperty(value="A图片人脸分数:[0,100]", position = 1, required = true)
    private Float faceScoreA;
    /**人脸质量分数**/
    @ApiModelProperty(value="B图片人脸分数:[0,100]", position = 1, required = true)
    private Float faceScoreB;

    /**人脸位置信息**/
    @ApiModelProperty(value="A图片人脸位置信息", position = 3, required = true)
    private FaceLocation locationA;
    /**人脸位置信息**/
    @ApiModelProperty(value="B图片人脸位置信息", position = 4, required = true)
    private FaceLocation locationB;


    public Float getFaceScoreA() {
        return faceScoreA;
    }

    public void setFaceScoreA(Float faceScoreA) {
        this.faceScoreA = faceScoreA;
    }

    public FaceLocation getLocationA() {
        return locationA;
    }

    public void setLocationA(FaceLocation locationA) {
        this.locationA = locationA;
    }

    public Float getFaceScoreB() {
        return faceScoreB;
    }

    public void setFaceScoreB(Float faceScoreB) {
        this.faceScoreB = faceScoreB;
    }

    public FaceLocation getLocationB() {
        return locationB;
    }

    public void setLocationB(FaceLocation locationB) {
        this.locationB = locationB;
    }
}
