package com.visual.face.search.server.domain.response;

import com.visual.face.search.server.domain.base.BaseVo;
import com.visual.face.search.server.domain.extend.CompareFace;
import io.swagger.annotations.ApiModelProperty;

public class FaceCompareRepVo extends BaseVo {
    /**向量的距离**/
    @ApiModelProperty(value="向量欧式距离:>=0", position = 1, required = true)
    private Float distance;
    /**转换后的置信度**/
    @ApiModelProperty(value="余弦距离转换后的置信度:[-100,100]，值越大，相似度越高。", position = 2, required = true)
    private Float confidence;
    /**人脸信息**/
    @ApiModelProperty(value="人脸信息,参数needFaceInfo=false时，值为null", position = 3, required = false)
    private CompareFace faceInfo;

    public Float getDistance() {
        return distance;
    }

    public void setDistance(Float distance) {
        this.distance = distance;
    }

    public Float getConfidence() {
        return confidence;
    }

    public void setConfidence(Float confidence) {
        this.confidence = confidence;
    }

    public CompareFace getFaceInfo() {
        return faceInfo;
    }

    public void setFaceInfo(CompareFace faceInfo) {
        this.faceInfo = faceInfo;
    }
}
