package com.visual.face.search.server.service.api;

import com.visual.face.search.server.domain.request.SampleDataReqVo;
import com.visual.face.search.server.domain.response.SampleDataRepVo;

import java.util.List;

public interface SampleDataService {

    public Boolean create(SampleDataReqVo sample);

    public Boolean update(SampleDataReqVo sample);

    public Boolean delete(String namespace, String collectionName, String sampleId);

    public SampleDataRepVo getSample(String namespace, String collectionName, String sampleId);

    public List<SampleDataRepVo> list(String namespace, String collectionName, Integer offset, Integer limit, String order);
}
