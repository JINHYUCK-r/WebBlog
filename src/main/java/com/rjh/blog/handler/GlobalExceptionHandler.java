package com.rjh.blog.handler;
//예외처리 페이지

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;

import com.rjh.blog.dto.ResponseDto;

@ControllerAdvice //어디에서든 이쪽으로 오게하기 위해서 
@RestController
public class GlobalExceptionHandler {
	
	@ExceptionHandler(value = Exception.class)
	public ResponseDto<String> handleArgumentException(Exception e) {
		return new ResponseDto<String>(HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage());
	}
	
	
}
