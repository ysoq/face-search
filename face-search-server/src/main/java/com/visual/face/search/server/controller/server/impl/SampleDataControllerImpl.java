package com.visual.face.search.server.controller.server.impl;

import com.visual.face.search.server.controller.base.BaseController;
import com.visual.face.search.server.controller.server.api.SampleDataControllerApi;
import com.visual.face.search.server.domain.common.ResponseInfo;
import com.visual.face.search.server.domain.request.SampleDataReqVo;
import com.visual.face.search.server.domain.response.SampleDataRepVo;
import com.visual.face.search.server.service.api.SampleDataService;
import com.visual.face.search.server.utils.ResponseBuilder;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class SampleDataControllerImpl extends BaseController implements SampleDataControllerApi {

    @Autowired
    private SampleDataService sampleDataService;

    @Override
    public ResponseInfo<Boolean> create(SampleDataReqVo sample) {
        try {
            boolean flag = sampleDataService.create(sample);
            if(flag){
                return ResponseBuilder.success(true);
            }else{
                return ResponseBuilder.error(false);
            }
        }catch (Exception e){
            logger.error("Create sample exception:", e);
            return ResponseBuilder.exception(e.getMessage(), false);
        }
    }

    @Override
    public ResponseInfo<Boolean> update(SampleDataReqVo sample) {
        try {
            boolean flag = sampleDataService.update(sample);
            if(flag){
                return ResponseBuilder.success(true);
            }else{
                return ResponseBuilder.error(false);
            }
        }catch (Exception e){
            logger.error("Update sample exception:", e);
            return ResponseBuilder.exception(e.getMessage(), false);
        }
    }

    @Override
    public ResponseInfo<Boolean> delete(String namespace, String collectionName, String sampleId) {
        try {
            boolean flag = sampleDataService.delete(namespace, collectionName, sampleId);
            if(flag){
                return ResponseBuilder.success(true);
            }else{
                return ResponseBuilder.error(false);
            }
        }catch (Exception e){
            logger.error("Delete sample exception:", e);
            return ResponseBuilder.exception(e.getMessage(), false);
        }
    }

    @Override
    public ResponseInfo<SampleDataRepVo> get(String namespace, String collectionName, String sampleId) {
        try {
            SampleDataRepVo vo = sampleDataService.getSample(namespace, collectionName, sampleId);
            if(null != vo){
                return ResponseBuilder.success(vo);
            }else{
                return ResponseBuilder.error("sample id is not exist");
            }
        }catch (Exception e){
            logger.error("Delete sample exception:", e);
            return ResponseBuilder.exception(e.getMessage(), null);
        }
    }

    @Override
    public ResponseInfo<List<SampleDataRepVo>> list(String namespace, String collectionName, Integer offset, Integer limit, String order) {
        try {
            offset = (null == offset || offset <= 0) ? 0  : offset;
            limit  = (null == limit  || limit <=0 )  ? 10 : limit;
            order  = (null == order  || !order.equalsIgnoreCase("desc") )  ? "asc" : order;
            List<SampleDataRepVo> vos = sampleDataService.list(namespace, collectionName, offset, limit, order);
            return ResponseBuilder.success(vos);
        }catch (Exception e){
            logger.error("List sample exception:", e);
            return ResponseBuilder.exception(e.getMessage(), null);
        }
    }

}
