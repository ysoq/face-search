package com.visual.face.search.server.service.api;

import com.visual.face.search.server.domain.request.CollectReqVo;
import com.visual.face.search.server.domain.response.CollectRepVo;

import java.util.List;

public interface CollectService {

    public Boolean createCollection(CollectReqVo collect);

    public Boolean delete(String namespace, String collectionName);

    public CollectRepVo get(String namespace, String collectionName);

    public List<CollectRepVo> list(String namespace);
}
