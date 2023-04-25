package com.visual.face.search.server.model;


import java.util.Date;
import java.util.List;

public class SampleData {
    /**
     *	自增主键自增
     */
    private Long id;

    /**样本ID**/
    private String sampleId;

    /**数据信息**/
    private List<ColumnValue> columnValues;

    /**
     *	创建时间
     */
    private Date createTime;

    /**
     *	更新时间
     */
    private Date updateTime;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSampleId() {
        return sampleId;
    }

    public void setSampleId(String sampleId) {
        this.sampleId = sampleId;
    }

    public List<ColumnValue> getColumnValues() {
        return columnValues;
    }

    public void setColumnValues(List<ColumnValue> columnValues) {
        this.columnValues = columnValues;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }
}
