package com.visual.face.search.server.service.api;

import com.visual.face.search.server.domain.request.FaceSearchReqVo;
import com.visual.face.search.server.domain.response.FaceSearchRepVo;

import java.util.List;

public interface FaceSearchService {

    public List<FaceSearchRepVo> search(FaceSearchReqVo search);

}
