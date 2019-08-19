package com.example.starterapp.handler;

import com.example.starterapp.commons.ServerResponse;
import com.example.starterapp.exception.CustomException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
public class CustomExceptionHandler {

	@ResponseBody
	@ExceptionHandler(value = CustomException.class)
	public ServerResponse customExceptionHandler(CustomException e) {
		return ServerResponse.createByErrorCodeMessage(e.getCode(), e.getMessage());
	}
}