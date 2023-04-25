package com.visual.face.search.model;

public class SampleReq extends Sample<SampleReq>{

    /**命名空间**/
    private String namespace;
    /**集合名称**/
    private String collectionName;

    /**
     * 构建集合对象
     * @param namespace         命名空间
     * @param collectionName    集合名称
     * @return
     */
    public static SampleReq build(String namespace, String collectionName){
        return new SampleReq().setNamespace(namespace).setCollectionName(collectionName);
    }

    public String getNamespace() {
        return namespace;
    }

    public SampleReq setNamespace(String namespace) {
        this.namespace = namespace;
        return this;
    }

    public String getCollectionName() {
        return collectionName;
    }

    public SampleReq setCollectionName(String collectionName) {
        this.collectionName = collectionName;
        return this;
    }
}
