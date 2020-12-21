package com.rjh.blog.test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.rjh.blog.model.RoleType;
import com.rjh.blog.model.User;
import com.rjh.blog.repository.UserRepository;

@RestController
public class DummyControllerTest {

	//의존성주입(DI)
	@Autowired //DummyControllerTest가 메모리에 뜰때 userRepository도 같이 뜸 
	//UserRepository 형태로 spring이 관리하고 있는게 있다면 userRepository에 넣어라.
	//UserRepository는 JpaRepository에 의해 스프링이 실행될때 스캔되어 생성되어져있음 
	private UserRepository userRepository;
	
	//http://localhost:8000/blog/dummy/join(요청)
	//http의 body에 username, password, email 데이터를 가지고 요청 
	@PostMapping("/dummy/join")
	//public String join(String username, String password, String email) { //key=value 형태의 값을 받아줌 (약속된 규칙. x-www-form형태로 전송되는 형태는 함수의 파라미터로 파싱해서 넣어줌  )
	//System.out.println("username: " + username);
	public String join(User user) { //오브젝트 형태로 받을수도 있음 	
		System.out.println("id: " + user.getId());
		System.out.println("username: " + user.getUsername());
		System.out.println("password: " + user.getPassword());
		System.out.println("email: " + user.getEmail());
		System.out.println("role: " + user.getRole());
		System.out.println("creatDate: " + user.getCreateDate());
		
		//user.setRole("user"); string으로 못쓰도록 제한을 걸어놨기때문에 오류발생 
		user.setRole(RoleType.USER);
		userRepository.save(user);
		return "회원가입이 완료 되었습니다.";
	}
}
