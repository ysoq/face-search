package com.visual.face.search.server.service.api;

import com.visual.face.search.server.domain.storage.StorageDataInfo;
import com.visual.face.search.server.domain.storage.StorageEngine;
import com.visual.face.search.server.domain.storage.StorageImageInfo;
import com.visual.face.search.server.service.impl.StorageImageServiceDataBaseImpl;
import com.visual.face.search.server.utils.SpringUtils;

public interface StorageImageService {

    public StorageDataInfo create(StorageImageInfo storageImageInfo);

    public static class Factory{

        private static StorageImageService get(StorageEngine storageEngine){
            switch (storageEngine){
                case CURR_DB:
                    return SpringUtils.getBean(StorageImageServiceDataBaseImpl.class);
                default:
                    return null;
            }
        }

        public static StorageDataInfo create(StorageImageInfo storageImageInfo){
            return get(storageImageInfo.getStorageEngine()).create(storageImageInfo);
        }

    }
}
