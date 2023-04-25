package com.visual.face.search.server.service.impl;

import com.visual.face.search.engine.conf.Constant;
import com.visual.face.search.engine.api.SearchEngine;
import com.visual.face.search.server.domain.extend.FieldKeyValue;
import com.visual.face.search.server.domain.extend.FieldKeyValues;
import com.visual.face.search.server.domain.extend.SimpleFaceVo;
import com.visual.face.search.server.domain.request.SampleDataReqVo;
import com.visual.face.search.server.domain.response.SampleDataRepVo;
import com.visual.face.search.server.mapper.CollectMapper;
import com.visual.face.search.server.mapper.FaceDataMapper;
import com.visual.face.search.server.mapper.SampleDataMapper;
import com.visual.face.search.server.model.Collection;
import com.visual.face.search.server.model.ColumnValue;
import com.visual.face.search.server.model.SampleData;
import com.visual.face.search.server.service.api.SampleDataService;
import com.visual.face.search.server.service.base.BaseService;
import com.visual.face.search.server.utils.TableUtils;
import com.visual.face.search.server.utils.ValueUtil;
import org.apache.commons.collections4.MapUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import javax.annotation.Resource;
import java.util.*;
import java.util.stream.Collectors;

@Service("visualSampleDataService")
public class SampleDataServiceImpl extends BaseService implements SampleDataService {

