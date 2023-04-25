package com.visual.face.search.model;

import java.io.Serializable;

/**
 * des:接口返回对象
 * @author diven
 * @date 上午9:34 2018/7/12
 */
public class Response<T> implements Serializable{
	
	private static final long serialVersionUID = -6919611972884058300L;

	private Integer code = -1;
    private String  message;
    private T 	    data;

    public Response(){}

    public Response(Integer code, String message, T data) {
        if(null != code) {
            this.code = code;
        }
        this.message = message;
        this.data = data;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        if(null != code){
            this.code = code;
        }
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

    public boolean ok(){
        return new Integer(0).equals(code);
    }

    @Override
    public String toString() {
        return "Response{" + "code=" + code + ", message='" + message + '\'' + ", data=" + data + '}';
    }
}
