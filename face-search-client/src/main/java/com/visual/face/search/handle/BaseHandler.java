package com.visual.face.search.handle;


public class BaseHandler<ExtendsVo extends BaseHandler<ExtendsVo>> {
    /**服务地址**/
    protected String serverHost;
    /**命名空间**/
    protected String namespace;
    /**集合名称**/
    protected String collectionName;

    public String getServerHost() {
        return serverHost;
    }

    public ExtendsVo setServerHost(String serverHost) {
        this.serverHost = serverHost;
        return (ExtendsVo) this;
    }

    public String getNamespace() {
        return namespace;
    }

    public ExtendsVo setNamespace(String namespace) {
        this.namespace = namespace;
        return (ExtendsVo) this;
    }

    public String getCollectionName() {
        return collectionName;
    }

    public ExtendsVo setCollectionName(String collectionName) {
        this.collectionName = collectionName;
        return (ExtendsVo) this;
    }

}
