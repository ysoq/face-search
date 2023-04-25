package com.visual.face.search.server.domain.response;

import java.util.ArrayList;
import java.util.List;
import com.visual.face.search.server.domain.base.BaseVo;
import com.visual.face.search.server.domain.extend.FaceLocation;
import com.visual.face.search.server.domain.extend.SampleFaceVo;
import io.swagger.annotations.ApiModelProperty;

public class FaceSearchRepVo extends BaseVo {
    /**人脸位置信息**/
    @ApiModelProperty(value="人脸位置信息", position = 1, required = true)
    private FaceLocation location;
    /**人脸质量分数**/
    @ApiModelProperty(value="人脸分数:[0,100]", position = 2, required = true)
    private Float faceScore;
    /**匹配的人脸列表**/
    @ApiModelProperty(value="匹配的人脸列表", position = 3, required = false)
    private List<SampleFaceVo> match = new ArrayList<>();

    /**
     * 构建对象
     * @return
     */
    public static FaceSearchRepVo build(){
        return new FaceSearchRepVo();
    }

    public FaceLocation getLocation() {
        return location;
    }

    public void setLocation(FaceLocation location) {
        this.location = location;
    }

    public Float getFaceScore() {
        return faceScore;
    }

    public void setFaceScore(Float faceScore) {
        this.faceScore = faceScore;
    }

    public List<SampleFaceVo> getMatch() {
        return match;
    }

    public void setMatch(List<SampleFaceVo> match) {
        this.match = match;
    }
}
