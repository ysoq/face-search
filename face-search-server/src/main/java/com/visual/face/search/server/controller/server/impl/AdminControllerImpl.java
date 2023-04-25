package com.visual.face.search.server.controller.server.impl;

import com.visual.face.search.server.controller.base.BaseController;
import com.visual.face.search.server.controller.server.api.AdminControllerApi;
import com.visual.face.search.server.domain.common.ResponseInfo;

import java.util.List;
import java.util.Map;

public class AdminControllerImpl extends BaseController implements AdminControllerApi {

    @Override
    public ResponseInfo<List<String>> getNamespaceList() {
        return null;
    }

    @Override
    public ResponseInfo<List<Map<String, String>>> getCollectList(String namespace) {
        return null;
    }
}
