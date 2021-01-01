package com.rjh.blog.test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.rjh.blog.model.Board;
import com.rjh.blog.repository.BoardRepository;

@RestController
public class ReplyControllerTest {
	
	@Autowired
	private BoardRepository boardRepository;
	
	@GetMapping("/test/blog/{id}")
	public Board getBoard(@PathVariable int id) {
		return boardRepository.findById(id).get(); //jakson라이브러리 (오브젝트를 json으로 리턴) -> 모델의 getter를 호출 
		 //board 모델 안에 있는 replys가 호출되고 replys안에 있는 board가 호출되는 무한 참조가 발생됨 
		//모델안에 @JsonIgnoreProperties({"board"}) 를 적어주면 무한참조 방지함 
	}
}
