package com.rjh.blog.controller.api;
import javax.servlet.http.HttpSession;

// json 데이터를 전달하는 컨트롤러 
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.bind.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.rjh.blog.config.auth.principalDetail;
import com.rjh.blog.dto.ReplySaveRequestDto;
import com.rjh.blog.dto.ResponseDto;
import com.rjh.blog.model.Board;
import com.rjh.blog.model.Reply;
import com.rjh.blog.model.RoleType;
import com.rjh.blog.model.User;
import com.rjh.blog.service.BoardService;
import com.rjh.blog.service.UserService;



@RestController
public class BoardApiController {
	
	@Autowired
	private BoardService boardService;

	@PostMapping("/api/board")
	public ResponseDto<Integer> save(@RequestBody Board board, @AuthenticationPrincipal principalDetail principal) { 
		boardService.write(board, principal.getUser());
		
		return new ResponseDto<Integer>(HttpStatus.OK.value(), 1);
	}
	
	@DeleteMapping("/api/board/{id}")
	public ResponseDto<Integer> deleteById(@PathVariable int id){
		boardService.deleteById(id);
		
		return new ResponseDto<Integer>(HttpStatus.OK.value(), 1);
	}
	
	@PutMapping("/api/board/{id}")
	public ResponseDto<Integer>update(@PathVariable int id, @RequestBody Board board){
		boardService.edit(id, board);
		
		return new ResponseDto<Integer>(HttpStatus.OK.value(), 1);
	}
	
	//데이터를 받을때 컨트롤러에서 dto를 만들어서 받는게 좋다. 장점: 필요한 데이터를 한번에 받을수 있음 
	//dto 클래스를 만들어서 replySave 파라미터에 @RequestBody ReplySaveRequestDto 로 받는게 깔끔하다
	// (@PathVariable int boardId,@RequestBody Reply reply, @AuthenticationPrincipal principalDetail principal) 이거대신 
	//replySave(@RequestBody ReplySaveRequestDto reply) 이렇게 
	//dto를 사용하지 않은 이유는! 
	
	@PostMapping("/api/board/{boardId}/reply")
	public ResponseDto<Integer> replySave(@RequestBody ReplySaveRequestDto replySaveRequestDto) { 
	
		boardService.replyWrite(replySaveRequestDto);
		
		return new ResponseDto<Integer>(HttpStatus.OK.value(), 1);
	}
	
}
