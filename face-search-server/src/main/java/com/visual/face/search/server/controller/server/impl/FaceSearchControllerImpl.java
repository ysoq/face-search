package com.visual.face.search.server.controller.server.impl;

import com.visual.face.search.server.controller.base.BaseController;
import com.visual.face.search.server.controller.server.api.FaceSearchControllerApi;
import com.visual.face.search.server.domain.common.ResponseInfo;
import com.visual.face.search.server.domain.request.FaceSearchReqVo;
import com.visual.face.search.server.domain.response.FaceSearchRepVo;
import com.visual.face.search.server.domain.response.SampleDataRepVo;
import com.visual.face.search.server.service.api.FaceSearchService;
import com.visual.face.search.server.utils.ResponseBuilder;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class FaceSearchControllerImpl extends BaseController implements FaceSearchControllerApi {

    @Autowired
    private FaceSearchService faceSearchService;

    @Override
    public ResponseInfo<List<FaceSearchRepVo>> search(FaceSearchReqVo search) {
        try {
            return ResponseBuilder.success(faceSearchService.search(search));
        }catch (Exception e){
            logger.error("List sample exception:", e);
            return ResponseBuilder.exception(e.getMessage(), null);
        }
    }

}
