package com.rjh.blog.config.oauth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import com.rjh.blog.config.auth.principalDetail;
import com.rjh.blog.config.oauth.provider.FacebookUserInfo;
import com.rjh.blog.config.oauth.provider.GoogleUserInfo;
import com.rjh.blog.config.oauth.provider.OAuth2UserInfo;
import com.rjh.blog.model.RoleType;
import com.rjh.blog.model.User;
import com.rjh.blog.repository.UserRepository;
import com.rjh.blog.service.UserService;


@Service
public class PrincipalOAuth2UserService  extends DefaultOAuth2UserService{
	
	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private UserService userService;

	@Override
	public principalDetail loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
		
		OAuth2User oauth2User = super.loadUser(userRequest);
		OAuth2UserInfo oAuth2UserInfo = null;
		if(userRequest.getClientRegistration().getRegistrationId().equals("google")) {
			oAuth2UserInfo = new GoogleUserInfo(oauth2User.getAttributes());
		}else if(userRequest.getClientRegistration().getRegistrationId().equals("facebook")) {
			oAuth2UserInfo = new FacebookUserInfo(oauth2User.getAttributes());
		}else {
			System.out.println("지원하지 않는 로그인입니다.");
		}
		
		String oauth =  oAuth2UserInfo.getProvier();//google
		String oauthId = oAuth2UserInfo.getProviderId();
		String username =  oauth + "_"+oauthId;
		String password = bCryptPasswordEncoder.encode("암호화비밀번호");
		String email = oAuth2UserInfo.getEmail();

		User userEntity = User.builder()
				.username(username)
				.password(password)
				.email(email)
				.oauth(oauth)
				.build();
		User originuser = userService.findUser(userEntity.getUsername());
		if(originuser.getUsername() == null) {
			userService.Join(userEntity);
		}
		return new principalDetail(originuser, oauth2User.getAttributes()); 
		
	}
}
