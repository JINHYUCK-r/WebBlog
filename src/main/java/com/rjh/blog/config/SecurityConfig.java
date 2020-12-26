package com.rjh.blog.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;


@Configuration //Bean 등록: 스프링 컨테이너에서 객체를 관리 할수 있게 하는 것(IoC관리 )
@EnableWebSecurity  //리퀘스트에 대한 요청으로 컨트롤러로 가기전에 얘가 실행될수 있도록 필터링 
// 시큐리티 필터 추가 = 스프링 시큐리티가 활성화가 되어 잇는데 어떤 설정을 해당 파일에서 하겠다.  여기서는 SecurityConfig 에서 설정을 하게됨. 
@EnableGlobalMethodSecurity(prePostEnabled = true) //특정주소로 접근을 하면 권한 및 인증을 미리 체크하겠다. 

public class SecurityConfig extends WebSecurityConfigurerAdapter{

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http
			.authorizeRequests()			//요청이 들어오면 
				.antMatchers("/auth/**")	// /auth 이하 주소는 
				.permitAll()							//모두 접근이 가능
				.anyRequest()					//그외의 다른 모든 요청
				.authenticated()				//인증이 필요해 
			.and()										//인증이 필요할때는 
				.formLogin()
				.loginPage("/auth/loginForm");	//우리가 만든 페이지로 이동 
	}
}

