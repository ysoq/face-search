package com.visual.face.search.server.mapper;

import com.visual.face.search.server.model.ColumnValue;
import com.visual.face.search.server.model.FaceData;
import com.visual.face.search.server.model.SampleData;
import org.apache.ibatis.annotations.*;

import java.util.List;
import java.util.Map;

@Mapper
public interface FaceDataMapper {

    @Insert({
            "<script>",
            "insert into ${table} (",
                "sample_id,",
                "face_id,",
                "face_score,",
                "face_vector,",
                "<foreach item=\"item\" index=\"index\" collection=\"columnValues\" open=\"\" separator=\",\" close=\",\">",
                    "${item.name}",
                "</foreach>",
                "create_time,",
                "modify_time",
            ")",
            "values (",
                "#{face.sampleId,jdbcType=VARCHAR},",
                "#{face.faceId,jdbcType=VARCHAR},",
                "#{face.faceScore,jdbcType=VARCHAR},",
                "#{face.faceVector,jdbcType=VARCHAR},",
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
                "#{face.createTime,jdbcType=TIMESTAMP}, ",
                "#{face.updateTime,jdbcType=TIMESTAMP}",
            ")",
            "</script>"
    })

    @SelectKey(statement="SELECT LAST_INSERT_ID()", keyProperty="face.id", before=false, resultType=Long.class)
    int create(@Param("table") String table, @Param("face") FaceData face, @Param("columnValues") List<ColumnValue> columnValues);

    @Delete({"delete from ${table} where 1=1 and id = #{id,jdbcType=BIGINT}"})
    int deleteById(@Param("table") String table, @Param("id") Long id);

    @Delete({"delete from ${table} where sample_id = #{sampleId,jdbcType=VARCHAR}"})
    int deleteBySampleId(@Param("table") String table, @Param("sampleId") String sampleId);

    @Delete({"delete from ${table} where sample_id = #{sampleId,jdbcType=VARCHAR} and face_id=#{faceId,jdbcType=VARCHAR}"})
    int deleteByFaceId(@Param("table") String table, @Param("sampleId") String sampleId, @Param("faceId") String faceId);

    @Select({"select count(1) from ${table} where sample_id = '${sampleId}'"})
    long countBySampleId(@Param("table") String table, @Param("sampleId") String sampleId);

    @Select({"select count(1) from ${table} where sample_id = '${sampleId}' and face_id=#{faceId,jdbcType=VARCHAR}"})
    long count(@Param("table") String table, @Param("sampleId") String sampleId, @Param("faceId") String faceId);

    @Select({"select id from ${table} where sample_id = '${sampleId}' and face_id=#{faceId,jdbcType=VARCHAR}"})
    Long getIdByFaceId(@Param("table") String table, @Param("sampleId") String sampleId, @Param("faceId") String faceId);

    @Select({"select face_id from ${table} where sample_id=#{sampleId,jdbcType=VARCHAR}"})
    List<String> getFaceIdBySampleId(@Param("table") String table, @Param("sampleId") String sampleId);

    @Select({"select * from ${table} where sample_id = #{sampleId,jdbcType=VARCHAR}"})
    List<Map<String, Object>> getBySampleId(@Param("table") String table, @Param("sampleId") String sampleId);

    @Select({
        "<script>",
            "select * from ${table} where sample_id in ",
            "<foreach collection=\"sampleIds\" item=\"item\" index=\"index\" open=\"(\" separator=\",\" close=\")\">",
                "#{item,jdbcType=VARCHAR}",
            "</foreach>",
        "</script>"
    })
    List<Map<String, Object>> getBySampleIds(@Param("table") String table, @Param("sampleIds") List<String> sampleIds);

    @Select({"select * from ${table} where sample_id = #{sampleId,jdbcType=VARCHAR} and face_id=#{faceId,jdbcType=VARCHAR}"})
    Map<String, Object> getByFaceId(@Param("table") String table, @Param("sampleId") String sampleId, @Param("faceId") String faceId);

    @Select({
            "<script>",
                "select",
                "<foreach item=\"item\" index=\"index\" collection=\"columns\" open=\"\" separator=\",\" close=\"\">",
                    "${item}",
                "</foreach>",
                "from ${table} where face_id in ",
                "<foreach collection=\"faceIds\" item=\"item\" index=\"index\" open=\"(\" separator=\",\" close=\")\">",
                    "#{item,jdbcType=VARCHAR}",
                "</foreach>",
            "</script>"
    })
    List<Map<String, Object>> getByFaceIds(@Param("table") String table, @Param("columns") List<String> columns, @Param("faceIds") List<String> faceIds);

    @Select({
            "<script>",
                "select * from ${table} where id in ",
                "<foreach collection=\"keyIds\" item=\"item\" index=\"index\" open=\"(\" separator=\",\" close=\")\">",
                    "#{item,jdbcType=BIGINT}",
                "</foreach>",
            "</script>"
    })
    List<Map<String, Object>> getByPrimaryIds(@Param("table") String table, @Param("keyIds") List<Long> keyIds);

}
