package com.rjh.blog.controller.api;
import javax.servlet.http.HttpSession;

// json 데이터를 전달하는 컨트롤러 
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.rjh.blog.dto.ResponseDto;
import com.rjh.blog.model.RoleType;
import com.rjh.blog.model.User;
import com.rjh.blog.service.UserService;



@RestController
public class UserApiController {
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private HttpSession session;

	@PostMapping("/api/user")
	public ResponseDto<Integer> save(@RequestBody User user) { //username, password, email 3가지를 받음 
		//System.out.println("UserApiController : save 호출 ");
		//실제로 DB에 Insert 구현 
		user.setRole(RoleType.USER);
		userService.Join(user);
		return new ResponseDto<Integer>(HttpStatus.OK.value(), 1); //date:1, status: 200 을 반환 / 자바오브젝트를 json으로 변환해서 리턴(jackson)
	}
	
	@PostMapping("/api/user/login")
	public ResponseDto<Integer> login(@RequestBody User user){
		System.out.println("UserApiController : login 호출 ");
		User pricipal = userService.login(user); // pricipal(접근주)
		
		if(pricipal !=null) {
			session.setAttribute("principal", pricipal); //세션생성 
		}
		
		return new ResponseDto<Integer>(HttpStatus.OK.value(), 1); 
	}
	
}
