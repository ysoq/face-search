package com.visual.face.search.model;

public class SearchReq extends Search<SearchReq> {
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
    public static SearchReq build(String namespace, String collectionName){
        return new SearchReq().setNamespace(namespace).setCollectionName(collectionName);
    }

    public String getNamespace() {
        return namespace;
    }

    public SearchReq setNamespace(String namespace) {
        this.namespace = namespace;
        return this;
    }

    public String getCollectionName() {
        return collectionName;
    }

    public SearchReq setCollectionName(String collectionName) {
        this.collectionName = collectionName;
        return this;
    }

}
