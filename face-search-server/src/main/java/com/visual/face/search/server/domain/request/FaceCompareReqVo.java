package com.visual.face.search.server.domain.request;

import com.visual.face.search.server.domain.base.BaseVo;
import io.swagger.annotations.ApiModelProperty;
import org.hibernate.validator.constraints.Range;
import javax.validation.constraints.NotNull;

public class FaceCompareReqVo extends BaseVo {

    /**图像Base64编码值**/
    @NotNull(message = "imageBase64A cannot be empty")
    @ApiModelProperty(value="图像A的Base64编码值", position = 1,required = true)
    private String imageBase64A;

    /**图像Base64编码值**/
    @NotNull(message = "imageBase64B cannot be empty")
    @ApiModelProperty(value="图像B的Base64编码值", position = 2,required = true)
    private String imageBase64B;

    /**人脸质量分数阈值**/
    @Range(min=0, max = 100, message = "faceScoreThreshold is not in the range")
    @ApiModelProperty(value="人脸质量分数阈值,范围：[0,100]：默认0。当设置为0时，会默认使用当前模型的默认值，该方法为推荐使用方式", position = 3,required = false)
    private Float faceScoreThreshold = 0f;

    /**是否需要人脸信息**/
    @ApiModelProperty(value="是否需要人脸信息,默认为:true", position = 4,required = false)
    private Boolean needFaceInfo = true;


    public String getImageBase64A() {
        return imageBase64A;
    }

    public void setImageBase64A(String imageBase64A) {
        this.imageBase64A = imageBase64A;
    }

    public String getImageBase64B() {
        return imageBase64B;
    }

    public void setImageBase64B(String imageBase64B) {
        this.imageBase64B = imageBase64B;
    }

    public Float getFaceScoreThreshold() {
        return faceScoreThreshold;
    }

    public void setFaceScoreThreshold(Float faceScoreThreshold) {
        if(null != faceScoreThreshold && faceScoreThreshold >= 0 && faceScoreThreshold <= 100){
            this.faceScoreThreshold = faceScoreThreshold;
        }
    }

    public Boolean getNeedFaceInfo() {
        return needFaceInfo;
    }

    public void setNeedFaceInfo(Boolean needFaceInfo) {
        if(null != needFaceInfo){
            this.needFaceInfo = needFaceInfo;
        }
    }
}
