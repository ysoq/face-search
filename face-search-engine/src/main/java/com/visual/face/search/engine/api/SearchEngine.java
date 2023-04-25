package com.visual.face.search.engine.api;


import com.visual.face.search.engine.model.MapParam;
import com.visual.face.search.engine.model.SearchResponse;

import java.util.List;

public interface SearchEngine {

    public Object getEngine();

    public boolean exist(String collectionName);

    public boolean dropCollection(String collectionName);

    public boolean createCollection(String collectionName, MapParam param);

    public boolean insertVector(String collectionName, String sampleId, String faceId, float[] vectors);

    public boolean deleteVectorByKey(String collectionName, String faceId);

    public boolean deleteVectorByKey(String collectionName, List<String> faceIds);

    public SearchResponse search(String collectionName, float[][] features, String algorithm, int topK);

    public float searchMinScoreBySampleId(String collectionName, String sampleId,float[] feature, String algorithm);

    public float searchMaxScoreBySampleId(String collectionName, String sampleId,float[] feature, String algorithm);
}
