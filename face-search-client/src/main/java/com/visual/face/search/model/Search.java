package com.visual.face.search.model;

import java.io.Serializable;

public class Search<ExtendsVo extends Search<ExtendsVo>> implements Serializable {

    /**图像Base64编码值**/
    private String imageBase64;
    /**人脸质量分数阈值：默认0,范围：[0,100]。当设置为0时，会默认使用当前模型的默认值，该方法为推荐使用方式**/
    private Float faceScoreThreshold=0f;
    /**人脸匹配分数阈值：默认0,范围：[-100,100]**/
    private Float confidenceThreshold=0f;
    /**搜索条数：默认5**/
    private Integer limit=5;
    /**对输入图像中多少个人脸进行检索比对：默认5**/
    private Integer maxFaceNum=5;

    /**
     * 构建检索对象
     * @param imageBase64       待检索的图片
     * @return
     */
    public static Search build(String imageBase64){
        return new Search().setImageBase64(imageBase64);
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
        this.faceScoreThreshold = faceScoreThreshold;
        return (ExtendsVo) this;
    }

    public Float getConfidenceThreshold() {
        return confidenceThreshold;
    }

    public ExtendsVo setConfidenceThreshold(Float confidenceThreshold) {
        this.confidenceThreshold = confidenceThreshold;
        return (ExtendsVo) this;
    }

    public Integer getLimit() {
        return limit;
    }

    public ExtendsVo setLimit(Integer limit) {
        this.limit = limit;
        return (ExtendsVo) this;
    }

    public Integer getMaxFaceNum() {
        return maxFaceNum;
    }

    public ExtendsVo setMaxFaceNum(Integer maxFaceNum) {
        this.maxFaceNum = maxFaceNum;
        return (ExtendsVo) this;
    }
}
