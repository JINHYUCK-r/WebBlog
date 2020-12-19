package com.rjh.blog.test;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

//import lombok.AllArgsConstructor;
//import lombok.Data;
//import lombok.RequiredArgsConstructor;
//
////@Getter
////@Setter
//@Data	//getter, setter역할을 동시에 하는
////@AllArgsConstructor //생성자 만들어줌  
//
//@RequiredArgsConstructor //final붙은 애들에 대한 contsruct를 만들어줌

@Data
@NoArgsConstructor

public class Member {
	private  int id;
	private  String username;
	private  String password;
	private  String email;
	
	@Builder
	public Member(int id, String username, String password, String email) {
		this.id = id;
		this.username = username;
		this.password = password;
		this.email = email;
	}
	
	
	
}
