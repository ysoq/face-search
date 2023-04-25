package com.visual.face.search.model;

import java.io.Serializable;

public class FaceRep implements Serializable {

    /**命名空间**/
    private String namespace;
    /**集合名称**/
    private String collectionName;
    /**样本ID**/
    private String sampleId;
    /**人脸ID**/
    private String faceId;
    /**人脸分数**/
    private Float faceScore;
    /**人脸扩展的额外数据**/
    private KeyValues faceData = KeyValues.build();


    /**
     * 构建集合对象
     * @param namespace         命名空间
     * @param collectionName    集合名称
     * @return
     */
    public static FaceRep build(String namespace, String collectionName){
        return new FaceRep().setNamespace(namespace).setCollectionName(collectionName);
    }

    public String getNamespace() {
        return namespace;
    }

    public FaceRep setNamespace(String namespace) {
        this.namespace = namespace;
        return this;
    }

    public String getCollectionName() {
        return collectionName;
    }

    public FaceRep setCollectionName(String collectionName) {
        this.collectionName = collectionName;
        return this;
    }

    public String getSampleId() {
        return sampleId;
    }

    public FaceRep setSampleId(String sampleId) {
        this.sampleId = sampleId;
        return this;
    }

    public String getFaceId() {
        return faceId;
    }

    public FaceRep setFaceId(String faceId) {
        this.faceId = faceId;
        return this;
    }

    public Float getFaceScore() {
        return faceScore;
    }

    public FaceRep setFaceScore(Float faceScore) {
        this.faceScore = faceScore;
        return this;
    }

    public KeyValues getFaceData() {
        return faceData;
    }

    public FaceRep setFaceData(KeyValues faceData) {
        this.faceData = faceData;
        return this;
    }
}
