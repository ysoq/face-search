package com.visual.face.search.server.domain.extend;

import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

/**
 * 字段定义
 */
public class FiledColumn implements Serializable {
    /**字段名称*/
    @ApiModelProperty(value="字段名称,支持小写字母、数字和下划线的组合，最大32个字符", position = 1,required = true)
    private String name;
    /**字段描述*/
    @ApiModelProperty(value="字段描述,最大64个字符", position = 2,required = false)
    private String comment;
    /**字段类型*/
    @ApiModelProperty(value="字段类型，不能为UNDEFINED类型", position = 3,required = true)
    private FiledDataType dataType;

    /**构建工具**/
    public static FiledColumn build(){
        return new FiledColumn();
    }

    public String getName() {
        return name;
    }

    public FiledColumn setName(String name) {
        this.name = name;
        return this;
    }

    public String getComment() {
        return comment;
    }

    public FiledColumn setComment(String comment) {
        this.comment = comment;
        return this;
    }

    public FiledDataType getDataType() {
        return dataType;
    }

    public FiledColumn setDataType(FiledDataType dataType) {
        this.dataType = dataType;
        return this;
    }
}
