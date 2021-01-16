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
		String oauth = userRequest.getClientRegistration().getRegistrationId(); //google
		String oauthId = oauth2User.getAttribute("sub");
		String username =  oauth + "_"+oauthId;
		String password = bCryptPasswordEncoder.encode("암호화비밀번호");
		String email = oauth2User.getAttribute("email");

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
