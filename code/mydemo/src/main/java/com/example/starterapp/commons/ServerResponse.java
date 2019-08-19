package com.example.starterapp.commons;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.example.starterapp.enums.ResponseCode;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import lombok.Getter;

@Getter
@SuppressWarnings("deprecation")
@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
//注解中的属性：保证序列化json的时候，如果是null对象，key也会消失
public class ServerResponse<T> implements Serializable {

	private static final long serialVersionUID = -6064832354890786471L;

	private int code;
	
	private String msg;
	
	private T data;
	
	private String serverTime;
	
	private ServerResponse(int code) {
		this.code = code;
		this.serverTime = this.setTime();
	}
	
	private ServerResponse(int code, String msg) {
		this.code = code;
		this.msg = msg;
		this.serverTime = this.setTime();
	}
	
	private ServerResponse(int code, T data) {
		this.code = code;
		this.data = data;
		this.serverTime = this.setTime();
	}
	
	private String setTime() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return sdf.format(new Date());
	}
	
	public static <T> ServerResponse<T> createBySuccess(){
		return new ServerResponse<T>(ResponseCode.SUCCESS_CODE.getCode());
	}
	
	public static <T> ServerResponse<T> createBySuccess(T data){
		return new ServerResponse<T>(ResponseCode.SUCCESS_CODE.getCode(), data);
	}
	
	public static <T> ServerResponse<T> createBySuccessMessage(String msg){
		return new ServerResponse<>(ResponseCode.SUCCESS_CODE.getCode(), msg);
	}
	
	public static <T> ServerResponse<T> createByError(){
		return new ServerResponse<>(ResponseCode.FAIL_CODE.getCode(), ResponseCode.FAIL_CODE.getMsg());
	}
	
	public static <T> ServerResponse<T> createByErrorMessage(String msg){
		return new ServerResponse<T>(ResponseCode.FAIL_CODE.getCode(), msg);
	}
	
	public static <T> ServerResponse<T> createByErrorCodeMessage(int code, String msg){
		return new ServerResponse<T>(code, msg);
	}
}
