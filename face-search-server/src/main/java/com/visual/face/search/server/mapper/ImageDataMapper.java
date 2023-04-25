package com.visual.face.search.server.mapper;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.SelectKey;
import com.visual.face.search.server.model.ImageData;

public interface ImageDataMapper {

    @Insert({
            "insert into ${table} (sample_id, face_id, ",
            "storage_type, image_raw_info, ",
            "image_ebd_info, image_face_info, ",
            "create_time, modify_time)",
            "values (#{image.sampleId,jdbcType=VARCHAR}, #{image.faceId,jdbcType=VARCHAR}, ",
            "#{image.storageType,jdbcType=VARCHAR}, #{image.imageRawInfo,jdbcType=VARCHAR}, ",
            "#{image.imageEbdInfo,jdbcType=VARCHAR}, #{image.imageFaceInfo,jdbcType=VARCHAR}, ",
            "#{image.createTime,jdbcType=TIMESTAMP}, #{image.modifyTime,jdbcType=TIMESTAMP})"
    })
    @SelectKey(statement="SELECT LAST_INSERT_ID()", keyProperty="id", before=false, resultType=Long.class)
    int insert(@Param("table") String table, @Param("image") ImageData record);

}
