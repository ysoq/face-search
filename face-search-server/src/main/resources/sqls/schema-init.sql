SET FOREIGN_KEY_CHECKS = 0;

CREATE TABLE IF NOT EXISTS `visual_collection` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '自增主键自增',
  `uuid` varchar(127) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '字符唯一键',
  `namespace` varchar(16) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '命名空间',
  `collection` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '集合名称',
  `describe` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '集合描述',
  `statue` int DEFAULT NULL COMMENT '集合状态',
  `sample_table` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '样本数据表',
  `face_table` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '人脸数据表',
  `image_table` varchar(64)  CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '图片数据信息',
  `vector_table` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '人脸向量库',
  `schema_info` longtext CHARACTER SET utf8 COLLATE utf8_general_ci COMMENT '集合元数据信息',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `deleted` char(1) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT '0' COMMENT '是否删除',
  PRIMARY KEY (`id`),
  KEY `id` (`id`),
  KEY `uuid` (`uuid`),
  KEY `namespace` (`namespace`),
  KEY `collection` (`collection`),
  KEY `create_time` (`create_time`),
  KEY `update_time` (`update_time`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;

SET FOREIGN_KEY_CHECKS = 1;
