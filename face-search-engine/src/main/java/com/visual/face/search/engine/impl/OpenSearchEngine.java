package com.visual.face.search.engine.impl;

import com.visual.face.search.engine.api.SearchEngine;
import com.visual.face.search.engine.conf.Constant;
import com.visual.face.search.engine.exps.SearchEngineException;
import com.visual.face.search.engine.model.*;
import org.apache.commons.collections4.MapUtils;
import org.opensearch.action.DocWriteResponse;
import org.opensearch.action.admin.indices.delete.DeleteIndexRequest;
import org.opensearch.action.delete.DeleteRequest;
import org.opensearch.action.delete.DeleteResponse;
import org.opensearch.action.index.IndexRequest;
import org.opensearch.action.index.IndexResponse;
import org.opensearch.action.search.MultiSearchRequest;
import org.opensearch.action.search.MultiSearchResponse;
import org.opensearch.action.search.SearchRequest;
import org.opensearch.client.RequestOptions;
import org.opensearch.client.RestHighLevelClient;
import org.opensearch.client.indices.CreateIndexRequest;
import org.opensearch.client.indices.CreateIndexResponse;
import org.opensearch.client.indices.GetIndexRequest;
import org.opensearch.common.settings.Settings;
import org.opensearch.index.query.*;
import org.opensearch.index.query.functionscore.ScriptScoreQueryBuilder;
import org.opensearch.index.reindex.BulkByScrollResponse;
import org.opensearch.index.reindex.DeleteByQueryRequest;
import org.opensearch.rest.RestStatus;
import org.opensearch.script.Script;
import org.opensearch.search.SearchHit;
import org.opensearch.search.builder.SearchSourceBuilder;
import java.io.IOException;
import java.util.*;

public class OpenSearchEngine implements SearchEngine {

    private RestHighLevelClient client;
    private MapParam params = new MapParam();

    public OpenSearchEngine(RestHighLevelClient client){
        this(client, null);
    }

    public OpenSearchEngine(RestHighLevelClient client, MapParam params){
        this.client = client;
        if(null != params) { this.params = params; }
    }

    @Override
    public Object getEngine() {
        return this.client;
    }

