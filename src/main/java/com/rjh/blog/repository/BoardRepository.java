package com.rjh.blog.repository;
import java.util.Optional;

//db에 값을 insert 하기 위한 패키지 
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.rjh.blog.model.Board;
import com.rjh.blog.model.User;



public interface BoardRepository extends JpaRepository<Board, Integer>{


	
}
