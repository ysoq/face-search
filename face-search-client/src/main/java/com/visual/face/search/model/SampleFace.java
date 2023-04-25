package com.visual.face.search.model;

import java.io.Serializable;

public class SampleFace implements Comparable<SampleFace>, Serializable {

    /**样本ID**/
    private String sampleId;
    /**人脸ID**/
    private String faceId;
    /**人脸人数质量**/
    private Float faceScore;
    /**转换后的置信度**/
    private Float confidence;
    /**样本扩展的额外数据**/
    private KeyValues sampleData;
    /**人脸扩展的额外数据**/
    private KeyValues faceData;

    /**
     * 构造数据
     * @return
     */
    public static SampleFace build(){
        return new SampleFace();
    }

    public String getSampleId() {
        return sampleId;
    }

    public void setSampleId(String sampleId) {
        this.sampleId = sampleId;
    }

    public KeyValues getSampleData() {
        return sampleData;
    }

    public void setSampleData(KeyValues sampleData) {
        this.sampleData = sampleData;
    }

    public String getFaceId() {
        return faceId;
    }

    public void setFaceId(String faceId) {
        this.faceId = faceId;
    }

    public KeyValues getFaceData() {
        return faceData;
    }

    public void setFaceData(KeyValues faceData) {
        this.faceData = faceData;
    }

    public Float getFaceScore() {
        return faceScore;
    }

    public void setFaceScore(Float faceScore) {
        this.faceScore = faceScore;
    }

    public Float getConfidence() {
        return confidence;
    }

    public void setConfidence(Float confidence) {
        this.confidence = confidence;
    }

    @Override
    public int compareTo(SampleFace that) {
        return Float.compare(that.confidence, this.confidence);
    }
}
