package com.visual.face.search.server.service.impl;

import com.visual.face.search.server.domain.storage.StorageDataInfo;
import com.visual.face.search.server.domain.storage.StorageImageInfo;
import com.visual.face.search.server.service.api.StorageImageService;
import org.springframework.stereotype.Service;

@Service("visualStorageImageServiceDataBase")
public class StorageImageServiceDataBaseImpl implements StorageImageService {

    @Override
    public StorageDataInfo create(StorageImageInfo storageImageInfo) {
        return new StorageDataInfo(
                storageImageInfo.getStorageEngine(),
                storageImageInfo.getImageRawInfo(),
                storageImageInfo.getImageEbdInfo(),
                storageImageInfo.getImageFaceInfo()
        );
    }

}
