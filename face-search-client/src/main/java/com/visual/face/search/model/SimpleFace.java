package com.visual.face.search.model;

import java.io.Serializable;

public class SimpleFace implements Serializable {

    /**人脸ID**/
    private String faceId;
    /**人脸扩展的额外数据**/
    private KeyValues faceData;
    /**人脸人数质量**/
    private Float faceScore;

    public static SimpleFace build(String faceId){
        return new SimpleFace().setFaceId(faceId);
    }

    public String getFaceId() {
        return faceId;
    }

    public SimpleFace setFaceId(String faceId) {
        this.faceId = faceId;
        return this;
    }

    public KeyValues getFaceData() {
        return faceData;
    }

    public SimpleFace setFaceData(KeyValues faceData) {
        this.faceData = faceData;
        return this;
    }

    public Float getFaceScore() {
        return faceScore;
    }

    public SimpleFace setFaceScore(Float faceScore) {
        this.faceScore = faceScore;
        return this;
    }
}
