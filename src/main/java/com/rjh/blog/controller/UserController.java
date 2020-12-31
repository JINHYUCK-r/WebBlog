package com.rjh.blog.controller;
//페이지 이동을 위한 컨트롤러 @Controller



import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.rjh.blog.model.KakaoProfile;
import com.rjh.blog.model.OAuthToken;
import com.rjh.blog.model.User;
import com.rjh.blog.service.UserService;

//인증이 안된 사용자들이 출입할수 있는 경로를  /auth/**를 허용
// 그냥 주소가 / 이면 index.jsp 허용
//static이하에 있는 /js/**, /scc/**, /imaga/**

@Controller
public class UserController {

	@Value("${rjh.key}")
	private String rjhkey;
	
	@Autowired
	private AuthenticationManager authenticationManager; 
	
		@Autowired
		private UserService userService;
		
	@GetMapping("/auth/joinForm")
	public String joinForm() {
		return "user/joinForm";
	}

	@GetMapping("/auth/loginForm")
	public String loginForm() {
		return "user/loginForm";
	}
	
	@GetMapping("/user/updateForm")
	public String updateForm() {
		return "user/updateForm";
	}
	
	@GetMapping("/auth/kakao/callback")
	public String kakaoCallback(String code) { //데이터를 리턴해주는 컨트롤러 함수 
		
		//POST 방식으로 key=value 데이터를 요청(카카오쪽으로 )
		//http 요청을편하게 할수 있음 
		RestTemplate rt = new RestTemplate();
		//헤더오브젝트  생성 
		HttpHeaders headers = new HttpHeaders();
		//바디데이터가 어떤형식인지 알려주는 것 
		headers.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");
		
		MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
		//특정값을 직접입력하는것 보다 변수로 지정해서 넣는것이 좋음 
		params.add("grant_type", "authorization_code");
		params.add("client_id", "9245f2292bd208dd4f6dc64bf0ddc378");
		params.add("redirect_uri", "http://localhost:8000/auth/kakao/callback");
		params.add("code", code);
		
		//kakaoTokenRequest 가 헤더값과바디값을 가지고 있는 엔티티가 됨 
		//헤더와 바디를 하나로 묶는 이유는 밑에 exchage 메소드가 httpEntity를 받기 때문 
		HttpEntity<MultiValueMap<String, String>> kakaoTokenRequest = 
				new HttpEntity<>(params,headers);
		
		//http 요청하기 -Post 방식으로 - 그리고 responser 변수의 응답 받음 
		ResponseEntity<String> response = rt.exchange(
				"https://kauth.kakao.com/oauth/token", //token발급요청 주
				HttpMethod.POST,										// 요청 매서드 
				kakaoTokenRequest,										// 데이터(헤더,바디) 
				String.class														//응답받을 타입(스트링으로 응답) 
				
				);
		//Gson, Json Simple, ObjectMapper
				ObjectMapper objectMapper = new ObjectMapper();
				OAuthToken oauthToken = null;
				try {
					oauthToken = objectMapper.readValue(response.getBody(), OAuthToken.class);
				} catch (JsonMappingException e) {
					e.printStackTrace();
				} catch (JsonProcessingException e) {
					e.printStackTrace();
				}
				
				//System.out.println("카카오 엑세스 토큰 : "+oauthToken.getAccess_token());
				
				RestTemplate rt2 = new RestTemplate();
		
				HttpHeaders headers2 = new HttpHeaders();
				headers2.add("Authorization", "Bearer "+oauthToken.getAccess_token());
				headers2.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");
				
				
				HttpEntity<MultiValueMap<String, String>> kakaoProfileRequest2 = 
						new HttpEntity<>(headers2);
				
				//http 요청하기 -Post 방식으로 - 그리고 responser 변수의 응답 받음 
				ResponseEntity<String> response2 = rt2.exchange(
						"https://kapi.kakao.com/v2/user/me", 
						HttpMethod.POST,										
						kakaoProfileRequest2,										
						String.class														
						);
				ObjectMapper objectMapper2 = new ObjectMapper();
				KakaoProfile kakaoProfile = null;
				try {
					kakaoProfile = objectMapper2.readValue(response2.getBody(), KakaoProfile.class);
				} catch (JsonMappingException e) {
					e.printStackTrace();
				} catch (JsonProcessingException e) {
					e.printStackTrace();
				}
				//우리가 만든 User 오브젝트는 유저네임/패스워드/이메일이 필요 
				System.out.println("카카오 아이디(번호 ) : " + kakaoProfile.getId());
				System.out.println("카카오 이메일  : " + kakaoProfile.getKakao_account().getEmail());
				
				System.out.println("블로그서버  유저네임 : " + kakaoProfile.getKakao_account().getEmail()+"_"+kakaoProfile.getId());
				System.out.println("블로그서버 이메일  : " + kakaoProfile.getKakao_account().getEmail());
				
				//UUID -> 중복되지않는 특정 값을 만드러내는 알고리즘 
				//UUID garbagePassword = UUID.randomUUID();
				System.out.println("블로그서버 패스워드  : " + rjhkey);
				
				User kakaoUser = User.builder()
						.username(kakaoProfile.getKakao_account().getEmail()+"_"+kakaoProfile.getId())
						.password(rjhkey)
						.email(kakaoProfile.getKakao_account().getEmail())
						.oauth("kakao")
						.build();
		//가입자 비가입자 체크 처리
			User originUser = userService.findUser(kakaoUser.getUsername());	
			
			//비가입자면 회원가입 처리 
				if(originUser.getUsername() == null) {
					
					userService.Join(kakaoUser);
				}
			//가입자면 로그인 처리
				Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(kakaoUser.getUsername(), rjhkey));
				SecurityContextHolder.getContext().setAuthentication(authentication);
				
		return "redirect:/";
	}

}