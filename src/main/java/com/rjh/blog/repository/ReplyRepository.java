package com.rjh.blog.repository;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;


import com.rjh.blog.dto.ReplySaveRequestDto;
import com.rjh.blog.model.Reply;

public interface ReplyRepository  extends JpaRepository<Reply, Integer>{

	@Modifying //변경, 삭제 쿼리 메서드를 사용할 때 필요
	@Query(value="INSERT INTO reply(userId, boardId, content, createDate) VALUES(?1, ?2, ?3, now())" , nativeQuery = true)
	//ReplySaveRequestDto 에 있는 userId, boardId, content 가 ?1,2,3에순서대로 담김 
	//쿼리에 들어가는 순서를 잘 맞추어 주어야함 . 
	int mSave(int useId, int boardId, String content); // jdbc가 행동을 수행(업뎃,삭제,인설)하고 리턴값을 업데이트 된 행의 개수를 리턴해줌 
}
