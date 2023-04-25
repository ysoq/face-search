package com.visual.face.search.server.controller.server.api;

import java.util.List;
import com.visual.face.search.server.domain.common.ResponseInfo;
import com.visual.face.search.server.domain.request.SampleDataReqVo;
import com.visual.face.search.server.domain.response.SampleDataRepVo;


public interface SampleDataControllerApi {

    /**
     * 创建一个样本，
     * @param sample 样本的定义信息
     * @return  是否创建成功
     */
    public ResponseInfo<Boolean> create(SampleDataReqVo sample);

    /**
     * 修改一个样本，
     * @param sample 样本的定义信息
     * @return  是否创建成功
     */
    public ResponseInfo<Boolean> update(SampleDataReqVo sample);

    /**
     *根据条件删除样本
     * @param namespace 命名空间
     * @param collectionName 集合名称
     * @param sampleId  样本ID
     * @return  是否删除成功
     */
    public ResponseInfo<Boolean> delete(String namespace, String collectionName, String sampleId);

    /**
     *根据命名空间，集合名称查看集合信息
     * @param namespace 命名空间
     * @param collectionName 集合名称
     * @param sampleId  样本ID
     * @return  集合信息
     */
    public ResponseInfo<SampleDataRepVo> get(String namespace, String collectionName, String sampleId);

    /**
     * 根据查询信息查看样本列表
     * @param namespace         命名空间
     * @param collectionName    集合名称
     * @param offset            起始记录
     * @param limit             样本数目
     * @param order             排列方式。包括asc（升序）和desc（降序）
     * @return
     */
    public ResponseInfo<List<SampleDataRepVo>> list(String namespace, String collectionName, Integer offset, Integer limit, String order);
}
