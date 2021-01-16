package com.rjh.blog.config.oauth.provider;

import java.util.Map;

public class NaverUserInfo implements OAuth2UserInfo {
	
	private Map<String, Object> attributes; //getAttuributes() 를 받을것 
	
	public NaverUserInfo(Map<String, Object> attributes) {
		this.attributes = attributes;
	}

	@Override
	public String getProviderId() {
		return (String) attributes.get("id");
	}

	@Override
	public String getProvier() {
		return "naver";
	}

	@Override
	public String getEmail() {
		return (String) attributes.get("email");
	}

	@Override
	public String getName() {
		return (String) attributes.get("name");
	}

}
