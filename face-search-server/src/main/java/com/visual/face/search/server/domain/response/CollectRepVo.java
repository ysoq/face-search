package com.visual.face.search.server.domain.response;

import com.visual.face.search.server.domain.base.CollectVo;

public class CollectRepVo extends CollectVo<CollectRepVo> {

    /**
     * 构建集合对象
     * @param namespace         命名空间
     * @param collectionName    集合名称
     * @return
     */
    public static CollectRepVo build(String namespace, String collectionName){
        return new CollectRepVo().setNamespace(namespace).setCollectionName(collectionName);
    }

}