    @Resource
    private SearchEngine searchEngine;
    @Resource
    private CollectMapper collectMapper;
    @Resource
    private FaceDataMapper faceDataMapper;
    @Resource
    private SampleDataMapper sampleDataMapper;

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public Boolean create(SampleDataReqVo sample) {
        //查看集合是否存在
        Collection collection = collectMapper.selectByName(sample.getNamespace(), sample.getCollectionName());
        if(null == collection){
            throw new RuntimeException("collection is not exist");
        }
        if(0 != collection.getStatue()){
            throw new RuntimeException("collection can not use, statue != 0.");
        }
        //查看样本是否存在
        long count = sampleDataMapper.count(collection.getSampleTable(), sample.getSampleId());
        if(count > 0){
            throw new RuntimeException("sample_id is exist");
        }
        //获取数据类型
        Map<String, String> filedTypeMap = TableUtils.getSampleFiledTypeMap(collection.getSchemaInfo());
        //插入数据
        SampleData samplePo = new SampleData();
        samplePo.setSampleId(sample.getSampleId());
        FieldKeyValues values = sample.getSampleData();
        List<ColumnValue> columnValues = new ArrayList<>();
        if(null != values && !values.isEmpty()){
            for(FieldKeyValue value : values){
                if(filedTypeMap.containsKey(value.getKey())){
                    columnValues.add(new ColumnValue(value.getKey(), value.getValue(), filedTypeMap.get(value.getKey())));
                }
            }
        }
        samplePo.setColumnValues(columnValues);
        samplePo.setCreateTime(new Date());
        samplePo.setUpdateTime(new Date());
        int flag = sampleDataMapper.create(collection.getSampleTable(), samplePo, samplePo.getColumnValues());
        return flag > 0;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public Boolean update(SampleDataReqVo sample) {
        //获取待更新的数据
        FieldKeyValues sampleData = sample.getSampleData();
        if(null == sampleData || sampleData.isEmpty()){
            throw new RuntimeException("sample data is not void");
        }
        //查看集合是否存在
        Collection collection = collectMapper.selectByName(sample.getNamespace(), sample.getCollectionName());
        if(null == collection){
            throw new RuntimeException("collection is not exist");
        }
        if(0 != collection.getStatue()){
            throw new RuntimeException("collection can not use, statue != 0.");
        }
        //查看样本是否存在
        long count = sampleDataMapper.count(collection.getSampleTable(), sample.getSampleId());
        if(count <= 0){
            throw new RuntimeException("sample_id is not exist");
        }
        //获取数据类型
        Map<String, String> filedTypeMap = TableUtils.getSampleFiledTypeMap(collection.getSchemaInfo());
        List<ColumnValue> columnValues = new ArrayList<>();
        if(!sampleData.isEmpty()){
            for(FieldKeyValue value : sampleData){
                if(filedTypeMap.containsKey(value.getKey())){
                    columnValues.add(new ColumnValue(value.getKey(), value.getValue(), filedTypeMap.get(value.getKey())));
                }
            }
        }
        //更新数据
        int flag = sampleDataMapper.update(collection.getSampleTable(), sample.getSampleId(), columnValues);
        return flag > 0;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public Boolean delete(String namespace, String collectionName, String sampleId) {
        //查看集合是否存在
        Collection collection = collectMapper.selectByName(namespace, collectionName);
        if(null == collection){
            throw new RuntimeException("collection is not exist");
        }
        if(0 != collection.getStatue()){
            throw new RuntimeException("collection can not use, statue != 0.");
        }
        //查看样本是否存在
        long count = sampleDataMapper.count(collection.getSampleTable(), sampleId);
        if(count <= 0){
            throw new RuntimeException("sample_id is not exist");
        }
        //删除向量数据
        List<String> faceIds = faceDataMapper.getFaceIdBySampleId(collection.getFaceTable(), sampleId);
        searchEngine.deleteVectorByKey(collection.getVectorTable(), faceIds);
        //删除人脸数据
        faceDataMapper.deleteBySampleId(collection.getFaceTable(), sampleId);
        //删除样本数据
        int flag = sampleDataMapper.delete(collection.getSampleTable(), sampleId);
        return flag > 0;
    }

    @Override
    public SampleDataRepVo getSample(String namespace, String collectionName, String sampleId) {
        //查看集合是否存在
        Collection collection = collectMapper.selectByName(namespace, collectionName);
        if(null == collection){
            throw new RuntimeException("collection is not exist");
        }
        if(0 != collection.getStatue()){
            throw new RuntimeException("collection can not use, statue != 0.");
        }
        //查询数据
        Map<String, Object> map = sampleDataMapper.getBySampleId(collection.getSampleTable(), sampleId);
        if(null == map || map.isEmpty()){
            return null;
        }
        //查询人脸数据
        List<Map<String, Object>> facesMap = faceDataMapper.getBySampleId(collection.getFaceTable(), sampleId);
        //组织数据
        SampleDataRepVo vo = new SampleDataRepVo();
        vo.setNamespace(namespace);
        vo.setCollectionName(collectionName);
        vo.setSampleId(sampleId);
        vo.setSampleData(ValueUtil.getFieldKeyValues(map, ValueUtil.getSampleColumns(collection)));
        List<SimpleFaceVo> faces = new ArrayList<>();
        for(Map<String, Object> faceMap : facesMap){
            String faceId = MapUtils.getString(faceMap, Constant.ColumnNameFaceId);
            Float faceScore = MapUtils.getFloat(faceMap, Constant.ColumnNameFaceScore);
            SimpleFaceVo simpleFace = SimpleFaceVo.build(faceId).setFaceScore(faceScore);
            simpleFace.setFaceData(ValueUtil.getFieldKeyValues(faceMap, ValueUtil.getFaceColumns(collection)));
            faces.add(simpleFace);
        }
        vo.setFaces(faces);
        return vo;
    }

    @Override
    public List<SampleDataRepVo> list(String namespace, String collectionName, Integer offset, Integer limit, String order) {
        //查看集合是否存在
        Collection collection = collectMapper.selectByName(namespace, collectionName);
        if(null == collection){
            throw new RuntimeException("collection is not exist");
        }
        if(0 != collection.getStatue()){
            throw new RuntimeException("collection can not use, statue != 0.");
        }
        List<Map<String, Object>> samples = sampleDataMapper.getBySampleList(collection.getSampleTable(), offset, limit, order);
        List<String> sampleIds = samples.stream().map(item -> MapUtils.getString(item, Constant.ColumnNameSampleId)).collect(Collectors.toList());
        List<Map<String, Object>> faces = faceDataMapper.getBySampleIds(collection.getFaceTable(), sampleIds);
        Map<String, List<Map<String, Object>>> faceMapping = new HashMap<>();
        for(Map<String, Object> face : faces){
            String sampleId = MapUtils.getString(face, Constant.ColumnNameSampleId);
            if(!faceMapping.containsKey(sampleId)){
                faceMapping.put(sampleId, new ArrayList<>());
            }
            faceMapping.get(sampleId).add(face);
        }
        //组织数据
        List<SampleDataRepVo> result = new ArrayList<>();
        for(Map<String, Object> sample : samples){
            String sampleId = MapUtils.getString(sample, Constant.ColumnNameSampleId);
            SampleDataRepVo vo = new SampleDataRepVo();
            vo.setNamespace(namespace);
            vo.setCollectionName(collectionName);
            vo.setSampleId(sampleId);
            vo.setSampleData(ValueUtil.getFieldKeyValues(sample, ValueUtil.getSampleColumns(collection)));
            List<SimpleFaceVo> facesVo = new ArrayList<>();
            if(faceMapping.containsKey(sampleId)){
                List<Map<String, Object>> facesMap = faceMapping.get(sampleId);
                for(Map<String, Object> faceMap : facesMap){
                    String faceId = MapUtils.getString(faceMap, Constant.ColumnNameFaceId);
                    Float faceScore = MapUtils.getFloat(faceMap, Constant.ColumnNameFaceScore);
                    SimpleFaceVo simpleFace = SimpleFaceVo.build(faceId).setFaceScore(faceScore);
                    simpleFace.setFaceData(ValueUtil.getFieldKeyValues(faceMap, ValueUtil.getFaceColumns(collection)));
                    facesVo.add(simpleFace);
                }
            }
            vo.setFaces(facesVo);
            result.add(vo);
        }
        return result;
    }

}
