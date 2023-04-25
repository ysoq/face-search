package com.visual.face.search.server.domain.request;

import com.visual.face.search.server.domain.base.FaceDataVo;
import io.swagger.annotations.ApiModelProperty;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.NotNull;

public class FaceDataReqVo extends FaceDataVo<FaceDataReqVo> {

    /**图像Base64编码值**/
    @NotNull(message = "imageBase64 cannot be empty")
    @ApiModelProperty(value="图像Base64编码值", position = 11,required = true)
    private String imageBase64;
    /**人脸质量分数阈值**/
    @Range(min=0, max = 100, message = "faceScoreThreshold is not in the range")
    @ApiModelProperty(value="人脸质量分数阈值,范围：[0,100]：默认0。当设置为0时，会默认使用当前模型的默认值，该方法为推荐使用方式", position = 12,required = false)
    private Float faceScoreThreshold = 0f;
    /**当前样本里，人脸自信度的最小阈值**/
    @Range(min = 0, max = 100, message = "minConfidenceThresholdWhitThisSample is not in the range")
    @ApiModelProperty(value="当前样本的人脸相似度的最小阈值,范围：[0,100]：默认0。当设置为0时，表示不做类间相似度判断逻辑,开启后对效率有较大影响", position = 13,required = false)
    private Float minConfidenceThresholdWithThisSample = 0f;
    /**当前样本与其他样本里，人脸自信度的最大阈值**/
    @Range(min = 0, max = 100, message = "maxConfidenceThresholdWhitOtherSample is not in the range")
    @ApiModelProperty(value="当前样本与其他样本的人脸相似度的最大阈值,范围：[0,100]：默认0。当设置为0时，表示不做类间相似度判断逻辑,开启后对效率有较大影响", position = 14,required = false)
    private Float maxConfidenceThresholdWithOtherSample = 0f;

    /**
     * 构建集合对象
     * @param namespace         命名空间
     * @param collectionName    集合名称
     * @param sampleId          样本ID
     * @return
     */
    public static FaceDataReqVo build(String namespace, String collectionName, String sampleId){
        return new FaceDataReqVo().setNamespace(namespace).setCollectionName(collectionName).setSampleId(sampleId);
    }

    public String getImageBase64() {
        return imageBase64;
    }

    public FaceDataReqVo setImageBase64(String imageBase64) {
        this.imageBase64 = imageBase64;
        return this;
    }

    public Float getFaceScoreThreshold() {
        return faceScoreThreshold;
    }

    public FaceDataReqVo setFaceScoreThreshold(Float faceScoreThreshold) {
        if(null != faceScoreThreshold){
            this.faceScoreThreshold = faceScoreThreshold;
        }
        return this;
    }

    public Float getMinConfidenceThresholdWithThisSample() {
        return minConfidenceThresholdWithThisSample;
    }

    public FaceDataReqVo setMinConfidenceThresholdWithThisSample(Float minConfidenceThresholdWithThisSample) {
        if(null != minConfidenceThresholdWithThisSample){
            this.minConfidenceThresholdWithThisSample = minConfidenceThresholdWithThisSample;
        }
        return this;
    }

    public Float getMaxConfidenceThresholdWithOtherSample() {
        return maxConfidenceThresholdWithOtherSample;
    }

    public FaceDataReqVo setMaxConfidenceThresholdWithOtherSample(Float maxConfidenceThresholdWithOtherSample) {
        if(null != maxConfidenceThresholdWithOtherSample){
            this.maxConfidenceThresholdWithOtherSample = maxConfidenceThresholdWithOtherSample;
        }
        return this;
    }
}
