package com.visual.face.search.server.controller.server.api;

import java.util.List;
import com.visual.face.search.server.domain.common.ResponseInfo;
import com.visual.face.search.server.domain.request.FaceSearchReqVo;
import com.visual.face.search.server.domain.response.FaceSearchRepVo;


public interface FaceSearchControllerApi {

    /**
     * 人脸搜索
     * @param search 搜索条件
     * @return  获取当前匹配的列表
     */
    public ResponseInfo<List<FaceSearchRepVo>> search(FaceSearchReqVo search);

}
