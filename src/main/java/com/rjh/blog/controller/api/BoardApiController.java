package com.rjh.blog.controller.api;
import javax.servlet.http.HttpSession;

// json 데이터를 전달하는 컨트롤러 
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.bind.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.rjh.blog.config.auth.principalDetail;
import com.rjh.blog.dto.ResponseDto;
import com.rjh.blog.model.Board;
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
		boardService.Write(board, principal.getUser());
		
		return new ResponseDto<Integer>(HttpStatus.OK.value(), 1);
	}
	
}
