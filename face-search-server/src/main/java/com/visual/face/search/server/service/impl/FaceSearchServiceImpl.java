package com.visual.face.search.server.service.impl;

import java.util.*;
import java.util.stream.Collectors;

import com.visual.face.search.core.domain.ExtParam;
import com.visual.face.search.core.domain.FaceImage;
import com.visual.face.search.core.domain.FaceInfo;
import com.visual.face.search.core.domain.ImageMat;
import com.visual.face.search.core.extract.FaceFeatureExtractor;
import com.visual.face.search.core.utils.Similarity;
import com.visual.face.search.server.domain.extend.FaceLocation;
import com.visual.face.search.server.domain.extend.SampleFaceVo;
import com.visual.face.search.server.domain.request.FaceSearchReqVo;
import com.visual.face.search.server.domain.request.SearchAlgorithm;
import com.visual.face.search.server.domain.response.FaceSearchRepVo;
import com.visual.face.search.engine.api.SearchEngine;
import com.visual.face.search.engine.conf.Constant;
import com.visual.face.search.engine.model.SearchDocument;
import com.visual.face.search.engine.model.SearchResponse;
import com.visual.face.search.engine.model.SearchResult;
import com.visual.face.search.server.mapper.CollectMapper;
import com.visual.face.search.server.mapper.FaceDataMapper;
import com.visual.face.search.server.mapper.SampleDataMapper;
import com.visual.face.search.server.model.Collection;
import com.visual.face.search.server.service.api.FaceSearchService;
import com.visual.face.search.server.service.base.BaseService;
import com.visual.face.search.server.utils.ValueUtil;
import org.apache.commons.collections4.MapUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service("visualFaceSearchService")
public class FaceSearchServiceImpl extends BaseService implements FaceSearchService {
    @Value("${visual.face-mask.face-search:false}")
    private boolean faceMask;
    @Resource
    private SearchEngine searchEngine;
    @Resource
    private CollectMapper collectMapper;
    @Resource
    private FaceDataMapper faceDataMapper;
    @Resource
    private SampleDataMapper sampleDataMapper;
    @Resource
    private FaceFeatureExtractor faceFeatureExtractor;

