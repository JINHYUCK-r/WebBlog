package com.rjh.blog.dto;
//응답할때 사용

import org.springframework.http.HttpStatus;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class ResponseDto<T> {

	HttpStatus status;
	T data;
}