    @Override
    public boolean exist(String collectionName) {
        try {
            GetIndexRequest request = new GetIndexRequest(collectionName);
            return this.client.indices().exists(request, RequestOptions.DEFAULT);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean dropCollection(String collectionName) {
        try {
            DeleteIndexRequest request = new DeleteIndexRequest(collectionName);
            return this.client.indices().delete(request, RequestOptions.DEFAULT).isAcknowledged();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean createCollection(String collectionName, MapParam param) {
        try {
            //构建请求
            CreateIndexRequest createIndexRequest = new CreateIndexRequest(collectionName);
            createIndexRequest.settings(Settings.builder()
                .put("index.number_of_shards", param.getIndexShardsNum())
                .put("index.number_of_replicas", param.getIndexReplicasNum())
            );
            HashMap<String, Object> properties = new HashMap<>();
            properties.put(Constant.ColumnNameSampleId, Map.of("type", "keyword"));
            properties.put(Constant.ColumnNameFaceVector, Map.of("type", "knn_vector", "dimension", "512"));
            createIndexRequest.mapping(Map.of("properties", properties));
            //创建集合
            CreateIndexResponse createIndexResponse = client.indices().create(createIndexRequest, RequestOptions.DEFAULT);
            return createIndexResponse.isAcknowledged();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean insertVector(String collectionName, String sampleId, String faceId, float[] vectors) {
        try {
        //构建请求
            IndexRequest request = new IndexRequest(collectionName)
                .id(faceId)
                .source(Map.of(
                    Constant.ColumnNameSampleId, sampleId,
                    Constant.ColumnNameFaceVector, vectors
                ));
            //插入数据
            IndexResponse indexResponse = client.index(request, RequestOptions.DEFAULT);
            DocWriteResponse.Result result = indexResponse.getResult();
            return DocWriteResponse.Result.CREATED == result || DocWriteResponse.Result.UPDATED == result;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean deleteVectorByKey(String collectionName, String faceId) {
        try {
            DeleteRequest deleteDocumentRequest = new DeleteRequest(collectionName, faceId);
            DeleteResponse deleteResponse = client.delete(deleteDocumentRequest, RequestOptions.DEFAULT);
            return DocWriteResponse.Result.DELETED == deleteResponse.getResult();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean deleteVectorByKey(String collectionName, List<String> keyIds) {
        try {
            String[] idArray = new String[keyIds.size()]; idArray = keyIds.toArray(idArray);
            QueryBuilder queryBuilder = new BoolQueryBuilder().must(QueryBuilders.idsQuery().addIds(idArray));
            DeleteByQueryRequest request = new DeleteByQueryRequest(collectionName).setQuery(queryBuilder);
            BulkByScrollResponse response = client.deleteByQuery(request, RequestOptions.DEFAULT);
            return response.getBulkFailures() != null && response.getBulkFailures().size() == 0;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public SearchResponse search(String collectionName, float[][] features, String algorithm, int topK) {
        try {
            //构建搜索请求
            MultiSearchRequest multiSearchRequest = new MultiSearchRequest();
            for(float[] feature : features){
                QueryBuilder queryBuilder = new MatchAllQueryBuilder();
                Map<String, Object> params = new HashMap<>();
                params.put("field", Constant.ColumnNameFaceVector);
                params.put("space_type", algorithm);
                params.put("query_value", feature);
                Script script = new Script(Script.DEFAULT_SCRIPT_TYPE, "knn", "knn_score", params);
                ScriptScoreQueryBuilder scriptScoreQueryBuilder = new ScriptScoreQueryBuilder(queryBuilder, script);
                SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder()
                    .query(scriptScoreQueryBuilder).size(topK)
                    .fetchSource(null, Constant.ColumnNameFaceVector); //是否需要向量字段
                SearchRequest searchRequest = new SearchRequest(collectionName).source(searchSourceBuilder);
                multiSearchRequest.add(searchRequest);
            }
            //查询索引
            MultiSearchResponse response = this.client.msearch(multiSearchRequest, RequestOptions.DEFAULT);
            MultiSearchResponse.Item[] responses = response.getResponses();
            if(features.length != responses.length){
                throw new SearchEngineException("features.length != responses.length");
            }
            //解析数据
            List<SearchResult> result = new ArrayList<>();
            for(MultiSearchResponse.Item item : response.getResponses()){
                List<SearchDocument> documents = new ArrayList<>();
                SearchHit[] searchHits = item.getResponse().getHits().getHits();
                if(searchHits != null){
                    for(SearchHit searchHit : searchHits){
                        String faceId = searchHit.getId();
                        float score = searchHit.getScore()-1;
                        Map<String, Object> sourceMap = searchHit.getSourceAsMap();
                        String sampleId = MapUtils.getString(sourceMap, Constant.ColumnNameSampleId);
                        Object faceVector = MapUtils.getObject(sourceMap, Constant.ColumnNameFaceVector);
                        SearchDocument document = SearchDocument.build(sampleId, faceId, score).setVectors(faceVector);
                        documents.add(document);
                    }
                }
                result.add(SearchResult.build(documents));
            }
            //返回结果信息
            return SearchResponse.build(SearchStatus.build(0, "success"), result);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public float searchMinScoreBySampleId(String collectionName, String sampleId,float[] feature, String algorithm) {
        try {
            //构建请求
            QueryBuilder queryBuilder = new MatchQueryBuilder(Constant.ColumnNameSampleId, sampleId);
            Map<String, Object> params = new HashMap<>();
            params.put("field", Constant.ColumnNameFaceVector);
            params.put("space_type", algorithm);
            params.put("query_value", feature);
            Script script = new Script(Script.DEFAULT_SCRIPT_TYPE, "knn", "knn_score", params);
            ScriptScoreQueryBuilder scriptScoreQueryBuilder = new ScriptScoreQueryBuilder(queryBuilder, script);
            SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder()
                    .query(scriptScoreQueryBuilder)
                    .fetchSource(false).size(10000); //是否需要索引字段
            SearchRequest searchRequest = new SearchRequest(collectionName).source(searchSourceBuilder);
            //搜索请求
            org.opensearch.action.search.SearchResponse response = this.client.search(searchRequest, RequestOptions.DEFAULT);
            if(RestStatus.OK == response.status()){
                SearchHit[] searchHits = response.getHits().getHits();
                Double minScore = Arrays.stream(searchHits).mapToDouble(SearchHit::getScore).min().orElse(2f);
                return minScore.floatValue()-1;
            }else{
                throw new RuntimeException("get score error!");
            }
        } catch (Exception e) {
            throw new SearchEngineException(e);
        }
    }

    @Override
    public float searchMaxScoreBySampleId(String collectionName, String sampleId,float[] feature, String algorithm) {
        try {
            //构建请求
            QueryBuilder queryBuilder = new BoolQueryBuilder()
                    .mustNot(new MatchQueryBuilder(Constant.ColumnNameSampleId, sampleId));
            Map<String, Object> params = new HashMap<>();
            params.put("field", Constant.ColumnNameFaceVector);
            params.put("space_type", algorithm);
            params.put("query_value", feature);
            Script script = new Script(Script.DEFAULT_SCRIPT_TYPE, "knn", "knn_score", params);
            ScriptScoreQueryBuilder scriptScoreQueryBuilder = new ScriptScoreQueryBuilder(queryBuilder, script);
            SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder()
                    .query(scriptScoreQueryBuilder)
                    .fetchSource(false).size(1); //是否需要索引字段
            SearchRequest searchRequest = new SearchRequest(collectionName).source(searchSourceBuilder);
            //搜索请求
            org.opensearch.action.search.SearchResponse response = this.client.search(searchRequest, RequestOptions.DEFAULT);
            if(RestStatus.OK == response.status()){
                SearchHit[] searchHits = response.getHits().getHits();
                Double maxScore = Arrays.stream(searchHits).mapToDouble(SearchHit::getScore).max().orElse(1f);
                return maxScore.floatValue()-1;
            }else{
                throw new RuntimeException("get score error!");
            }
        } catch (Exception e) {
            throw new SearchEngineException(e);
        }
    }


}
