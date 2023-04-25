package com.visual.face.search.server.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.*;
import com.visual.face.search.server.model.ColumnValue;
import com.visual.face.search.server.model.SampleData;

@Mapper
public interface SampleDataMapper {

    @Insert({
        "<script>",
            "insert into ${table} (",
                "sample_id,",
                "<foreach item=\"item\" index=\"index\" collection=\"columnValues\" open=\"\" separator=\",\" close=\",\">",
                    "${item.name}",
                "</foreach>",
                "create_time,",
                "modify_time",
            ")",
            "values (",
                "#{sample.sampleId,jdbcType=VARCHAR},",
                "<foreach item=\"item\" index=\"index\" collection=\"columnValues\" open=\"\" separator=\",\" close=\",\">",
                    "<choose>",
                        "<when test=\"item.type=='string'\">",
                            "#{item.value,jdbcType=VARCHAR}",
                        "</when>",
                        "<when test=\"item.type=='bool'\">",
                            "#{item.value,jdbcType=CHAR}",
                        "</when>",
                        "<when test=\"item.type=='int'\">",
                            "#{item.value,jdbcType=INTEGER}",
                        "</when>",
                        "<when test=\"item.type=='float'\">",
                            "#{item.value,jdbcType=FLOAT}",
                        "</when>",
                        "<when test=\"item.type=='double'\">",
                            "#{item.value,jdbcType=DOUBLE}",
                        "</when>",
                    "</choose>",
                "</foreach>",
                "#{sample.createTime,jdbcType=TIMESTAMP}, ",
                "#{sample.updateTime,jdbcType=TIMESTAMP}",
            ")",
        "</script>"
    })
    @SelectKey(statement="SELECT LAST_INSERT_ID()", keyProperty="id", before=false, resultType=Long.class)
    int create(@Param("table") String table, @Param("sample") SampleData sample, @Param("columnValues") List<ColumnValue> columnValues);

    @Select({"select count(1) from ${table} where sample_id = '${sampleId}'"})
    long count(@Param("table") String table, @Param("sampleId") String sampleId);

    @Update({
        "<script>",
            "update ${table}",
            "set",
                "<foreach item=\"item\" index=\"index\" collection=\"columnValues\" open=\"\" separator=\",\" close=\",\">",
                    "<choose>",
                        "<when test=\"item.type=='string'\">",
                            "${item.name} = #{item.value,jdbcType=VARCHAR}",
                        "</when>",
                        "<when test=\"item.type=='bool'\">",
                            "${item.name} = #{item.value,jdbcType=CHAR}",
                        "</when>",
                        "<when test=\"item.type=='int'\">",
                            "${item.name} = #{item.value,jdbcType=INTEGER}",
                        "</when>",
                        "<when test=\"item.type=='float'\">",
                            "${item.name} = #{item.value,jdbcType=FLOAT}",
                        "</when>",
                        "<when test=\"item.type=='double'\">",
                            "${item.name} = #{item.value,jdbcType=DOUBLE}",
                        "</when>",
                    "</choose>",
                "</foreach>",
            "sample_id = #{sampleId}",
            "where sample_id = #{sampleId,jdbcType=BIGINT}",
        "</script>"
    })
    int update(@Param("table") String table, @Param("sampleId") String sampleId, @Param("columnValues") List<ColumnValue> columnValues);

    @Delete({"delete from ${table} where sample_id = #{sampleId,jdbcType=VARCHAR}",})
    int delete(@Param("table") String table, @Param("sampleId") String sampleId);

    @Select({"select * from ${table} where sample_id = #{sampleId,jdbcType=VARCHAR}"})
    Map<String, Object> getBySampleId(@Param("table") String table, @Param("sampleId") String sampleId);

    @Select({"select * from ${table} order by id ${order} limit ${offset}, ${limit}"})
    List<Map<String, Object>> getBySampleList(@Param("table") String table, @Param("offset") Integer offset, @Param("limit") Integer limit, @Param("order") String order);

    @Select({
            "<script>",
                "select * from ${table} where sample_id in ",
                "<foreach collection=\"sampleIds\" item=\"item\" index=\"index\" open=\"(\" separator=\",\" close=\")\">",
                    "#{item,jdbcType=VARCHAR}",
                "</foreach>",
            "</script>"
    })
    List<Map<String, Object>> getBySampleIds(@Param("table") String table, @Param("sampleIds") List<String> sampleIds);
}
