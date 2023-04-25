package com.visual.face.search.server.mapper;

import com.visual.face.search.server.model.Collection;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.type.JdbcType;

import java.util.List;

@Mapper
public interface CollectMapper {

    @Insert({
            "insert into visual_collection (",
                "uuid,",
                "namespace,",
                "`collection`,",
                "`describe`,",
                "statue,",
                "sample_table,",
                "face_table,",
                "image_table,",
                "vector_table,",
                "schema_info",
             ")",
            "values (",
                "#{uuid,jdbcType=VARCHAR}, #{namespace,jdbcType=VARCHAR}, ",
                "#{collection,jdbcType=VARCHAR}, #{describe,jdbcType=VARCHAR}, ",
                "#{statue,jdbcType=INTEGER}, #{sampleTable,jdbcType=VARCHAR}, ",
                "#{faceTable,jdbcType=VARCHAR},  #{imageTable,jdbcType=VARCHAR},",
                "#{vectorTable,jdbcType=VARCHAR},#{schemaInfo,jdbcType=VARCHAR}",
            ")"
    })
    @SelectKey(statement="SELECT LAST_INSERT_ID()", keyProperty="id", before=false, resultType=Long.class)
    int insert(Collection record);

    @Delete({
        "delete from visual_collection where 1=1 ",
        "and id = #{id,jdbcType=BIGINT}"
    })
    int deleteByKey(@Param("id") Long id);

    @Delete({
        "delete from visual_collection where 1=1 ",
        "and namespace = #{namespace,jdbcType=VARCHAR} ",
        "and collection = #{collection,jdbcType=VARCHAR}"
    })
    int deleteByName(@Param("namespace") String namespace, @Param("collection") String collection);

    @Select({
            "select",
            "id, uuid, namespace, `collection`, `describe`, statue, sample_table, face_table, image_table, ",
            "vector_table, schema_info, create_time, update_time, deleted",
            "from visual_collection",
            "where 1=1 ",
            "and namespace = #{namespace,jdbcType=VARCHAR} ",
            "and collection = #{collection,jdbcType=VARCHAR} ",
            "limit 1",
    })
    @Results({
            @Result(column="id", property="id", jdbcType= JdbcType.BIGINT, id=true),
            @Result(column="uuid", property="uuid", jdbcType=JdbcType.VARCHAR),
            @Result(column="namespace", property="namespace", jdbcType=JdbcType.VARCHAR),
            @Result(column="collection", property="collection", jdbcType=JdbcType.VARCHAR),
            @Result(column="describe", property="describe", jdbcType=JdbcType.VARCHAR),
            @Result(column="statue", property="statue", jdbcType=JdbcType.INTEGER),
            @Result(column="sample_table", property="sampleTable", jdbcType=JdbcType.VARCHAR),
            @Result(column="face_table", property="faceTable", jdbcType=JdbcType.VARCHAR),
            @Result(column="image_table", property="imageTable", jdbcType=JdbcType.VARCHAR),
            @Result(column="vector_table", property="vectorTable", jdbcType=JdbcType.VARCHAR),
            @Result(column="schema_info", property="schemaInfo", jdbcType=JdbcType.VARCHAR),
            @Result(column="create_time", property="createTime", jdbcType=JdbcType.TIMESTAMP),
            @Result(column="update_time", property="updateTime", jdbcType=JdbcType.TIMESTAMP),
            @Result(column="deleted", property="deleted", jdbcType=JdbcType.CHAR)
    })
    Collection selectByName(@Param("namespace") String namespace, @Param("collection") String collection);

    @Select({
            "select",
            "id, uuid, namespace, `collection`, `describe`, statue, sample_table, face_table, image_table, ",
            "vector_table, schema_info, create_time, update_time, deleted",
            "from visual_collection",
            "where 1=1 ",
            "and namespace = #{namespace,jdbcType=VARCHAR}"
    })
    @Results({
            @Result(column="id", property="id", jdbcType= JdbcType.BIGINT, id=true),
            @Result(column="uuid", property="uuid", jdbcType=JdbcType.VARCHAR),
            @Result(column="namespace", property="namespace", jdbcType=JdbcType.VARCHAR),
            @Result(column="collection", property="collection", jdbcType=JdbcType.VARCHAR),
            @Result(column="describe", property="describe", jdbcType=JdbcType.VARCHAR),
            @Result(column="statue", property="statue", jdbcType=JdbcType.INTEGER),
            @Result(column="sample_table", property="sampleTable", jdbcType=JdbcType.VARCHAR),
            @Result(column="face_table", property="faceTable", jdbcType=JdbcType.VARCHAR),
            @Result(column="image_table", property="imageTable", jdbcType=JdbcType.VARCHAR),
            @Result(column="vector_table", property="vectorTable", jdbcType=JdbcType.VARCHAR),
            @Result(column="schema_info", property="schemaInfo", jdbcType=JdbcType.VARCHAR),
            @Result(column="create_time", property="createTime", jdbcType=JdbcType.TIMESTAMP),
            @Result(column="update_time", property="updateTime", jdbcType=JdbcType.TIMESTAMP),
            @Result(column="deleted", property="deleted", jdbcType=JdbcType.CHAR)
    })
    List<Collection> selectByNamespace(@Param("namespace") String namespace);
}
