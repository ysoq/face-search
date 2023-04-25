package com.visual.face.search.server.controller.server.impl;

import com.visual.face.search.server.controller.base.BaseController;
import com.visual.face.search.server.controller.server.api.FaceDataControllerApi;
import com.visual.face.search.server.domain.common.ResponseInfo;
import com.visual.face.search.server.domain.request.FaceDataReqVo;
import com.visual.face.search.server.domain.response.FaceDataRepVo;
import com.visual.face.search.server.service.api.FaceDataService;
import com.visual.face.search.server.utils.ResponseBuilder;
import org.springframework.beans.factory.annotation.Autowired;

public class FaceDataControllerImpl extends BaseController implements FaceDataControllerApi {

    @Autowired
    private FaceDataService faceDataService;

    @Override
    public ResponseInfo<FaceDataRepVo> create(FaceDataReqVo face) {
        try {
            FaceDataRepVo faceDataRep = faceDataService.create(face);
            if(null != faceDataRep){
                return ResponseBuilder.success(faceDataRep);
            }else{
                return ResponseBuilder.error(null);
            }
        }catch (Exception e){
            logger.error("Create face exception:", e);
            return ResponseBuilder.exception(e.getMessage(), null);
        }
    }

    @Override
    public ResponseInfo<Boolean> delete(String namespace, String collectionName, String sampleId, String faceId) {
        try {
            boolean flag = faceDataService.delete(namespace, collectionName, sampleId, faceId);
            if(flag){
                return ResponseBuilder.success(true);
            }else{
                return ResponseBuilder.error(false);
            }
        }catch (Exception e){
            logger.error("Delete face exception:", e);
            return ResponseBuilder.exception(e.getMessage(), false);
        }
    }

}
