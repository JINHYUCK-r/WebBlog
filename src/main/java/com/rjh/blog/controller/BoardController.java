package com.rjh.blog.controller;
//html을 전달하는 컨트롤러 


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import com.rjh.blog.config.auth.principalDetail;
import com.rjh.blog.model.Board;
import com.rjh.blog.service.BoardService;

@Controller  //return 할때 viewResolver 작동. 해당페이지로 model의 정보를 가지고 이동. 그리고 리턴값 앞뒤로 prefix,suffix를 붙여줌 
public class BoardController {
	
	@Autowired
	private BoardService boardService;

	@GetMapping({"","/"})
	//@AuthenticationPrincipal : security core로 임포트.  
	//@AuthenticationPrincipal principalDetail principal 이것을 파람에 넣어서  세션에 접근가능 	
	public String index(Model model, @PageableDefault(size = 3, sort="id", direction = Sort.Direction.DESC) Pageable pageable ) { 
		// /WEB-INF/views/index.jsp 로 찾게됨 
		//System.out.println("로그인 사용자 아이디: "+ principal.getUsername());
		model.addAttribute("boards", boardService.readList(pageable)); //readList에서 읽어진 정보들이 boards 라는 이름으로 담겨서 넘어감 
		return "index";
	}
	
	//USER 권한 필요
	@GetMapping("/board/saveForm")
	public String saveForm() {
		return "board/saveForm";
	}
	
	@GetMapping("/board/{id}")
	public String findById(@PathVariable int id, Model model) {
		boardService.bCount(id);
		model.addAttribute("board", boardService.readDetail(id));
		
		return "board/detail";
		
	}
	
	@GetMapping("/board/{id}/updateForm")
	public String updateForm(@PathVariable int id, Model model) {
		model.addAttribute("board", boardService.readDetail(id));
		return "/board/updateForm";
	}
	
}
