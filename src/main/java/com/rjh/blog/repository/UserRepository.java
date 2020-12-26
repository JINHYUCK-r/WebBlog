package com.rjh.blog.repository;
//db에 값을 insert 하기 위한 패키지 
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.rjh.blog.model.User;

//DAO같은 역할 . 자동으로 bean으로 등록됨. @Repository 생략가능 
public interface UserRepository extends JpaRepository<User, Integer>{
	//JpaRepository는 User테이블을 관리. User테이블의 primarykey 는 Integer
	//JpaRepository는 findall 등의  함수를 가지고 있음. 기본적인 crud가능 
	
	//전통적 로그인을 위한 함수
	// JPA Naming 전략
	//User findByUsernameAndPassword(String username, String password);
	//findByUsernameAndPassword 실제로 없는 함수지만 이름을 이렇게 만들어 주면 
	//select *from user Where username=? and password=? 이 메소드가 자동적으로 실행되고 파라미터값이 순차적으로 들어감 
	//return User 로 함 
	
	// 이렇게 만들어 줘도 
//	@Query(value="select *from user Where username=? and password=?", nativeQuery = true)
//	User login(String usename, String password);
	
	
}
