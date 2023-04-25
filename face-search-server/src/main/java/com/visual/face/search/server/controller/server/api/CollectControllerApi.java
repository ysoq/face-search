package com.visual.face.search.server.controller.server.api;

import com.visual.face.search.server.domain.common.ResponseInfo;
import com.visual.face.search.server.domain.request.CollectReqVo;
import com.visual.face.search.server.domain.response.CollectRepVo;

import java.util.List;

/**
 * 人脸数据库服务
 */
public interface CollectControllerApi {

    /**
     * 创建一个集合，
     * @param collect 集合的定义信息
     * @return  是否创建成功
     */
    public ResponseInfo<Boolean> create(CollectReqVo collect);

    /**
     *根据命名空间，集合名称删除集合
     * @param namespace 命名空间
     * @param collectionName 集合名称
     * @return  是否删除成功
     */
    public ResponseInfo<Boolean> delete(String namespace, String collectionName);

    /**
     *根据命名空间，集合名称查看集合信息
     * @param namespace 命名空间
     * @param collectionName 集合名称
     * @return  集合信息
     */
    public ResponseInfo<CollectRepVo> get(String namespace, String collectionName);

    /**
     *根据命名空间查看集合列表
     * @param namespace 命名空间
     * @return  集合列表
     */
    public ResponseInfo<List<CollectRepVo>> list(String namespace);

}
