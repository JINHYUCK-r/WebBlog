package com.rjh.blog.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.rjh.blog.dto.ReplySaveRequestDto;
import com.rjh.blog.model.Board;
import com.rjh.blog.model.Reply;
import com.rjh.blog.model.RoleType;
import com.rjh.blog.model.User;
import com.rjh.blog.repository.BoardRepository;
import com.rjh.blog.repository.ReplyRepository;
import com.rjh.blog.repository.UserRepository;

import lombok.RequiredArgsConstructor;



@Service
@RequiredArgsConstructor //생성자를 만들때 초기화가 필요한애들을 초기화 해줘
public class BoardService {
	
	@Autowired
	private BoardRepository boardRepository;
	//private final BoardRepository boardRepository; @RequiredArgsConstructor 이걸 걸어서 이렇게 적어도됨. final은 초기화가 필요한데 어노테이션이 알아서 해줌
	//이렇게 쓰면 각 항목마다 @Autowired를 쓰지않아도 됨.
	/*
	public BoardService(BoardRepository bRepo) {
		this.boardRepository = bRepo
	}Autowired 한게 이거랑 같은의미 
	*/
	@Autowired
	private ReplyRepository replyRepository;
	@Autowired
	private UserRepository userRepository;

	
	@Transactional 
	public void write(Board board, User user) { //title과 content 를 받음 
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
		Board board =  boardRepository.findById(id)
				.orElseThrow(() ->{
					return new IllegalArgumentException("글 상세보기 실패: 아이디를 찾을수 없습니다. ");
				});
		
		return board;
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
	
	@Transactional
	public void replyWrite(ReplySaveRequestDto replySaveRequestDto) {
		/*
		User user = userRepository.findById(replySaveRequestDto.getUserId())
				.orElseThrow(() ->{
					return new IllegalArgumentException("댓글쓰기실패: 유저 id를 찾을수 없습니다.");
				}); //영속화 

		Board board = boardRepository.findById(replySaveRequestDto.getBoardId())
		.orElseThrow(() ->{
			return new IllegalArgumentException("댓글쓰기실패: 게시글 id를 찾을수 없습니다.");
		}); //영속화 
		
		Reply  reply = Reply.builder()
				.user(user)
				.board(board)
				.content(replySaveRequestDto.getContent())
				.build();
				*/
		
		/*		
		//Reply 모델안에함수를 만들어서 간략화 시킬수도 있다.
		Reply reply = new Reply();
		reply.update(user, board, replySaveRequestDto.getContent());

		System.out.println(reply); //오브젝트를 출력하게되면 자동으로 toString()이 호출됨 
		replyRepository.save(reply);
		*/
		replyRepository.mSave(replySaveRequestDto.getUserId(), replySaveRequestDto.getBoardId(), replySaveRequestDto.getContent());

	}
	@Transactional
	public void replyDelete(int replyId) {
		replyRepository.deleteById(replyId);
	}
	
	@Transactional
	public void bCount(int boardId) {
		boardRepository.bCount(boardId);
	}
	
	

}
