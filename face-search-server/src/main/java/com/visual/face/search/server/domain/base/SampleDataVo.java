package com.visual.face.search.server.domain.base;

import com.visual.face.search.server.domain.extend.FieldKeyValues;
import io.swagger.annotations.ApiModelProperty;
import org.hibernate.validator.constraints.Length;

public class SampleDataVo<ExtendsVo extends SampleDataVo<ExtendsVo>> extends BaseVo {

    /**命名空间**/
    @Length(min = 1, max = 12, message = "namespace length is not in the range")
    @ApiModelProperty(value="命名空间：最大12个字符,支持小写字母、数字和下划线的组合", position = 0,required = true)
    private String namespace;
    /**集合名称**/
    @Length(min = 1, max = 24, message = "collectionName length is not in the range")
    @ApiModelProperty(value="集合名称：最大24个字符,支持小写字母、数字和下划线的组合",name="collectionName", position = 1,required = true)
    private String collectionName;
    /**样本ID**/
    @Length(min = 1, max = 32, message = "sampleId length is not in the range")
    @ApiModelProperty(value="样本ID：最大32个字符,支持小写字母、数字和下划线的组合",name="sampleId", position = 2,required = true)
    private String sampleId;
    /**样本扩展的额外数据**/
    @ApiModelProperty(value="扩展字段",name="sampleData", position = 3,required = false)
    private FieldKeyValues sampleData;

    public SampleDataVo(){}

    /**
     * 构建样本数据
     * @param namespace         命名空间
     * @param collectionName    集合名称
     * @param sampleId          样本ID
     * @return
     */
    public static SampleDataVo build(String namespace, String collectionName, String sampleId){
        return new SampleDataVo().setNamespace(namespace).setCollectionName(collectionName).setSampleId(sampleId);
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

    public String getSampleId() {
        return sampleId;
    }

    public ExtendsVo setSampleId(String sampleId) {
        this.sampleId = sampleId;
        return (ExtendsVo) this;
    }

    public FieldKeyValues getSampleData() {
        return sampleData;
    }

    public ExtendsVo setSampleData(FieldKeyValues sampleData) {
        this.sampleData = sampleData;
        return (ExtendsVo) this;
    }
}
