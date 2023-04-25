package com.visual.face.search.model;

public class CompareFace {

    /**A图片人脸分数:[0,100]**/
    private Float faceScoreA;
    /**B图片人脸分数:[0,100]**/
    private Float faceScoreB;

    /**A图片人脸位置信息**/
    private FaceLocation locationA;
    /**B图片人脸位置信息**/
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
