package com.visual.face.search.server.domain.storage;

import java.io.Serializable;

public class StorageInfo implements Serializable {

    private boolean storageFaceInfo;
    private StorageEngine storageEngine;

    public boolean isStorageFaceInfo() {
        return storageFaceInfo;
    }

    public void setStorageFaceInfo(Boolean storageFaceInfo) {
        if(null != storageFaceInfo){
            this.storageFaceInfo = storageFaceInfo;
        }
    }

    public StorageEngine getStorageEngine() {
        return storageEngine;
    }

    public void setStorageEngine(StorageEngine storageEngine) {
        this.storageEngine = storageEngine;
    }
}
