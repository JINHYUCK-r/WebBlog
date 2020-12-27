package com.rjh.blog.service;



import org.springframework.beans.factory.annotation.Autowired;
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
	public void Write(Board board, User user) { //titel과 content 를 받음 
		board.setCount(0);
		board.setUser(user);
		boardRepository.save(board);
	}

}
