package com.rjh.blog.model;
//테이블을 생성하기위한 모델 만들것 

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.DynamicInsert;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


//ORM ->Java(다른언어) Object -> 테이블로 매핑해주는 기술 

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder  //빌더패턴

@Entity //User클래스를 통해서 내용을 읽어서 Mysql에 테이블이 생성됨
//@DynamicInsert //insert 할때 null인 필드 제외. 지금은 사용하지 않고 만들어보기 
public class User {
	
	@Id //primary key
	@GeneratedValue(strategy = GenerationType.IDENTITY) //넘버링전략: 해당 프로젝트에 연결된 db의 넘버링 전략을 따라간다. 
	//오라클을 사용하면 시퀀스가 되는거고, mysql을 사용하면 auto-increment가 되는것 
	private int id; //auto_increment 할것 
	
	@Column(nullable = false, length =150, unique = true)
	// 널이 될수없음, 아이디의 길이를 100자 이하로 제한 
	private String username; //아이디
	
	@Column(nullable = false, length =100) // 비밀번호를 길게하는 이유는 123456 => 해쉬(비밀번호 암호화)
	private String password;
	
	@Column(nullable = false, length =50)
	private String email;
	
	//@ColumnDefault("'user'") // 쓸때 ' ' 를 붙여서 문자라는 것을 알려줘야함 . 기본값을 user로 적는데 insert할때 비워져 있지 않으면 Null로 입력됨 
	//private String role; //정확히 할때는 Enum을 쓰는게 좋다. // Admin, user, manage 등의 권한// Enum을 쓰면 쓸수 있는 도메인을 한정시킬수 있음
	
	//DynamicInsert사용하지않고 값을 넣기 
	@Enumerated(EnumType.STRING) // db는 RoleType이라는게 없기 때문에 해당 enum이 string이라고 알려주어야함.
	private RoleType role; //ADMIN, USER로 한정지음
	
	private String oauth; //kakao, google 등 OAuth 로그인을 통했을때 확인용 
	
	@CreationTimestamp // 시간이 자동입력 
	private Timestamp createDate;
}
