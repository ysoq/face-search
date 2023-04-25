package com.visual.face.search.server.controller.server.impl;

import com.visual.face.search.server.controller.base.BaseController;
import com.visual.face.search.server.controller.server.api.CollectControllerApi;
import com.visual.face.search.server.domain.common.ResponseInfo;
import com.visual.face.search.server.domain.request.CollectReqVo;
import com.visual.face.search.server.domain.response.CollectRepVo;
import com.visual.face.search.server.service.api.CollectService;
import com.visual.face.search.server.utils.ResponseBuilder;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class CollectControllerImpl extends BaseController implements CollectControllerApi {

    @Autowired
    private CollectService collectService;

    @Override
    public ResponseInfo<Boolean> create(CollectReqVo collect) {
        try {
            boolean flag = collectService.createCollection(collect);
            if(flag){
                return ResponseBuilder.success(true);
            }else{
                return ResponseBuilder.error(false);
            }
        }catch (Exception e){
            logger.error("Create collect exception:", e);
            return ResponseBuilder.exception(e.getMessage(), false);
        }
    }

    @Override
    public ResponseInfo<Boolean> delete(String namespace, String collectionName) {
        try {
            boolean flag = collectService.delete(namespace, collectionName);
            if(flag){
                return ResponseBuilder.success(true);
            }else{
                return ResponseBuilder.error(false);
            }
        }catch (Exception e){
            logger.error("delete collect exception:", e);
            return ResponseBuilder.exception(e.getMessage(), false);
        }
    }

    @Override
    public ResponseInfo<CollectRepVo> get(String namespace, String collectionName) {
        try {
            CollectRepVo vo = collectService.get(namespace, collectionName);
            if(null != vo){
                return ResponseBuilder.success(vo);
            }else{
                return ResponseBuilder.error(null);
            }
        }catch (Exception e){
            logger.error("get collect exception:", e);
            return ResponseBuilder.exception(e.getMessage(), null);
        }
    }

    @Override
    public ResponseInfo<List<CollectRepVo>> list(String namespace) {
        try {
            List<CollectRepVo> vos = collectService.list(namespace);
            return ResponseBuilder.success(vos);
        }catch (Exception e){
            logger.error("get collect list exception:", e);
            return ResponseBuilder.exception(e.getMessage(), null);
        }
    }
}
