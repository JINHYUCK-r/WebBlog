package com.rjh.blog.controller.api;
import javax.servlet.http.HttpSession;

// json 데이터를 전달하는 컨트롤러 
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
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
	
//	@Autowired 이렇게 세션을 할당해서 전통적로그인 방식에 넣을수 있음. 파라미터를 할당하지 않고  
//	private HttpSession session;
	
	@PostMapping("/auth/joinProc")
	public ResponseDto<Integer> save(@RequestBody User user) { //username, password, email 3가지를 받음 
		//System.out.println("UserApiController : save 호출 ");
		//실제로 DB에 Insert 구현 
		userService.Join(user);
		return new ResponseDto<Integer>(HttpStatus.OK.value(), 1); //date:1, status: 200 을 반환 / 자바오브젝트를 json으로 변환해서 리턴(jackson)
	}
	
	//스프링 시큐리티 이용해서 로그인 만들기
	
  /*	
// 전통적인 방식의 로그인 
	@PostMapping("/api/user/login")
	public ResponseDto<Integer> login(@RequestBody User user, {HttpSession session//세션을 파라미터로 할당시 autowired를 안써도됨}){
		//System.out.println("UserApiController : login 호출 ");
		User pricipal = userService.login(user); // pricipal(접근주)
		
		if(pricipal !=null) {
			session.setAttribute("principal", pricipal); //세션생성 
		}
		
		return new ResponseDto<Integer>(HttpStatus.OK.value(), 1); 
	}
	*/
	
}
