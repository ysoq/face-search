package com.visual.face.search.server.model;

import java.util.Date;

public class Collection {
    /**
     *	自增主键自增
     */
    private Long id;

    /**
     *	字符唯一键
     */
    private String uuid;

    /**
     *	命名空间
     */
    private String namespace;

    /**
     *	集合名称
     */
    private String collection;

    /**
     *	集合描述
     */
    private String describe;

    /**
     *	集合状态
     */
    private Integer statue;

    /**
     *	样本数据表
     */
    private String sampleTable;

    /**
     *	人脸数据表
     */
    private String faceTable;

    /**
     *	图片数据表
     */
    private String imageTable;

    /**
     *	人脸向量库
     */
    private String vectorTable;

    /**
     *	集合元数据信息
     */
    private String schemaInfo;

    /**
     *	创建时间
     */
    private Date createTime;

    /**
     *	更新时间
     */
    private Date updateTime;

    /**
     *	是否删除
     */
    private Boolean deleted;

    /**
     *	自增主键自增
     *	@return id 自增主键自增
     */
    public Long getId() {
        return id;
    }

    /**
     *	自增主键自增
     *	@param id 自增主键自增
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     *	字符唯一键
     *	@return uuid 字符唯一键
     */
    public String getUuid() {
        return uuid;
    }

    /**
     *	字符唯一键
     *	@param uuid 字符唯一键
     */
    public void setUuid(String uuid) {
        this.uuid = uuid == null ? null : uuid.trim();
    }

    /**
     *	命名空间
     *	@return namespace 命名空间
     */
    public String getNamespace() {
        return namespace;
    }

    /**
     *	命名空间
     *	@param namespace 命名空间
     */
    public void setNamespace(String namespace) {
        this.namespace = namespace == null ? null : namespace.trim();
    }

    /**
     *	集合名称
     *	@return collection 集合名称
     */
    public String getCollection() {
        return collection;
    }

    /**
     *	集合名称
     *	@param collection 集合名称
     */
    public void setCollection(String collection) {
        this.collection = collection == null ? null : collection.trim();
    }

    /**
     *	集合描述
     *	@return describe 集合描述
     */
    public String getDescribe() {
        return describe;
    }

    /**
     *	集合描述
     *	@param describe 集合描述
     */
    public void setDescribe(String describe) {
        this.describe = describe == null ? null : describe.trim();
    }

    /**
     *	集合状态
     *	@return statue 集合状态
     */
    public Integer getStatue() {
        return statue;
    }

    /**
     *	集合状态
     *	@param statue 集合状态
     */
    public void setStatue(Integer statue) {
        this.statue = statue;
    }

    /**
     *	样本数据表
     *	@return sample_table 样本数据表
     */
    public String getSampleTable() {
        return sampleTable;
    }

    /**
     *	样本数据表
     *	@param sampleTable 样本数据表
     */
    public void setSampleTable(String sampleTable) {
        this.sampleTable = sampleTable == null ? null : sampleTable.trim();
    }

    /**
     *	人脸数据表
     *	@return face_table 人脸数据表
     */
    public String getFaceTable() {
        return faceTable;
    }

    /**
     *	人脸数据表
     *	@param faceTable 人脸数据表
     */
    public void setFaceTable(String faceTable) {
        this.faceTable = faceTable == null ? null : faceTable.trim();
    }

    /**
     *	图片数据表
     *	@return image_table 图片数据表
     */
    public String getImageTable() {
        return imageTable;
    }

    /**
     *	图片数据表
     *	@param imageTable 图片数据表
     */
    public void setImageTable(String imageTable) {
        this.imageTable = imageTable == null ? null : imageTable.trim();
    }

    /**
     *	人脸向量库
     *	@return vector_table 人脸向量库
     */
    public String getVectorTable() {
        return vectorTable;
    }

    /**
     *	人脸向量库
     *	@param vectorTable 人脸向量库
     */
    public void setVectorTable(String vectorTable) {
        this.vectorTable = vectorTable == null ? null : vectorTable.trim();
    }

    /**
     *	集合元数据信息
     *	@return schema_info 集合元数据信息
     */
    public String getSchemaInfo() {
        return schemaInfo;
    }

    /**
     *	集合元数据信息
     *	@param schemaInfo 集合元数据信息
     */
    public void setSchemaInfo(String schemaInfo) {
        this.schemaInfo = schemaInfo == null ? null : schemaInfo.trim();
    }

    /**
     *	创建时间
     *	@return create_time 创建时间
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     *	创建时间
     *	@param createTime 创建时间
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    /**
     *	更新时间
     *	@return update_time 更新时间
     */
    public Date getUpdateTime() {
        return updateTime;
    }

    /**
     *	更新时间
     *	@param updateTime 更新时间
     */
    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    /**
     *	是否删除
     *	@return deleted 是否删除
     */
    public Boolean getDeleted() {
        return deleted;
    }

    /**
     *	是否删除
     *	@param deleted 是否删除
     */
    public void setDeleted(Boolean deleted) {
        this.deleted = deleted;
    }
}