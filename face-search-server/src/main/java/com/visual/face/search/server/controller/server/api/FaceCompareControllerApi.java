package com.visual.face.search.server.controller.server.api;

import com.visual.face.search.server.domain.common.ResponseInfo;
import com.visual.face.search.server.domain.request.FaceCompareReqVo;
import com.visual.face.search.server.domain.response.FaceCompareRepVo;

/**
 * 人脸1：1比对
 */
public interface FaceCompareControllerApi {

    /**
     * 人脸比对1：1接口
     * @param compareReq
     * @return
     */
    public ResponseInfo<FaceCompareRepVo> faceCompare(FaceCompareReqVo compareReq);

}
