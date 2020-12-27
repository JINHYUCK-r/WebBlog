package com.rjh.blog.service;





import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.rjh.blog.model.Board;
import com.rjh.blog.model.RoleType;
import com.rjh.blog.model.User;
import com.rjh.blog.repository.BoardRepository;
import com.rjh.blog.repository.UserRepository;



@Service
public class BoardService {
	
	@Autowired
	private BoardRepository boardRepository;

	
	@Transactional 
	public void write(Board board, User user) { //titel과 content 를 받음 
		board.setCount(0);
		board.setUser(user);
		boardRepository.save(board);
	}
	
	public Page<Board> readList(Pageable pageable ){ //page를 리턴해서 page에 들어있는 정보를 활용 
		return boardRepository.findAll(pageable);
	}
	
	public Board readDetail(int id) {
		return boardRepository.findById(id)
				.orElseThrow(() ->{
					return new IllegalArgumentException("글 상세보기 실패: 아이디를 찾을수 없습니다. ");
				});
	}

}
