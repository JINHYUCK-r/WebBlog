package com.rjh.blog.config.auth;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.user.OAuth2User;

import com.rjh.blog.model.User;

import lombok.Data;
import lombok.Getter;

// 스프링 시큐리티가 로그인 요청을 가로채서 로그인을 진행하고완료가 되면 UserDetails 타입의 오브젝를 스프링시큐리티의 고유한 세션저장소에 저장한다. 
@Getter
public class principalDetail implements UserDetails, OAuth2User {
	private User user; //컴포지션 : 객체를 안에 품고있는것.   extends 하는건 상속 
	private Map<String,Object> attributes;

	//생성자
	public principalDetail(User user) {
		this.user = user; 
	}
	public principalDetail(User user, Map<String,Object> attributes) {
		this.user = user;
		this.attributes = attributes;
	}
	
	
	//UserDetails에 있는 함수들을 전부 오버라이딩 
	@Override
	public String getPassword() {
		// TODO Auto-generated method stub
		return user.getPassword();
	}

	@Override
	public String getUsername() {
		// TODO Auto-generated method stub
		return user.getUsername();
	}

	// 계정이 만료되지 않았는지 리턴한다 (true : 만료안됨)
	@Override
	public boolean isAccountNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}

	// 계정이 잠겨있는지 확인 (true :잠기지 않음)
	@Override
	public boolean isAccountNonLocked() {
		// TODO Auto-generated method stub
		return true;
	}

	//비밀번호의 만료여부 확인 ( true: 만료안됨)
	@Override
	public boolean isCredentialsNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}

	//계정활성화(사용가능) 여부 리턴 (true: 활성화 )
	@Override
	public boolean isEnabled() {
		// TODO Auto-generated method stub
		return true;
	}
	
	//계정의 권한을 리턴 .권한히 여러개 있으면 루프를 돌아야 할 필요도 있음 
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		Collection<GrantedAuthority> collectors = new ArrayList<>();  //ArrayList 의 부모가 Collection타입이기때문에 가능 
		
		collectors.add(new GrantedAuthority() {
			
			//자바는 매서드를 직접넣을수 없기 때문에 오브젝트로 담아야함 
			//추상매서드를 오버라이딩 
			@Override
			public String getAuthority() {
				// TODO Auto-generated method stub
				return "ROLE_"+user.getRole();	  //롤을 받을때 "ROLE_" 꼭 넣어줘야함 규칙 
			}
		});
		//collectors.add(()->{return "ROLE"+user.getRole()}); 위에 것을 람다식으로 바꾼것. 들어갈수있는 파라미터와 함수식이 하나밖에 없기때문에 생략가능 
		return collectors;
	}

	@Override
	public Map<String, Object> getAttributes() {
		// TODO Auto-generated method stub
		return attributes;
	}

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return null;
	}

	
}
