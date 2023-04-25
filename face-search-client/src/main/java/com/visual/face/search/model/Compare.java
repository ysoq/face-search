package com.visual.face.search.model;

import java.io.Serializable;

public class Compare <ExtendsVo extends Compare<ExtendsVo>> implements Serializable {

    /**图像Base64编码值**/
    private String imageBase64A;

    /**图像Base64编码值**/
    private String imageBase64B;

    /**人脸质量分数阈值:人脸质量分数阈值,范围：[0,100]：默认0。当设置为0时，会默认使用当前模型的默认值，该方法为推荐使用方式**/
    private Float faceScoreThreshold = 0f;

    /**是否需要人脸信息**/
    private Boolean needFaceInfo = true;

    /**
     * 构建比对对象
     * @return
     */
    public static Compare build(){
        return new Compare();
    }


    public String getImageBase64A() {
        return imageBase64A;
    }

    public ExtendsVo setImageBase64A(String imageBase64A) {
        this.imageBase64A = imageBase64A;
        return (ExtendsVo) this;
    }

    public String getImageBase64B() {
        return imageBase64B;
    }

    public ExtendsVo setImageBase64B(String imageBase64B) {
        this.imageBase64B = imageBase64B;
        return (ExtendsVo) this;
    }

    public Float getFaceScoreThreshold() {
        return faceScoreThreshold;
    }

    public ExtendsVo setFaceScoreThreshold(Float faceScoreThreshold) {
        if(null != faceScoreThreshold && faceScoreThreshold >= 0 && faceScoreThreshold <= 100){
            this.faceScoreThreshold = faceScoreThreshold;
        }
        return (ExtendsVo) this;
    }

    public Boolean getNeedFaceInfo() {
        return needFaceInfo;
    }

    public ExtendsVo setNeedFaceInfo(Boolean needFaceInfo) {
        if(null != needFaceInfo){
            this.needFaceInfo = needFaceInfo;
        }
        return (ExtendsVo) this;
    }
}
