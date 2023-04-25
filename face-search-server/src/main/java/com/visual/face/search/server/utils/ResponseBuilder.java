package com.visual.face.search.server.utils;

import com.visual.face.search.server.domain.common.ResponseInfo;

public class ResponseBuilder {

	public static final Integer COMMON_SUCCESS_CODE = 0;
	public static final String COMMON_SUCCESS_INFO = "success";

    public static final Integer COMMON_EXCEPTION_CODE = 1;
    public static final String COMMON_EXCEPTION_INFO = "exception";

    public static final Integer COMMON_ERROR_CODE = 2;
    public static final String COMMON_ERROR_INFO = "error";
    
	public static <T> ResponseInfo<T> success(){
		return new ResponseInfo<>(COMMON_SUCCESS_CODE,COMMON_SUCCESS_INFO, null);
	}
	
	public static <T> ResponseInfo<T> success(T data){
		return new ResponseInfo<>(COMMON_SUCCESS_CODE,COMMON_SUCCESS_INFO, data);
	}
	
	public static <T> ResponseInfo<T> error(){
		return new ResponseInfo<>(COMMON_ERROR_CODE,COMMON_ERROR_INFO, null);
	}

	public static <T> ResponseInfo<T> error(String errorInfo){
		return new ResponseInfo<>(COMMON_ERROR_CODE,errorInfo, null);
	}
	
	public static <T> ResponseInfo<T> error(T data){
		return new ResponseInfo<>(COMMON_ERROR_CODE,COMMON_ERROR_INFO, data);
	}
	
	public static <T> ResponseInfo<T> error(T data, String errorInfo){
		return new ResponseInfo<>(COMMON_ERROR_CODE,errorInfo, data);
	}
	
	public static <T> ResponseInfo<T> exception(){
		return new ResponseInfo<>(COMMON_EXCEPTION_CODE, COMMON_EXCEPTION_INFO, null);
	}
	
	public static <T> ResponseInfo<T> exception(T data){
		return new ResponseInfo<>(COMMON_EXCEPTION_CODE, COMMON_EXCEPTION_INFO, data);
	}
	
	public static <T> ResponseInfo<T> exception(Exception e){
		return new ResponseInfo<>(COMMON_EXCEPTION_CODE, e.getMessage(), null);
	}
	
	public static <T> ResponseInfo<T> exception(Exception e, T data){
		return new ResponseInfo<>(COMMON_EXCEPTION_CODE, e.getMessage(), data);
	}
	
	public static <T> ResponseInfo<T> exception(String info, T data){
		return new ResponseInfo<>(COMMON_EXCEPTION_CODE, info, data);
	}
	
}
