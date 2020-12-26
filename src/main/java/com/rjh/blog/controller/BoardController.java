package com.rjh.blog.controller;
//html을 전달하는 컨트롤러 

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import com.rjh.blog.config.auth.principalDetail;

@Controller
public class BoardController {

	@GetMapping({"","/"})
	//@AuthenticationPrincipal : security core로 임포트.  
	//@AuthenticationPrincipal principalDetail principal 이것을 이용하여 세션에 접근가능 
	public String index(@AuthenticationPrincipal principalDetail principal) { 
		// /WEB-INF/views/index.jsp 로 찾게됨 
		System.out.println("로그인 사용자 아이디: "+ principal.getUsername());
		return "index";
	}
}
