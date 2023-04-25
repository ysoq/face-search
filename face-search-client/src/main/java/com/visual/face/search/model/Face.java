package com.visual.face.search.model;

import java.io.Serializable;

public class Face<ExtendsVo extends Face<ExtendsVo>> implements Serializable {

    /**样本ID**/
    private String sampleId;
    /**图像Base64编码值**/
    private String imageBase64;
    /**人脸质量分数阈值**/
    private Float faceScoreThreshold = 0f;
    /**当前样本里，人脸自信度的最小阈值**/
    private Float minConfidenceThresholdWithThisSample = 0f;
    /**当前样本与其他样本里，人脸自信度的最大阈值**/
    private Float maxConfidenceThresholdWithOtherSample = 0f;
    /**人脸扩展的额外数据**/
    private KeyValues faceData = KeyValues.build();

    /**
     * 构建样本数据
     * @param sampleId          样本ID
     * @return
     */
    public static Face build(String sampleId){
        return new Face().setSampleId(sampleId);
    }


    public String getSampleId() {
        return sampleId;
    }

    public ExtendsVo setSampleId(String sampleId) {
        this.sampleId = sampleId;
        return (ExtendsVo) this;
    }

    public KeyValues getFaceData() {
        return faceData;
    }

    public ExtendsVo setFaceData(KeyValues faceData) {
        if(null != faceData){
            this.faceData = faceData;
        }
        return (ExtendsVo) this;
    }

    public String getImageBase64() {
        return imageBase64;
    }

    public ExtendsVo setImageBase64(String imageBase64) {
        this.imageBase64 = imageBase64;
        return (ExtendsVo) this;
    }

    public Float getFaceScoreThreshold() {
        return faceScoreThreshold;
    }

    public ExtendsVo setFaceScoreThreshold(Float faceScoreThreshold) {
        if(null != faceScoreThreshold && faceScoreThreshold >= 0){
            this.faceScoreThreshold = faceScoreThreshold;
        }
        return (ExtendsVo) this;
    }

    public Float getMinConfidenceThresholdWithThisSample() {
        return minConfidenceThresholdWithThisSample;
    }

    public ExtendsVo setMinConfidenceThresholdWithThisSample(Float minConfidenceThresholdWithThisSample) {
        if(null != minConfidenceThresholdWithThisSample && minConfidenceThresholdWithThisSample >= 0){
            this.minConfidenceThresholdWithThisSample = minConfidenceThresholdWithThisSample;
        }
        return (ExtendsVo) this;
    }

    public Float getMaxConfidenceThresholdWithOtherSample() {
        return maxConfidenceThresholdWithOtherSample;
    }

    public ExtendsVo setMaxConfidenceThresholdWithOtherSample(Float maxConfidenceThresholdWithOtherSample) {
        if(null != maxConfidenceThresholdWithOtherSample && maxConfidenceThresholdWithOtherSample >= 0){
            this.maxConfidenceThresholdWithOtherSample = maxConfidenceThresholdWithOtherSample;
        }
        return (ExtendsVo) this;
    }
}
