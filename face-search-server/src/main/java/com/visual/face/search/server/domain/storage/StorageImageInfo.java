package com.visual.face.search.server.domain.storage;

import java.io.Serializable;

public class StorageImageInfo implements Serializable {
    private String imageRawInfo;
    private String imageEbdInfo;
    private String imageFaceInfo;
    private StorageEngine storageEngine;

    public StorageImageInfo(){}

    public StorageImageInfo(StorageEngine storageEngine, String imageRawInfo, String imageEbdInfo, String imageFaceInfo) {
        this.storageEngine = storageEngine;
        this.imageRawInfo = imageRawInfo;
        this.imageEbdInfo = imageEbdInfo;
        this.imageFaceInfo = imageFaceInfo;
    }

    public StorageEngine getStorageEngine() {
        return storageEngine;
    }

    public void setStorageEngine(StorageEngine storageEngine) {
        this.storageEngine = storageEngine;
    }

    public String getImageRawInfo() {
        return imageRawInfo;
    }

    public void setImageRawInfo(String imageRawInfo) {
        this.imageRawInfo = imageRawInfo;
    }

    public String getImageEbdInfo() {
        return imageEbdInfo;
    }

    public void setImageEbdInfo(String imageEbdInfo) {
        this.imageEbdInfo = imageEbdInfo;
    }

    public String getImageFaceInfo() {
        return imageFaceInfo;
    }

    public void setImageFaceInfo(String imageFaceInfo) {
        this.imageFaceInfo = imageFaceInfo;
    }
}
