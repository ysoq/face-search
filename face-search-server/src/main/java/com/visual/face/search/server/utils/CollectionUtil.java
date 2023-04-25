package com.visual.face.search.server.utils;

import com.alibaba.fastjson.JSONObject;
import com.visual.face.search.core.utils.JsonUtil;
import com.visual.face.search.server.domain.storage.StorageEngine;
import com.visual.face.search.server.domain.storage.StorageInfo;

public class CollectionUtil {

    public static StorageInfo getStorageInfo(String schemaInfo){
        StorageInfo storageInfo = new StorageInfo();
        try {
            JSONObject json = JsonUtil.toJsonObject(schemaInfo);
            storageInfo.setStorageFaceInfo(json.getBooleanValue("storageFaceInfo"));
            String storageEngineStr = json.getString("storageEngine");
            if(StringUtils.isNotEmpty(storageEngineStr)){
                storageInfo.setStorageEngine(StorageEngine.valueOf(storageEngineStr));
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        if(storageInfo.isStorageFaceInfo() && null == storageInfo.getStorageEngine()){
            storageInfo.setStorageEngine(StorageEngine.CURR_DB);
        }
        return storageInfo;
    }
}
