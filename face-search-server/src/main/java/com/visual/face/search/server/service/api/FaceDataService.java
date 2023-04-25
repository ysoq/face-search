package com.visual.face.search.server.service.api;

import com.visual.face.search.server.domain.request.FaceDataReqVo;
import com.visual.face.search.server.domain.response.FaceDataRepVo;

public interface FaceDataService {

    public FaceDataRepVo create(FaceDataReqVo sample);

    public Boolean delete(String namespace, String collectionName, String sampleId, String faceId);
}
