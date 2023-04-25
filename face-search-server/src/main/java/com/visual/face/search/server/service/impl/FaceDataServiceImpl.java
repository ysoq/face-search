package com.visual.face.search.server.service.impl;

import com.visual.face.search.engine.api.SearchEngine;
import com.visual.face.search.core.domain.ExtParam;
import com.visual.face.search.core.domain.FaceImage;
import com.visual.face.search.core.domain.FaceInfo;
import com.visual.face.search.core.domain.ImageMat;
import com.visual.face.search.core.extract.FaceFeatureExtractor;
import com.visual.face.search.core.utils.JsonUtil;
import com.visual.face.search.server.domain.request.SearchAlgorithm;
import com.visual.face.search.server.domain.storage.StorageDataInfo;
import com.visual.face.search.server.domain.storage.StorageImageInfo;
import com.visual.face.search.server.domain.storage.StorageInfo;
import com.visual.face.search.server.domain.extend.FieldKeyValue;
import com.visual.face.search.server.domain.extend.FieldKeyValues;
import com.visual.face.search.server.domain.request.FaceDataReqVo;
import com.visual.face.search.server.domain.response.FaceDataRepVo;
import com.visual.face.search.server.mapper.CollectMapper;
import com.visual.face.search.server.mapper.FaceDataMapper;
import com.visual.face.search.server.mapper.SampleDataMapper;
import com.visual.face.search.server.model.Collection;
import com.visual.face.search.server.model.ColumnValue;
import com.visual.face.search.server.model.FaceData;
import com.visual.face.search.server.model.ImageData;
import com.visual.face.search.server.service.api.FaceDataService;
import com.visual.face.search.server.service.api.ImageDataService;
import com.visual.face.search.server.service.api.StorageImageService;
import com.visual.face.search.server.service.base.BaseService;
import com.visual.face.search.server.utils.CollectionUtil;
import com.visual.face.search.server.utils.TableUtils;
import com.visual.face.search.server.utils.VTableCache;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.*;

