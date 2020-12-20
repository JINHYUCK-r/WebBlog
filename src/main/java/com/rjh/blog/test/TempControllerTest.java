package com.rjh.blog.test;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class TempControllerTest {

	//http://localhost:8000/blog/temp/home
	//오류가 발생  
	@GetMapping("/temp/home")
	public String tempHome() {
		System.out.println("tempHome()");
		//@Controller는 기본적으로 파일을 리턴함
		// 파일리턴 기본경로 : src/main/resources/static static밑에는 정적파일만 읽어짐. jsp파일은 못 읽음
		//리턴명을 /home.html 로 해야 읽어짐 
		return "home.html";
	}
	
	@GetMapping("/temp/jsp")
	//src/main/webapp/WEB-INF/views 폴더를 만들고 그 안에 test.jsp파일을 만듦
	//스프링이 jsp파일을 읽고 컴파일 해줌 
	public String tempJsp() {
		//prefix : /WEB-INF/views
		//suffix: .jsp
		
		//return "/test.jsp";
		//풀네임: /WEB-INF/views//test.jsp.jsp
		
		return "test";
		//풀네임: /WEB-INF/views/test.jsp
	}
	
	
}
