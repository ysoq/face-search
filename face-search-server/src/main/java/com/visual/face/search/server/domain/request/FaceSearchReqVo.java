package com.visual.face.search.server.domain.request;

import com.visual.face.search.server.domain.base.BaseVo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.Range;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@ApiModel(value = "FaceSearchReqVo",  description="人脸搜索参数")
public class FaceSearchReqVo extends BaseVo {
    /**命名空间**/
    @Length(min = 1, max = 12, message = "namespace length is not in the range")
    @ApiModelProperty(value="命名空间", position = 0, required = true)
    private String namespace;
    /**集合名称**/
    @Length(min = 1, max = 24, message = "collectionName length is not in the range")
    @ApiModelProperty(value="集合名称", position = 1, required = true)
    private String collectionName;
    /**图像Base64编码值**/
    @NotNull(message = "imageBase64 cannot be empty")
    @ApiModelProperty(value="图像Base64编码值", position = 2, required = true)
    private String imageBase64;
    /**人脸质量分数阈值：默认0,范围：[0,100]**/
    @Range(min = 0, max = 100, message = "faceScoreThreshold is not in the range")
    @ApiModelProperty(value="人脸质量分数阈值,范围：[0,100]：默认0。当设置为0时，会默认使用当前模型的默认值，该方法为推荐使用方式", position = 3, required = false)
    private Float faceScoreThreshold = 0f;
    /**人脸匹配分数阈值：默认0,范围：[-100,100]**/
    @Range(min = -100, max = 100, message = "faceScoreThreshold is not in the range")
    @ApiModelProperty(value="人脸匹配分数阈值，范围：[-100,100]：默认0", position = 4, required = false)
    private Float confidenceThreshold = 0f;
    /**选择搜索评分的算法，默认余弦相似度(COSINESIMIL)，可选参数：L1、L2、LINF、COSINESIMIL、INNERPRODUCT、HAMMINGBIT**/
    @ApiModelProperty(hidden = true, value="选择搜索评分的算法，默认是余弦相似度(COSINESIMIL)，可选参数：L1、L2、LINF、COSINESIMIL、INNERPRODUCT、HAMMINGBIT", position = 5, required = false)
    private String algorithm = SearchAlgorithm.COSINESIMIL.name();
    /**搜索条数：默认10**/
    @Min(value = 0, message = "limit must greater than or equal to 0")
    @ApiModelProperty(value="最大搜索条数：默认5", position = 6, required = false)
    private Integer limit;
    /**对输入图像中多少个人脸进行检索比对**/
    @Min(value = 0, message = "maxFaceNum must greater than or equal to 0")
    @ApiModelProperty(value="对输入图像中多少个人脸进行检索比对：默认5", position = 7, required = false)
    private Integer maxFaceNum;

    /**
     * 构建检索对象
     * @param namespace         命名空间
     * @param collectionName    集合名称
     * @param imageBase64       待检索的图片
     * @return
     */
    public static FaceSearchReqVo build(String namespace, String collectionName, String imageBase64){
        return new FaceSearchReqVo().setNamespace(namespace).setCollectionName(collectionName).setImageBase64(imageBase64);
    }

    public String getNamespace() {
        return namespace;
    }

    public FaceSearchReqVo setNamespace(String namespace) {
        this.namespace = namespace;
        return this;
    }

    public String getCollectionName() {
        return collectionName;
    }

    public FaceSearchReqVo setCollectionName(String collectionName) {
        this.collectionName = collectionName;
        return this;
    }

    public String getImageBase64() {
        return imageBase64;
    }

    public FaceSearchReqVo setImageBase64(String imageBase64) {
        this.imageBase64 = imageBase64;
        return this;
    }

    public Float getFaceScoreThreshold() {
        return faceScoreThreshold;
    }

    public FaceSearchReqVo setFaceScoreThreshold(Float faceScoreThreshold) {
        this.faceScoreThreshold = faceScoreThreshold;
        return this;
    }

    public Float getConfidenceThreshold() {
        return confidenceThreshold;
    }

    public FaceSearchReqVo setConfidenceThreshold(Float confidenceThreshold) {
        this.confidenceThreshold = confidenceThreshold;
        return this;
    }

    public SearchAlgorithm getAlgorithm() {
        if(null != algorithm && !algorithm.isEmpty()){
            return SearchAlgorithm.valueOf(this.algorithm);
        }else{
            return SearchAlgorithm.COSINESIMIL;
        }
    }

    public FaceSearchReqVo setAlgorithm(String algorithm) {
        if(null != algorithm){
            this.algorithm = algorithm;
        }
        return this;
    }

    public Integer getLimit() {
        return limit;
    }

    public FaceSearchReqVo setLimit(Integer limit) {
        this.limit = limit;
        return this;
    }

    public Integer getMaxFaceNum() {
        return maxFaceNum;
    }

    public FaceSearchReqVo setMaxFaceNum(Integer maxFaceNum) {
        this.maxFaceNum = maxFaceNum;
        return this;
    }
}
