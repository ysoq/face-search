package com.visual.face.search.server.utils;

import com.visual.face.search.core.utils.JsonUtil;
import com.visual.face.search.server.domain.extend.FiledColumn;
import com.visual.face.search.server.domain.response.CollectRepVo;
import com.visual.face.search.server.model.TableColumn;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TableUtils {

    public static List<TableColumn> convert(List<FiledColumn> columns){
        if(null == columns){
            return new ArrayList<>();
        }
        List<TableColumn> res = new ArrayList<>();
        for(FiledColumn column : columns){
            String comment = column.getComment();
            if(null == comment){
                comment = column.getName();
            }
            res.add(new TableColumn(column.getName(), column.getDataType().name().toLowerCase(), comment));
        }
        return res;
    }

    public static Map<String, String> getFaceFiledTypeMap(String schemaInfo){
        CollectRepVo vo = JsonUtil.toEntity(schemaInfo, CollectRepVo.class);
        return getFiledTypeMap(vo.getFaceColumns());
    }

    public static Map<String, String> getSampleFiledTypeMap(String schemaInfo){
        CollectRepVo vo = JsonUtil.toEntity(schemaInfo, CollectRepVo.class);
        return getFiledTypeMap(vo.getSampleColumns());
    }

    public static Map<String, String> getFiledTypeMap(List<FiledColumn> columns){
        Map<String, String> map = new HashMap<String, String>();
        if(null != columns && !columns.isEmpty()){
            for(FiledColumn column : columns){
                map.put(column.getName(), column.getDataType().name().toLowerCase());
            }
        }
        return map;
    }


}
