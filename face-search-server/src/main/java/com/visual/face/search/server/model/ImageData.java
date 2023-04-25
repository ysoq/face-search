package com.visual.face.search.server.model;

import java.util.Date;

public class ImageData {
    /**
     *	主键、自增、无意
     */
    private Long id;

    /**
     *	样本ID
     */
    private String sampleId;

    /**
     *	人脸ID
     */
    private String faceId;

    /**
     *	数据存储类型
     */
    private String storageType;

    /**
     *	原始图片数据
     */
    private String imageRawInfo;

    /**
     *	用于提取特征的人脸图片
     */
    private String imageEbdInfo;

    /**
     *	图片人脸检测信息
     */
    private String imageFaceInfo;

    /**
     *	创建时间
     */
    private Date createTime;

    /**
     *	更新时间
     */
    private Date modifyTime;

    /**
     *	主键、自增、无意
     *	@return id 主键、自增、无意
     */
    public Long getId() {
        return id;
    }

    /**
     *	主键、自增、无意
     *	@param id 主键、自增、无意
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     *	样本ID
     *	@return sample_id 样本ID
     */
    public String getSampleId() {
        return sampleId;
    }

    /**
     *	样本ID
     *	@param sampleId 样本ID
     */
    public void setSampleId(String sampleId) {
        this.sampleId = sampleId == null ? null : sampleId.trim();
    }

    /**
     *	人脸ID
     *	@return face_id 人脸ID
     */
    public String getFaceId() {
        return faceId;
    }

    /**
     *	人脸ID
     *	@param faceId 人脸ID
     */
    public void setFaceId(String faceId) {
        this.faceId = faceId == null ? null : faceId.trim();
    }

    /**
     *	数据存储类型
     *	@return storage_type 数据存储类型
     */
    public String getStorageType() {
        return storageType;
    }

    /**
     *	数据存储类型
     *	@param storageType 数据存储类型
     */
    public void setStorageType(String storageType) {
        this.storageType = storageType == null ? null : storageType.trim();
    }

    /**
     *	原始图片数据
     *	@return image_raw_info 原始图片数据
     */
    public String getImageRawInfo() {
        return imageRawInfo;
    }

    /**
     *	原始图片数据
     *	@param imageRawInfo 原始图片数据
     */
    public void setImageRawInfo(String imageRawInfo) {
        this.imageRawInfo = imageRawInfo == null ? null : imageRawInfo.trim();
    }

    /**
     *	用于提取特征的人脸图片
     *	@return image_ebd_info 用于提取特征的人脸图片
     */
    public String getImageEbdInfo() {
        return imageEbdInfo;
    }

    /**
     *	用于提取特征的人脸图片
     *	@param imageEbdInfo 用于提取特征的人脸图片
     */
    public void setImageEbdInfo(String imageEbdInfo) {
        this.imageEbdInfo = imageEbdInfo == null ? null : imageEbdInfo.trim();
    }

    /**
     *	图片人脸检测信息
     *	@return image_face_info 图片人脸检测信息
     */
    public String getImageFaceInfo() {
        return imageFaceInfo;
    }

    /**
     *	图片人脸检测信息
     *	@param imageFaceInfo 图片人脸检测信息
     */
    public void setImageFaceInfo(String imageFaceInfo) {
        this.imageFaceInfo = imageFaceInfo == null ? null : imageFaceInfo.trim();
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
     *	@return modify_time 更新时间
     */
    public Date getModifyTime() {
        return modifyTime;
    }

    /**
     *	更新时间
     *	@param modifyTime 更新时间
     */
    public void setModifyTime(Date modifyTime) {
        this.modifyTime = modifyTime;
    }
}