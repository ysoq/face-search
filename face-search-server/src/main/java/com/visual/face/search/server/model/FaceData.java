package com.visual.face.search.server.model;

import java.util.Date;
import java.util.List;

public class FaceData {

    /**
     *	自增主键自增
     */
    private Long id;

    /**样本ID**/
    private String sampleId;

    /**样本ID**/
    private String faceId;

    /**人脸人数质量**/
    private Float faceScore;

    /**人脸向量**/
    private String faceVector;

    /**数据信息**/
    private List<ColumnValue> columnValues;

    /**创建时间*/
    private Date createTime;

    /**更新时间*/
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

    public String getFaceId() {
        return faceId;
    }

    public void setFaceId(String faceId) {
        this.faceId = faceId;
    }

    public Float getFaceScore() {
        return faceScore;
    }

    public void setFaceScore(Float faceScore) {
        this.faceScore = faceScore;
    }

    public String getFaceVector() {
        return faceVector;
    }

    public void setFaceVector(String faceVector) {
        this.faceVector = faceVector;
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
