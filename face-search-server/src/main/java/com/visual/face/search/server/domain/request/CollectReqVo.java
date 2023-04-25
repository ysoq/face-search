package com.visual.face.search.server.domain.request;

import com.visual.face.search.server.domain.base.CollectVo;
import io.swagger.annotations.ApiModel;

@ApiModel(value = "CollectReqVo",  description="集合信息")
public class CollectReqVo extends CollectVo<CollectReqVo> {

    /**
     * 构建集合对象
     * @param namespace         命名空间
     * @param collectionName    集合名称
     * @return
     */
    public static CollectReqVo build(String namespace, String collectionName){
        return new CollectReqVo().setNamespace(namespace).setCollectionName(collectionName);
    }

}
