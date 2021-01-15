package com.rjh.blog.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.rjh.blog.config.auth.principalDetail;
import com.rjh.blog.config.auth.principalDetailService;
import com.rjh.blog.config.oauth.PrincipalOAuth2UserService;


@Configuration //Bean 등록: 스프링 컨테이너에서 객체를 관리 할수 있게 하는 것(IoC관리 )
@EnableWebSecurity  //리퀘스트에 대한 요청으로 컨트롤러로 가기전에 얘가 실행될수 있도록 필터링 
// 시큐리티 필터 추가 = 스프링 시큐리티가 활성화가 되어 잇는데 어떤 설정을 해당 파일에서 하겠다.  여기서는 SecurityConfig 에서 설정을 하게됨. 
@EnableGlobalMethodSecurity(prePostEnabled = true) //특정주소로 접근을 하면 권한 및 인증을 미리 체크하겠다. 

public class SecurityConfig extends WebSecurityConfigurerAdapter{

	@Autowired
	private principalDetailService principalDetailService;
	@Autowired
	private PrincipalOAuth2UserService principalOAuth2UserService;
	
	 
	@Bean
	@Override
	public AuthenticationManager authenticationManagerBean() throws Exception {
		return super.authenticationManagerBean();
	}

	//  이 객체를 통해서 해시암호화가 됨 
	@Bean //Ioc 리턴되는 값을 스프링이 관리함 
	public BCryptPasswordEncoder encodePWD() {
		return new BCryptPasswordEncoder();
	}
	
	//시큐리티가 대신 로그인 할때 password를 가로채서 해당 password가 무엇으로 해시가 되어서 회원가입이 되었는지 알아야 같은 해시로 암호화해서 db에 있는 해시와 비교 가능
	//이걸 안만들면 패스워드 비교를 할수 없음 
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(principalDetailService).passwordEncoder(encodePWD());

	}
	
	/*
	  xss : 자바스크립트 공격  lucy 필터같은것으로 막을수 있음  
	 csrf: 하이퍼링크 등의 웹사이트 주소를 이용한 공격 
	 */
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http
			.csrf().disable() // csrf 토큰 비 활성화 (테스트시 걸어두는게 좋음 )
			.authorizeRequests()			//요청이 들어오면 
				.antMatchers("/user/**").authenticated()
				.antMatchers("/manager/**").access("hasRole('ROLE_ADMIN') or hasRole('ROLE_MANAGER')")
				.antMatchers("/admin/**").access("hasRole('ROLE_ADMIN')")
				.antMatchers("/","/auth/**", "/js/**","/css/**","/image/**")	// /auth 이하 주소는 
				.permitAll()							//모두 접근이 가능
				.anyRequest()					//그외의 다른 모든 요청
				.authenticated()				//인증이 필요해 
			.and()										//인증이 필요할때는 
				.formLogin()
				.loginPage("/auth/loginForm")	//우리가 만든 페이지로 이동. 로그인 버튼이 수행되면  
				.loginProcessingUrl("/auth/loginProc") //스프링 시큐리티가 해당주소로 로그인을 가로채서 유저정보를 principalDetailService에 있는 loadUser~~에 던져줌. 대신 로그인 해줌 
				.defaultSuccessUrl("/") // 정상적으로 로그인 요청이 완료되면 이쪽으로 이동
		//		.failureUrl("/fail") 실패시에는 이렇게 이동하라고 만들수도 있다.
				.and()
				.oauth2Login()
				.loginPage("/auth/loginForm")
				.userInfoEndpoint()
				.userService(principalOAuth2UserService);
	}
	
}

