package com.visual.face.search.server.service.impl;

import com.visual.face.search.core.common.enums.CollectionStatue;
import com.visual.face.search.core.utils.JsonUtil;
import com.visual.face.search.core.utils.ThreadUtil;
import com.visual.face.search.server.domain.request.CollectReqVo;
import com.visual.face.search.server.domain.response.CollectRepVo;
import com.visual.face.search.engine.api.SearchEngine;
import com.visual.face.search.engine.conf.Constant;
import com.visual.face.search.engine.model.MapParam;
import com.visual.face.search.server.mapper.CollectMapper;
import com.visual.face.search.server.model.Collection;
import com.visual.face.search.server.service.api.CollectService;
import com.visual.face.search.server.service.api.OperateTableService;
import com.visual.face.search.server.service.base.BaseService;
import com.visual.face.search.server.utils.TableUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;


@Service("visualCollectService")
public class CollectServiceImpl extends BaseService implements CollectService {

    @Resource
    private CollectMapper collectMapper;
    @Resource
    private SearchEngine searchEngine;
    @Resource
    private OperateTableService operateTableService;

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public Boolean createCollection(CollectReqVo collect){
        //判断当前集合是否存在
        Collection selectCollection = collectMapper.selectByName(collect.getNamespace(), collect.getCollectionName());
        if(null != selectCollection){
            throw new RuntimeException("collection is exist");
        }
        //获取UUID
        String uuid = this.uuid();
        //最终的数据库名称
        String namespace = collect.getNamespace();
        String collection = collect.getCollectionName();
        String randomStr = RandomStringUtils.randomAlphanumeric(4).toLowerCase();
        String prefixName = StringUtils.join(TABLE_PREFIX, CHAR_UNDERLINE, namespace, CHAR_UNDERLINE, collection, CHAR_UNDERLINE, randomStr);
        //表名称
        String sampleTableName = StringUtils.join(prefixName, CHAR_UNDERLINE, "sample");
        String faceTableName   = StringUtils.join(prefixName, CHAR_UNDERLINE, "face");
        String imageTableName  = StringUtils.join(prefixName, CHAR_UNDERLINE, "image");
        String vectorTableName = StringUtils.join(prefixName, CHAR_UNDERLINE, "vector");
        //判断表是否存在
        if(operateTableService.exist(sampleTableName)){
            throw new RuntimeException("sample table is exist");
        }
        if(operateTableService.exist(faceTableName)){
            throw new RuntimeException("face table is exist");
        }
        if(operateTableService.exist(imageTableName)){
            throw new RuntimeException("image table is exist");
        }
        if(searchEngine.exist(vectorTableName)){
            throw new RuntimeException("vector table is exist");
        }
        //创建服务需要的数据表
        try {
            //新建样本表
            boolean createSampleFlag = operateTableService.createSampleTable(sampleTableName, TableUtils.convert(collect.getSampleColumns()));
            if(!createSampleFlag){
                throw new RuntimeException("create sample table error");
            }
            //新建人脸表
            boolean createFaceFlag = operateTableService.createFaceTable(faceTableName, TableUtils.convert(collect.getFaceColumns()));
            if(!createFaceFlag){
                throw new RuntimeException("create face table error");
            }
            //创建图像数据表
            boolean createImageFlag = operateTableService.createImageTable(imageTableName);
            if(!createImageFlag){
                throw new RuntimeException("create image table error");
            }
            //创建人脸向量库
            MapParam param = MapParam.build()
                    .put(Constant.IndexShardsNum, collect.getShardsNum())
                    .put(Constant.IndexReplicasNum, collect.getReplicasNum());
            boolean createVectorFlag = searchEngine.createCollection(vectorTableName, param);
            if(!createVectorFlag){
                throw new RuntimeException("create vector table error");
            }
        }catch (Exception e){
            //删除创建的表
            operateTableService.dropTable(sampleTableName);
            operateTableService.dropTable(faceTableName);
            operateTableService.dropTable(imageTableName);
            searchEngine.dropCollection(vectorTableName);
            throw new RuntimeException(e);
        }
        //添加回滚事务
        Object savePoint = TransactionAspectSupport.currentTransactionStatus().createSavepoint();
        boolean hasError = false;
        try {
            //添加平台管理的集合信息
            Collection insertCollection = new Collection();
            insertCollection.setUuid(uuid);
            insertCollection.setNamespace(collect.getNamespace());
            insertCollection.setCollection(collect.getCollectionName());
            insertCollection.setDescribe(collect.getCollectionComment());
            insertCollection.setStatue(CollectionStatue.NORMAL.getValue());
            insertCollection.setSampleTable(sampleTableName);
            insertCollection.setFaceTable(faceTableName);
            insertCollection.setImageTable(imageTableName);
            insertCollection.setVectorTable(vectorTableName);
            insertCollection.setSchemaInfo(JsonUtil.toSimpleString(collect));
            int flag = collectMapper.insert(insertCollection);
            return flag > 0;
        }catch (Exception e){
            //事务回滚
            hasError = true;
            TransactionAspectSupport.currentTransactionStatus().rollbackToSavepoint(savePoint);
            throw new RuntimeException(e);
        }finally {
            //删除创建的表
            if(hasError){
                operateTableService.dropTable(sampleTableName);
                operateTableService.dropTable(faceTableName);
                operateTableService.dropTable(imageTableName);
                searchEngine.dropCollection(vectorTableName);
            }
        }
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public Boolean delete(String namespace, String collectionName) {
        //判断数据是否存在
        Collection collection = collectMapper.selectByName(namespace, collectionName);
        if(null == collection){
            throw new RuntimeException("collection is not exist");
        }
        //删除数据
        collectMapper.deleteByKey(collection.getId());
        //异步删除数据表
        ThreadUtil.run(() -> {
            operateTableService.dropTable(collection.getSampleTable());
            operateTableService.dropTable(collection.getFaceTable());
            if(StringUtils.isNotEmpty(collection.getImageTable())){
                operateTableService.dropTable(collection.getImageTable());
            }
            searchEngine.dropCollection(collection.getVectorTable());
        });
        //返回
        return true;
    }

    @Override
    public CollectRepVo get(String namespace, String collectionName) {
        Collection collection = collectMapper.selectByName(namespace, collectionName);
        if(null == collection){
            throw new RuntimeException("collection is not exist");
        }
        return JsonUtil.toEntity(collection.getSchemaInfo(), CollectRepVo.class);
    }

    @Override
    public List<CollectRepVo> list(String namespace) {
        List<Collection> collections = collectMapper.selectByNamespace(namespace);
        List<CollectRepVo> vos = new ArrayList<>();
        if(null != collections){
            for(Collection collection : collections){
                vos.add(JsonUtil.toEntity(collection.getSchemaInfo(), CollectRepVo.class));
            }
        }
        return vos;
    }

}
