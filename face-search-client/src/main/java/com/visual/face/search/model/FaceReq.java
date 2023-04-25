package com.visual.face.search.model;

public class FaceReq extends Face<FaceReq>{

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
    public static FaceReq build(String namespace, String collectionName){
        return new FaceReq().setNamespace(namespace).setCollectionName(collectionName);
    }

    public String getNamespace() {
        return namespace;
    }

    public FaceReq setNamespace(String namespace) {
        this.namespace = namespace;
        return this;
    }

    public String getCollectionName() {
        return collectionName;
    }

    public FaceReq setCollectionName(String collectionName) {
        this.collectionName = collectionName;
        return this;
    }

}