@Service("visualFaceDataService")
public class FaceDataServiceImpl extends BaseService implements FaceDataService {
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
    private ImageDataService imageDataService;
    @Resource
    private FaceFeatureExtractor faceFeatureExtractor;

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public FaceDataRepVo create(FaceDataReqVo face) {
        //人脸ID
        String faceId = this.uuid();
        //查看集合是否存在
        Collection collection = collectMapper.selectByName(face.getNamespace(), face.getCollectionName());
        if(null == collection){
            throw new RuntimeException("collection is not exist");
        }
        if(0 != collection.getStatue()){
            throw new RuntimeException("collection can not use, statue != 0.");
        }
        //查看样本是否存在
        long count = sampleDataMapper.count(collection.getSampleTable(), face.getSampleId());
        if(count <= 0){
            throw new RuntimeException("sample_id is not exist");
        }
        //获取特征向量
        ExtParam extParam = ExtParam.build().setMask(faceMask).setScoreTh(face.getFaceScoreThreshold() / 100).setIouTh(0);
        ImageMat imageMat = null;
        FaceImage faceImage = null;
        try {
            imageMat = ImageMat.fromBase64(face.getImageBase64());
            faceImage = faceFeatureExtractor.extract(imageMat, extParam, new HashMap<>());
        }finally {
            if(null != imageMat){
                imageMat.release();
            }
        }
        if(null == faceImage){
            throw new RuntimeException("FeatureExtractor extract error");
        }
        if(faceImage.faceInfos().size() <= 0){
            throw new RuntimeException("image is not face");
        }
        //获取分数做好的人脸
        FaceInfo faceInfo = faceImage.faceInfos.get(0);
        float[] embeds = faceInfo.embedding.embeds;
        //当前样本的人脸相似度的最小阈值
        if(null != face.getMinConfidenceThresholdWithThisSample() && face.getMinConfidenceThresholdWithThisSample() > 0){
            float minScore = this.searchEngine.searchMinScoreBySampleId(collection.getVectorTable(), face.getSampleId(), embeds, SearchAlgorithm.COSINESIMIL.algorithm());
            float confidence = (float) Math.floor(minScore * 10000)/100;
            if(confidence < face.getMinConfidenceThresholdWithThisSample()){
                throw new RuntimeException("this face confidence is less than minConfidenceThresholdWithThisSample,confidence="+confidence+",threshold="+face.getMinConfidenceThresholdWithThisSample());
            }
        }
        //当前样本与其他样本的人脸相似度的最大阈值
        if(null != face.getMaxConfidenceThresholdWithOtherSample() && face.getMaxConfidenceThresholdWithOtherSample() > 0){
            float minScore = this.searchEngine.searchMaxScoreBySampleId(collection.getVectorTable(), face.getSampleId(), embeds, SearchAlgorithm.COSINESIMIL.algorithm());
            float confidence = (float) Math.floor(minScore * 10000)/100;
            if(confidence > face.getMaxConfidenceThresholdWithOtherSample()){
                throw new RuntimeException("this face confidence is gather than maxConfidenceThresholdWithOtherSample,confidence="+confidence+",threshold="+face.getMaxConfidenceThresholdWithOtherSample());
            }
        }
        //保存图片信息并获取图片存储
        StorageInfo storageInfo = CollectionUtil.getStorageInfo(collection.getSchemaInfo());
        if(storageInfo.isStorageFaceInfo()){
            //将数据保存到指定的数据引擎中
            StorageImageInfo storageImageInfo = new StorageImageInfo(storageInfo.getStorageEngine(), face.getImageBase64(), faceInfo.embedding.image, JsonUtil.toString(faceInfo));
            StorageDataInfo storageDataInfo = StorageImageService.Factory.create(storageImageInfo);
            //插入数据记录
            ImageData imageData = new ImageData();
            imageData.setSampleId(face.getSampleId());
            imageData.setFaceId(faceId);
            imageData.setStorageType(storageInfo.getStorageEngine().name());
            imageData.setImageRawInfo(storageDataInfo.getImageRawInfo());
            imageData.setImageEbdInfo(storageDataInfo.getImageEbdInfo());
            imageData.setImageFaceInfo(storageDataInfo.getImageFaceInfo());
            imageData.setCreateTime(new Date());
            imageData.setModifyTime(new Date());
            boolean flag = imageDataService.insert(collection.getImageTable(), imageData);
            if(!flag){
                throw new RuntimeException("save face image data error");
            }
        }
        //获取数据类型
        Map<String, String> filedTypeMap = TableUtils.getFaceFiledTypeMap(collection.getSchemaInfo());
        //插入数据
        FaceData facePo = new FaceData();
        facePo.setSampleId(face.getSampleId());
        facePo.setFaceId(faceId);
        facePo.setFaceScore(faceInfo.score * 100);
        facePo.setFaceVector(JsonUtil.toString(embeds));
        FieldKeyValues values = face.getFaceData();
        List<ColumnValue> columnValues = new ArrayList<>();
        if(null != values && !values.isEmpty()){
            for(FieldKeyValue value : values){
                if(filedTypeMap.containsKey(value.getKey())){
                    columnValues.add(new ColumnValue(value.getKey(), value.getValue(), filedTypeMap.get(value.getKey())));
                }
            }
        }
        facePo.setColumnValues(columnValues);
        facePo.setCreateTime(new Date());
        facePo.setUpdateTime(new Date());
        int flag = faceDataMapper.create(collection.getFaceTable(), facePo, facePo.getColumnValues());
        if(flag <= 0){
            throw new RuntimeException("create face error");
        }
        //写入数据到人脸向量库
        boolean flag1 = searchEngine.insertVector(collection.getVectorTable(), face.getSampleId(), faceId, embeds);
        if(!flag1){
            throw new RuntimeException("create face vector error");
        }
        //将队列写入到队列中
        VTableCache.put(collection.getVectorTable());
        //构造返回
        FaceDataRepVo vo = FaceDataRepVo.build(face.getNamespace(), face.getCollectionName(), face.getSampleId(), faceId);
        vo.setFaceScore(faceInfo.score * 100);
        vo.setFaceData(face.getFaceData());
        return vo;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public Boolean delete(String namespace, String collectionName, String sampleId, String faceId) {
        //查看集合是否存在
        Collection collection = collectMapper.selectByName(namespace, collectionName);
        if(null == collection){
            throw new RuntimeException("collection is not exist");
        }
        if(0 != collection.getStatue()){
            throw new RuntimeException("collection can not use, statue != 0.");
        }
        //查看人脸是否存在
        Long keyId = faceDataMapper.getIdByFaceId(collection.getFaceTable(), sampleId, faceId);
        if(null == keyId || keyId <= 0){
            throw new RuntimeException("face id is not exist");
        }
        //删除向量
        boolean delete = searchEngine.deleteVectorByKey(collection.getVectorTable(), faceId);
        if(!delete){
            throw new RuntimeException("delete face vector error");
        }
        //将队列写入到队列中
        VTableCache.put(collection.getVectorTable());
        //删除数据
        int flag = faceDataMapper.deleteByFaceId(collection.getFaceTable(), sampleId, faceId);
        return flag > 0;
    }
}
