package com.rjh.blog.repository;
import java.util.Optional;

//db에 값을 insert 하기 위한 패키지 
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.rjh.blog.model.Board;
import com.rjh.blog.model.User;



public interface BoardRepository extends JpaRepository<Board, Integer>{

	@Modifying //변경, 삭제 쿼리 메서드를 사용할 때 필요
	@Query(value="update board set count = count+1 where id= ?" , nativeQuery = true)
	//ReplySaveRequestDto 에 있는 userId, boardId, content 가 ?1,2,3에순서대로 담김 
	//쿼리에 들어가는 순서를 잘 맞추어 주어야함 . 
	int bCount(int boardId); 
	
}
