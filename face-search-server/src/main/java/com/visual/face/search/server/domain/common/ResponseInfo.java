package com.visual.face.search.server.domain.common;

import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

/**
 * des:接口返回对象
 * @author diven
 * @date 上午9:34 2018/7/12
 */
public class ResponseInfo<T> implements Serializable{
	
	private static final long serialVersionUID = -6919611972884058300L;

    @ApiModelProperty(value="返回代码",name="code",required = true, position = 0)
	private Integer code;
    @ApiModelProperty(value="返回信息",name="message",required = false, position = 1)
    private String  message;
    @ApiModelProperty(value="数据信息",name="data",required = false, position = 2)
    private T 	    data;

    public ResponseInfo(){}

    public ResponseInfo(Integer code, String message, T data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
