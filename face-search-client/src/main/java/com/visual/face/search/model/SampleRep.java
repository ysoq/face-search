package com.visual.face.search.model;

import java.util.ArrayList;
import java.util.List;

public class SampleRep extends Sample<SampleRep>{

    /**命名空间**/
    private String namespace;
    /**集合名称**/
    private String collectionName;
    /**人脸数据**/
    private List<SimpleFace> faces = new ArrayList<>();

    /**
     * 构建集合对象
     * @param namespace         命名空间
     * @param collectionName    集合名称
     * @return
     */
    public static SampleRep build(String namespace, String collectionName){
        return new SampleRep().setNamespace(namespace).setCollectionName(collectionName);
    }

    public String getNamespace() {
        return namespace;
    }

    public SampleRep setNamespace(String namespace) {
        this.namespace = namespace;
        return this;
    }

    public String getCollectionName() {
        return collectionName;
    }

    public SampleRep setCollectionName(String collectionName) {
        this.collectionName = collectionName;
        return this;
    }

    public List<SimpleFace> getFaces() {
        return faces;
    }

    public SampleRep setFaces(List<SimpleFace> faces) {
        this.faces = faces;
        return this;
    }
}
