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
	
	@Transactional(readOnly = true)
	public Page<Board> readList(Pageable pageable ){ //page를 리턴해서 page에 들어있는 정보를 활용 
		return boardRepository.findAll(pageable);
	}
	
	@Transactional(readOnly = true)
	public Board readDetail(int id) {
		return boardRepository.findById(id)
				.orElseThrow(() ->{
					return new IllegalArgumentException("글 상세보기 실패: 아이디를 찾을수 없습니다. ");
				});
	}
	
	@Transactional
	public void deleteById(int id) {
		 boardRepository.deleteById(id);
	}
	
	@Transactional
	public void edit(int id, Board requestBoard) {
		Board board = boardRepository.findById(id) //데이터베이스에 있는 보드 내용이 동기화 되어 있음 
		.orElseThrow(() ->{
			return new IllegalArgumentException("글 찾기 실패: 아이디를 찾을수 없습니다. ");
		}); //영속화 완료 
		board.setTitle(requestBoard.getTitle());
		board.setContent(requestBoard.getContent());
		// 해당 함수가 종료시 (서비스가 종료될때 ) 트랜잭션이 종료됨, 이때 더티체킹이 일어남. - 자동업데이트(flush)
	}

}