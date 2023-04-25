package com.visual.face.search.server.controller.server.api;

import com.visual.face.search.server.domain.common.ResponseInfo;

import java.util.List;
import java.util.Map;

public interface AdminControllerApi {

    /**
     *获取命名空间集合列表
     * @return  命名空间列表
     */
    public ResponseInfo<List<String>> getNamespaceList();

    /**
     *根据命名空间查看集合列表
     * @param namespace 命名空间
     * @return  集合列表
     */
    public ResponseInfo<List<Map<String, String>>> getCollectList(String namespace);

}
