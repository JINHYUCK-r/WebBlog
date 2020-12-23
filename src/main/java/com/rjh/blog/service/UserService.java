package com.rjh.blog.service;
import javax.transaction.Transactional;

//Service pakage 가 필요한 이유: 
 // 1. 트랜잭션을 관리하기 위해
// 2. 서비스의 의미 : 하나 혹은 그 이상의 기능을 하나로 묶어서 오류없이 처리하는 것. 
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rjh.blog.model.User;
import com.rjh.blog.repository.UserRepository;

//스프링이 컴포넌트 스캔을 통해서 Bean에 등록해줌. IoC 해줌  

@Service
public class UserService {
	
	@Autowired
	private UserRepository userRepository;
	
	@Transactional //전체가 성공하면 commit이 됨.  하나라도 실패하게 되면 rollback 이 됨 . 
	public int Join(User user) {
		try{
			userRepository.save(user);
			return 1;
		}catch(Exception e){
			e.printStackTrace();
			System.out.println("UserServie: Join(): " + e.getMessage());
		}
		return -1;
	}
}
