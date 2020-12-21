package com.rjh.blog.repository;
//db에 값을 insert 하기 위한 패키지 
import org.springframework.data.jpa.repository.JpaRepository;

import com.rjh.blog.model.User;

//DAO같은 역할 . 자동으로 bean으로 등록됨. @Repository 생략가능 
public interface UserRepository extends JpaRepository<User, Integer>{
	//JpaRepository는 User테이블을 관리. User테이블의 primarykey 는 Integer
	//JpaRepository는 findall 등의  함수를 가지고 있음. 기본적인 crud가능 
}
