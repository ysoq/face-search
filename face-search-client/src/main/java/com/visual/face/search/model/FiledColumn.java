package com.visual.face.search.model;

import java.io.Serializable;

/**
 * 字段定义
 */
public class FiledColumn implements Serializable {
    /**字段名称*/
    private String name;
    /**字段描述*/
    private String comment;
    /**字段类型*/
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
