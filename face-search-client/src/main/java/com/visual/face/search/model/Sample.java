package com.visual.face.search.model;

import java.io.Serializable;

public class Sample<ExtendsVo extends Sample<ExtendsVo>> implements Serializable {

    /**样本ID**/
    private String sampleId;
    /**样本扩展的额外数据**/
    private KeyValues sampleData;

    public Sample(){}

    /**
     * 构建样本数据
     * @param sampleId          样本ID
     * @return
     */
    public static Sample build(String sampleId){
        return new Sample().setSampleId(sampleId);
    }

    public String getSampleId() {
        return sampleId;
    }

    public ExtendsVo setSampleId(String sampleId) {
        this.sampleId = sampleId;
        return (ExtendsVo) this;
    }

    public KeyValues getSampleData() {
        return sampleData;
    }

    public ExtendsVo setSampleData(KeyValues sampleData) {
        this.sampleData = sampleData;
        return (ExtendsVo) this;
    }
}
