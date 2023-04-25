package com.visual.face.search.server.controller.server.impl;

import javax.annotation.Resource;
import com.visual.face.search.server.controller.base.BaseController;
import com.visual.face.search.server.controller.server.api.FaceCompareControllerApi;
import com.visual.face.search.server.domain.common.ResponseInfo;
import com.visual.face.search.server.domain.request.FaceCompareReqVo;
import com.visual.face.search.server.domain.response.FaceCompareRepVo;
import com.visual.face.search.server.service.api.FaceCompareService;
import com.visual.face.search.server.utils.ResponseBuilder;


public class FaceCompareControllerImpl extends BaseController implements FaceCompareControllerApi {

    @Resource
    private FaceCompareService faceCompareService;

    @Override
    public ResponseInfo<FaceCompareRepVo> faceCompare(FaceCompareReqVo compareReq) {
        try {
            return ResponseBuilder.success(faceCompareService.faceCompare(compareReq));
        }catch (Exception e){
            logger.error("do faceCompare exception:", e);
            return ResponseBuilder.exception(e.getMessage(), null);
        }
    }

}
