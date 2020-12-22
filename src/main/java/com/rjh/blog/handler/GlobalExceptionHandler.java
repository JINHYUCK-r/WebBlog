package com.rjh.blog.handler;
//예외처리 페이지

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;

@ControllerAdvice //어디에서든 이쪽으로 오게하기 위해서 
@RestController
public class GlobalExceptionHandler {
	
	@ExceptionHandler(value = Exception.class)
	public String handleArgumentException(Exception e) {
		
		return  "<h1>"+e.getMessage()+"</h1>";
	}
	
	
}
