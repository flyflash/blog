package com.example.starterapp.enums;

import lombok.Getter;

@Getter
public enum ResponseCode {

	SUCCESS_CODE(200, "请求成功"),
	FAIL_CODE(201, "请求失败"),

	/** commons*/
	PARAM_NULL_ERROR(2000, "请求参数为空"),

	FILE_IS_NULL(2001, "文件为空，请重新上传"),
	FILE_FORMAT_ERROR(2002, "文件格式错误, 请上传图片"),
	FILE_SIZE_NOT_SUITABLE(2003, "图片大小不合适"),

	USER_NOT_EXIST(3000, "用户名不存在"),
	LOGIN_PWD_ERROR(3001, "用户密码错误"),
	TOKEN_ENCRYPT_ERROR(3002, "token加密错误")
	;
	
	private Integer code;
	
	private String msg;
	
	private ResponseCode(Integer code, String msg) {
		this.code = code;
		this.msg = msg;
	}
}
