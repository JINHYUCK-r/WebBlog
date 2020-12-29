package com.rjh.blog.service;


//Service pakage 가 필요한 이유: 
 // 1. 트랜잭션을 관리하기 위해
// 2. 서비스의 의미 : 하나 혹은 그 이상의 기능을 하나로 묶어서 오류없이 처리하는 것. 
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.rjh.blog.model.RoleType;
import com.rjh.blog.model.User;
import com.rjh.blog.repository.UserRepository;

//스프링이 컴포넌트 스캔을 통해서 Bean에 등록해줌. IoC 해줌  

@Service
public class UserService {
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private BCryptPasswordEncoder encoder;
	

	@Transactional //전체가 성공하면 commit이 됨.  하나라도 실패하게 되면 rollback 이 됨 . 
	public void Join(User user) {
		String rawPassword = user.getPassword();
		String encPasswrod = encoder.encode(rawPassword); //해시 암호화 
		user.setPassword(encPasswrod);
		user.setRole(RoleType.USER);
		userRepository.save(user); //오류가 발생하면 handler에서 오류처리됨 
	}
	
	@Transactional
	public void modify(User user) {
		//수정시에는 영속성 컨텍스트 User 오브젝트를 영속화시키고, 영속화된 User오브젝트를 수정 
		//select 해서 User 오브젝트를 DB로 부터 가져오는 이유는 영속화 하기 위해서
		// 영속화된 오브젝트를 변경하명 자동으로 DB에 update문을 날려줌 
		User persistance = userRepository.findById(user.getId()).orElseThrow(()->{
			return new IllegalArgumentException("회원찾기실패");
		});
		String rawPassword = user.getPassword();
		String encPassword = encoder.encode(rawPassword);
		persistance.setPassword(encPassword);
		persistance.setEmail(user.getEmail());
		
		//회원수정 함수 종료시 = 서비스 종료시 = 트랜잭션이 종료 = commit 이 자동으로 됨 
		//영속화된 persistance 객체의 변화가 감지되면 더티체킹이 되어 update문을 날려줌 
	}
	
	
//	@Transactional(readOnly = true) //select 할때 트랜잭션 시작, 서비스 종료시에 트랜잭션 종료(정합성 유지)
//	public User login(User user) {
//		return userRepository.findByUsernameAndPassword(user.getUsername(), user.getPassword());
//	}
}
