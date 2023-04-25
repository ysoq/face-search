package com.visual.face.search.server.domain.response;

import com.visual.face.search.server.domain.base.SampleDataVo;
import com.visual.face.search.server.domain.extend.SimpleFaceVo;
import io.swagger.annotations.ApiModelProperty;

import java.util.ArrayList;
import java.util.List;

public class SampleDataRepVo extends SampleDataVo<SampleDataRepVo> {

    /**人脸数据**/
    @ApiModelProperty(value="人脸数据",name="faces", position = 11,required = false)
    private List<SimpleFaceVo> faces = new ArrayList<>();

    /**
     * 构建样本数据
     * @param namespace         命名空间
     * @param collectionName    集合名称
     * @param sampleId          样本ID
     * @return
     */
    public static SampleDataRepVo build(String namespace, String collectionName, String sampleId){
        return new SampleDataRepVo().setNamespace(namespace).setCollectionName(collectionName).setSampleId(sampleId);
    }

    public List<SimpleFaceVo> getFaces() {
        return faces;
    }

    public SampleDataRepVo setFaces(List<SimpleFaceVo> faces) {
        this.faces = faces;
        return this;
    }
}
