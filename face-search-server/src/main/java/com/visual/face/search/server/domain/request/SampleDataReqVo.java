package com.visual.face.search.server.domain.request;

import com.visual.face.search.server.domain.base.SampleDataVo;
import io.swagger.annotations.ApiModel;

@ApiModel(value = "SampleDataReqVo",  description="样本信息")
public class SampleDataReqVo extends SampleDataVo<SampleDataReqVo> {

    /**
     * 构建样本数据
     * @param namespace         命名空间
     * @param collectionName    集合名称
     * @param sampleId          样本ID
     * @return
     */
    public static SampleDataReqVo build(String namespace, String collectionName, String sampleId){
        return new SampleDataReqVo().setNamespace(namespace).setCollectionName(collectionName).setSampleId(sampleId);
    }

}
