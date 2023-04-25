package com.visual.face.search.model;

import java.io.Serializable;

public class CompareRep implements Serializable {
    /**向量欧式距离:>=0**/
    private Float distance;
    /**余弦距离转换后的置信度:[-100,100]，值越大，相似度越高。**/
    private Float confidence;
    /**人脸信息,参数needFaceInfo=false时，值为null**/
    private CompareFace faceInfo;

    public Float getDistance() {
        return distance;
    }

    public CompareRep setDistance(Float distance) {
        this.distance = distance;
        return this;
    }

    public Float getConfidence() {
        return confidence;
    }

    public CompareRep setConfidence(Float confidence) {
        this.confidence = confidence;
        return this;
    }

    public CompareFace getFaceInfo() {
        return faceInfo;
    }

    public CompareRep setFaceInfo(CompareFace faceInfo) {
        this.faceInfo = faceInfo;
        return this;
    }

}
