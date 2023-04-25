package com.visual.face.search.server.service.api;

import com.visual.face.search.server.domain.request.FaceCompareReqVo;
import com.visual.face.search.server.domain.response.FaceCompareRepVo;

public interface FaceCompareService {

    public FaceCompareRepVo faceCompare(FaceCompareReqVo compareReq);

}
