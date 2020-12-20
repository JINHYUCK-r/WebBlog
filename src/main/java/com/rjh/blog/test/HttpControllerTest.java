package com.rjh.blog.test;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.Mapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

//사용자가 요청 -> 응답(Html)
//@Controller

//사용자가 요청 -> 응답(Data)
@RestController
public class HttpControllerTest {
	
	private static final String TAG = "HttpControllerTest : ";
	
	//    localhost:8000/blog//http/lombok  yml파일에서 server설정을 바꾸어주었기때문에 주소이름이 바뀜 
	@GetMapping("/http/lombok")
	public String lombokTest() {
		//Member m= new Member(1,"ssar","123","ssar@nate.com");
		Member m = Member.builder().username("ssar").password("1234").email("ssar@nate.com").build(); 
		// 이렇게 build를 이용해서 만들면 아이디값이 안들어가있어도 자동으로 순서를부여해줌
		//생성자를 새로만들필요도 없고, 생성자 순서를 맞출필요도 없다. 
		System.out.println(TAG+"getter : " + m.getId());
		m.setId(5000);
		System.out.println(TAG+"setter : "+ m.getId());
		
		return "lombok test 완료 ";
	}

	//인터넷 브라우저 요청은 무조건 get
	//http://localhost:8080/http/get (select)
	@GetMapping("/http/get")
	//getTest안에 @RequestParam int id, @RequestParam String username를 쓰면 하나씩 받을수 있다.
	public String getTest(Member m) { //?id=1&username=ssar&password=1234&email=ssar@nate.com   ? 뒤에 쿼리방식으로 전달 , MessageConverter가 대입해줌 
	
		return  "get 요청  : " + m.getId() + ", " +m.getUsername()+", "+ m.getPassword()+", "+m.getEmail();
	}
	
	//인터넷 브라우저에서 확인이 불가능 하기때문에 postman을 이용 
	//http://localhost:8080/http/post(insert)
	//post는 정보를 쿼리가 아닌 바디에 담아서 보냄 
	//x-www는 <input>같은 폼형식 
//	@PostMapping("/http/post")
//	public String postTest(Member m) {
//			
//		return  "post 요청  : " + m.getId() + ", " +m.getUsername()+", "+ m.getPassword()+", "+m.getEmail();
//		}
	
	//raw에서 텍스트를 받기 위해  text/plain 형식
//	@PostMapping("/http/post")
//	public String postTest(@RequestBody String text) {
//			
//		return  "post 요청  : " + text;
//	}
	
	//application/json 데이터를 받기 
	//이렇게 보낼때는 text로 받으면안됨. 
	@PostMapping("/http/post")
	public String postTest(@RequestBody Member m) { //MessageConverter(스프링부트)가 알아서 파싱해서 오브젝트에 넣어줌 
			
		return  "post 요청  : " + m.getId() + ", " +m.getUsername()+", "+ m.getPassword()+", "+m.getEmail();
	}
	
		
	
	
	//http://localhost:8080/http/put(update)
	@PutMapping("/http/put")
	public String putTest(@RequestBody Member m) {
		
		return "put 요청 "  + m.getId() + ", " +m.getUsername()+", "+ m.getPassword()+", "+m.getEmail();

	}
	//http://localhost:8080/http/delete(delete)
	@DeleteMapping("/http/delete")
	public String deleteTest() {
		
		return "delete 요청 ";
	}
	
	
}
