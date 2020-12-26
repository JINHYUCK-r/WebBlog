package com.rjh.blog.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

//인증이 안된 사용자들이 출입할수 있는 경로를  /auth/**를 허용
// 그냥 주소가 / 이면 index.jsp 허용
//static이하에 있는 /js/**, /scc/**, /imaga/**

@Controller
public class UserController {
 
	@GetMapping("/auth/joinForm")
	public String joinForm() {
		return "user/joinForm";
	}

	@GetMapping("/auth/loginForm")
	public String loginForm() {
		return "user/loginForm";
	
}
}