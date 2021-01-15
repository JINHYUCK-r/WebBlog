package com.rjh.blog.config.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.rjh.blog.model.User;
import com.rjh.blog.repository.UserRepository;

@Service //bean 등록 
public class principalDetailService implements UserDetailsService{
	
	@Autowired
	private UserRepository userRepository;

	//스프링이 로그인 요청을 가로챌때 username과 password 변수를 가로챌때 password는 알아서 처리함
	//username 이  해당 db에 있는지만 확인해주면 됨. 
	//이걸 오버라이딩 하지않으면 우리가 만든정보를 세팅할수가 없음 .
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
		
		User principal = userRepository.findByUsername(username) // 유저정보를 select해줌 
				.orElseThrow(()->{
					return new UsernameNotFoundException("해당 사용자를 찾을수 없습니다."+username);
				});
				return new principalDetail(principal); //시큐리티 세션에 유저정보가 저장됨 UserDetails 타입으로 
				

}
}
