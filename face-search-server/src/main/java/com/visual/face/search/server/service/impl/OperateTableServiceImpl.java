package com.visual.face.search.server.service.impl;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.Resource;
import com.visual.face.search.server.model.TableColumn;
import com.visual.face.search.server.mapper.OperateTableMapper;
import com.visual.face.search.server.service.api.OperateTableService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service("visualOperateTableServiceImpl")
public class OperateTableServiceImpl implements OperateTableService {

    @Resource
    public OperateTableMapper operateTableMapper;


    @Override
    public boolean exist(String table) {
        String info = operateTableMapper.showTable(table);
        return null != info && !info.isEmpty();
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public boolean dropTable(String table) {
        return operateTableMapper.dropTable(table) > 0;
    }

    @Override
    public boolean createImageTable(String table) {
        return operateTableMapper.createImageTable(table) >= 0;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public boolean createSampleTable(String table, List<TableColumn> columns) {
        if(null == columns){
            columns = new ArrayList<>();
        }
        return operateTableMapper.createSampleTable(table, columns) >= 0;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public boolean createFaceTable(String table, List<TableColumn> columns) {
        if(null == columns){
            columns = new ArrayList<>();
        }
        return operateTableMapper.createFaceTable(table, columns) >= 0;
    }

}
