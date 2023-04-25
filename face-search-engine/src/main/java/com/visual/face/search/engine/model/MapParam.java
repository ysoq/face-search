package com.visual.face.search.engine.model;

import com.visual.face.search.engine.conf.Constant;
import org.apache.commons.collections4.MapUtils;

import java.util.concurrent.ConcurrentHashMap;

public class MapParam extends ConcurrentHashMap<String, Object> {

    public static MapParam build(){
        return new MapParam();
    }

    public MapParam put(String key, Object value){
        if(null != key && null != value){
            super.put(key, value);
        }
        return this;
    }

    public String getString(String key){
        return getString(key, null);
    }

    public String getString(String key, String def){
        return MapUtils.getString(this, key, def);
    }

    public Integer getInteger(String key){
        return getInteger(key, null);
    }

    public Integer getInteger(String key, Integer def){
        return MapUtils.getInteger(this, key, def);
    }

    public Long getLong(String key){
        return getLong(key, null);
    }

    public Long getLong(String key, Long def){
        return MapUtils.getLong(this, key, def);
    }

    public Float getFloat(String key){
        return getFloat(key, null);
    }

    public Float getFloat(String key, Float def){
        return MapUtils.getFloat(this, key, def);
    }

    public Double getDouble(String key){
        return getDouble(key, null);
    }

    public Double getDouble(String key, Double def){
        return MapUtils.getDouble(this, key, def);
    }

    /******************************************************************************************************************/
    public Long getIndexReplicasNum(){
        Long maxDocsPerSegment = this.getLong(Constant.IndexReplicasNum, 1L);
        maxDocsPerSegment = (null == maxDocsPerSegment || maxDocsPerSegment < 0) ? 0  : maxDocsPerSegment;
        return maxDocsPerSegment;
    }

    public Integer getIndexShardsNum(){
        Integer shardsNum = this.getInteger(Constant.IndexShardsNum, 4);
        shardsNum = (null == shardsNum || shardsNum <= 0) ? 4  : shardsNum;
        return shardsNum;
    }

}
