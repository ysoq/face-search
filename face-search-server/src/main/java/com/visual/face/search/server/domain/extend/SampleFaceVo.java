package com.visual.face.search.server.domain.extend;

import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

public class SampleFaceVo implements Comparable<SampleFaceVo>, Serializable {

    /**样本ID**/
    @ApiModelProperty(value="样本ID", position = 1, required = true)
    private String sampleId;
    /**人脸ID**/
    @ApiModelProperty(value="人脸ID", position = 2, required = true)
    private String faceId;
    /**人脸人数质量**/
    @ApiModelProperty(value="人脸分数:[0,100]", position = 3, required = true)
    private Float faceScore;
    /**转换后的置信度**/
    @ApiModelProperty(value="转换后的置信度:[-100,100]，值越大，相似度越高。", position = 4, required = true)
    private Float confidence;
    /**样本扩展的额外数据**/
    @ApiModelProperty(value="样本扩展的额外数据", position = 5, required = false)
    private FieldKeyValues sampleData;
    /**人脸扩展的额外数据**/
    @ApiModelProperty(value="人脸扩展的额外数据", position = 6, required = false)
    private FieldKeyValues faceData;

    /**
     * 构造数据
     * @return
     */
    public static SampleFaceVo build(){
        return new SampleFaceVo();
    }

    public String getSampleId() {
        return sampleId;
    }

    public void setSampleId(String sampleId) {
        this.sampleId = sampleId;
    }

    public FieldKeyValues getSampleData() {
        return sampleData;
    }

    public void setSampleData(FieldKeyValues sampleData) {
        this.sampleData = sampleData;
    }

    public String getFaceId() {
        return faceId;
    }

    public void setFaceId(String faceId) {
        this.faceId = faceId;
    }

    public FieldKeyValues getFaceData() {
        return faceData;
    }

    public void setFaceData(FieldKeyValues faceData) {
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
    public int compareTo(SampleFaceVo that) {
        return Float.compare(that.confidence, this.confidence);
    }
}
