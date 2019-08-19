package com.example.starterapp.exception;

import com.example.starterapp.enums.ResponseCode;
import lombok.Getter;

@Getter
public class CustomException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	private Integer code;
	
	public CustomException(ResponseCode responseCode) {
		super(responseCode.getMsg());
		this.code = responseCode.getCode();
	}
	
	public CustomException(Integer code, String msg) {
		super(msg);
		this.code = code;
	}
}
