package com.visual.face.search.server.mapper;

import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import com.visual.face.search.server.model.TableColumn;


@Mapper
public interface OperateTableMapper {

    @Select({"SHOW TABLES LIKE '${table}'"})
    String showTable(@Param("table") String table);

    @Update({ "DROP TABLE IF EXISTS ${table}"})
    int dropTable(@Param("table") String table);

    @Update({
        "<script>",
            "CREATE TABLE ${table} (",
                "`id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键、自增、无意',",
                "`sample_id` varchar(64) NOT NULL COMMENT '样本ID',",
                "<foreach item=\"item\" index=\"index\" collection=\"columns\" open=\"\" separator=\",\" close=\",\">",
                    "<choose>",
                        "<when test=\"item.type=='string'\">",
                            "`${item.name}` varchar(512) DEFAULT NULL COMMENT '${item.desc}'",
                        "</when>",
                        "<when test=\"item.type=='bool'\">",
                            "`${item.name}` tinyint(1) DEFAULT NULL COMMENT '${item.desc}'",
                        "</when>",
                        "<when test=\"item.type=='int'\">",
                            "`${item.name}` int(32) DEFAULT NULL COMMENT '${item.desc}'",
                        "</when>",
                        "<when test=\"item.type=='float'\">",
                            "`${item.name}`float DEFAULT NULL COMMENT '${item.desc}'",
                        "</when>",
                        "<when test=\"item.type=='double'\">",
                            "`${item.name}` double DEFAULT NULL COMMENT '${item.desc}'",
                        "</when>",
                    "</choose>",
                "</foreach>",
                "`create_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',",
                "`modify_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',",
                "PRIMARY KEY (`id`),",
                "KEY `id` (`id`),",
                "KEY `sample_id` (`sample_id`),",
                "KEY `create_time` (`create_time`)",
            ")",
            "ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8",
        "</script>"
    })
    int createSampleTable(@Param("table") String table, @Param("columns") List<TableColumn> columns);

    @Update({
            "<script>",
                "CREATE TABLE ${table} (",
                    "`id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键、自增、无意',",
                    "`sample_id` varchar(64) NOT NULL COMMENT '样本ID',",
                    "`face_id` varchar(64) NOT NULL COMMENT '人脸ID',",
                    "`face_score` float NOT NULL COMMENT '人脸分数',",
                    "`face_vector` longtext NOT NULL COMMENT '人脸向量',",
                    "<foreach item=\"item\" index=\"index\" collection=\"columns\" open=\"\" separator=\",\" close=\",\">",
                        "<choose>",
                            "<when test=\"item.type=='string'\">",
                                "`${item.name}` varchar(512) DEFAULT NULL COMMENT '${item.desc}'",
                            "</when>",
                            "<when test=\"item.type=='bool'\">",
                                "`${item.name}` tinyint(1) DEFAULT NULL COMMENT '${item.desc}'",
                            "</when>",
                            "<when test=\"item.type=='int'\">",
                                "`${item.name}` int(32) DEFAULT NULL COMMENT '${item.desc}'",
                            "</when>",
                            "<when test=\"item.type=='float'\">",
                                "`${item.name}`float DEFAULT NULL COMMENT '${item.desc}'",
                            "</when>",
                            "<when test=\"item.type=='double'\">",
                                "`${item.name}` double DEFAULT NULL COMMENT '${item.desc}'",
                            "</when>",
                        "</choose>",
                    "</foreach>",
                    "`create_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',",
                    "`modify_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',",
                    "PRIMARY KEY (`id`),",
                    "KEY `id` (`id`),",
                    "KEY `sample_id` (`sample_id`),",
                    "KEY `face_id` (`face_id`),",
                    "KEY `create_time` (`create_time`)",
                ")",
                "ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8",
            "</script>"
    })
    int createFaceTable(@Param("table") String table, @Param("columns") List<TableColumn> columns);

    @Update({
            "<script>",
                "CREATE TABLE ${table} (",
                    "`id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键、自增、无意',",
                    "`sample_id` varchar(64) NOT NULL COMMENT '样本ID',",
                    "`face_id` varchar(64) NOT NULL COMMENT '人脸ID',",
                    "`storage_type` varchar(64) DEFAULT NULL COMMENT '数据存储类型',",
                    "`image_raw_info` longtext DEFAULT NULL COMMENT '原始图片数据',",
                    "`image_ebd_info` longtext DEFAULT NULL COMMENT '用于提取特征的人脸图片',",
                    "`image_face_info` longtext DEFAULT NULL COMMENT '图片人脸检测信息',",
                    "`create_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',",
                    "`modify_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',",
                    "PRIMARY KEY (`id`),",
                    "KEY `id` (`id`),",
                    "KEY `sample_id` (`sample_id`),",
                    "KEY `face_id` (`face_id`),",
                    "KEY `storage_type` (`storage_type`),",
                    "KEY `create_time` (`create_time`)",
                ")",
                "ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8",
            "</script>"
    })
    int createImageTable(@Param("table") String table);
}