    @Override
    public List<FaceSearchRepVo> search(FaceSearchReqVo search) {
        //查看集合是否存在
        Collection collection = collectMapper.selectByName(search.getNamespace(), search.getCollectionName());
        if(null == collection){
            throw new RuntimeException("collection is not exist");
        }
        if(0 != collection.getStatue()){
            throw new RuntimeException("collection can not use, statue != 0.");
        }
        //获取特征向量
        int maxFaceNum = (null == search.getMaxFaceNum() || search.getMaxFaceNum()  <= 0) ? 5 : search.getMaxFaceNum();
        ExtParam extParam = ExtParam.build().setMask(faceMask).setScoreTh(search.getFaceScoreThreshold() / 100).setIouTh(0).setTopK(maxFaceNum);
        ImageMat imageMat = null;
        FaceImage faceImage = null;
        try {
            imageMat = ImageMat.fromBase64(search.getImageBase64());
            faceImage = faceFeatureExtractor.extract(imageMat, extParam, new HashMap<>());
        }finally {
            if(null != imageMat){
                imageMat.release();
            }
        }
        if(null == faceImage){
            throw new RuntimeException("FeatureExtractor extract error");
        }
        List<FaceInfo> faceInfos = faceImage.faceInfos();
        if(faceInfos.size() <= 0){
            throw new RuntimeException("image is not face");
        }
        float [][] vectors = new float[faceInfos.size()][];
        for(int i=0; i< faceInfos.size(); i++){
            vectors[i] = faceInfos.get(i).embedding.embeds;
        }
        //特征搜索
        int topK = (null == search.getLimit() || search.getLimit()  <= 0) ? 5 : search.getLimit();
        SearchResponse searchResponse =searchEngine.search(collection.getVectorTable(), vectors, search.getAlgorithm().algorithm(), topK);
        if(!searchResponse.getStatus().ok()){
            throw new RuntimeException(searchResponse.getStatus().getReason());
        }
        //结果和人数是否一致
        List<SearchResult> result = searchResponse.getResult();
        if(result.size() > 0 && result.size() != faceInfos.size()){
            throw new RuntimeException("search result error");
        }
        //如数据库中没有任何样本的情况下，会出现异常，这里进行单独处理
        if(result.size() == 0 && faceInfos.size() > 0){
            List<FaceSearchRepVo> vos = new ArrayList<>();
            for(int i=0; i<faceInfos.size(); i++) {
                FaceInfo.FaceBox box = faceInfos.get(i).box;
                FaceSearchRepVo vo = FaceSearchRepVo.build();
                vo.setLocation(FaceLocation.build(box.leftTop.x, box.leftTop.y, box.width(), box.height()));
                vo.setFaceScore((float) Math.floor(faceInfos.get(i).score * 1000000) / 10000);
                List<SampleFaceVo> match = new ArrayList<>();
                vo.setMatch(match);
                vos.add(vo);
            }
            return vos;
        }
        //获取关联数据ID
        Set<String> faceIds = new HashSet<>();
        for(SearchResult searchResult : result){
            List<SearchDocument> documents = searchResult.getDocuments();
            for(SearchDocument document : documents){
                faceIds.add(document.getFaceId());
            }
        }
        //查询数据
        Map<String, Map<String, Object>> faceMapping = new HashMap<>();
        Map<String, Map<String, Object>> sampleMapping = new HashMap<>();
        if(faceIds.size() > 0){
            List<Map<String, Object>> faceList = faceDataMapper.getByFaceIds(collection.getFaceTable(), ValueUtil.getAllFaceColumnNames(collection), new ArrayList<>(faceIds));
            Set<String> sampleIds = faceList.stream().map(item -> MapUtils.getString(item, Constant.ColumnNameSampleId)).collect(Collectors.toSet());
            List<Map<String, Object>> sampleList = sampleDataMapper.getBySampleIds(collection.getSampleTable(), new ArrayList<>(sampleIds));
            faceMapping = ValueUtil.mapping(faceList, Constant.ColumnNameFaceId);
            sampleMapping = ValueUtil.mapping(sampleList, Constant.ColumnNameSampleId);
        }
        //构造返回结果
        List<FaceSearchRepVo> vos = new ArrayList<>();
        for(int i=0; i<faceInfos.size(); i++){
            FaceInfo.FaceBox box = faceInfos.get(i).box;
            FaceSearchRepVo vo = FaceSearchRepVo.build();
            vo.setLocation(FaceLocation.build(box.leftTop.x, box.leftTop.y, box.width(), box.height()));
            vo.setFaceScore((float)Math.floor(faceInfos.get(i).score * 1000000)/10000);
            List<SampleFaceVo> match = new ArrayList<>();
            SearchResult searchResult = result.get(i);
            List<SearchDocument> documents = searchResult.getDocuments();
            for(SearchDocument document : documents){
                Map<String, Object> face = faceMapping.get(document.getFaceId());
                if(null != face){
                    float faceScore = MapUtils.getFloatValue(face, Constant.ColumnNameFaceScore);
                    String sampleId = MapUtils.getString(face, Constant.ColumnNameSampleId);
                    float score = document.getScore();
                    float confidence = score;
                    if(SearchAlgorithm.COSINESIMIL == search.getAlgorithm()){
                        score = Similarity.cosEnhance(score);
                        confidence = (float) Math.floor(score * 1000000)/10000;
                    }
                    if(null != sampleId && sampleMapping.containsKey(sampleId) && confidence >= search.getConfidenceThreshold()){
                        Map<String, Object> sample = sampleMapping.get(sampleId);
                        SampleFaceVo faceVo = SampleFaceVo.build();
                        faceVo.setSampleId(sampleId);
                        faceVo.setFaceId(document.getFaceId());
                        faceVo.setFaceScore(faceScore);
                        faceVo.setConfidence(confidence);
                        faceVo.setFaceData(ValueUtil.getFieldKeyValues(face, ValueUtil.getFaceColumns(collection)));
                        faceVo.setSampleData(ValueUtil.getFieldKeyValues(sample, ValueUtil.getSampleColumns(collection)));
                        match.add(faceVo);
                    }
                }
            }
            //排序
            Collections.sort(match);
            vo.setMatch(match);
            vos.add(vo);
        }
        return vos;
    }

}
