package com.visual.face.search.model;


import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class SearchRep implements Serializable {
    /**人脸位置信息**/
    private FaceLocation location;
    /**人脸质量分数**/
    private Float faceScore;
    /**匹配的人脸列表**/
    private List<SampleFace> match = new ArrayList<>();

    /**
     * 构建对象
     * @return
     */
    public static SearchRep build(){
        return new SearchRep();
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

    public List<SampleFace> getMatch() {
        return match;
    }

    public void setMatch(List<SampleFace> match) {
        this.match = match;
    }
}
