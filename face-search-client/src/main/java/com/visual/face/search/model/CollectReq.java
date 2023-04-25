package com.visual.face.search.model;

public class CollectReq extends Collect<CollectReq> {

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
    public static CollectReq build(String namespace, String collectionName){
        return new CollectReq().setNamespace(namespace).setCollectionName(collectionName);
    }

    public String getNamespace() {
        return namespace;
    }

    public CollectReq setNamespace(String namespace) {
        this.namespace = namespace;
        return this;
    }

    public String getCollectionName() {
        return collectionName;
    }

    public CollectReq setCollectionName(String collectionName) {
        this.collectionName = collectionName;
        return this;
    }

}
