package com.rjh.blog.test;

import java.util.List;
import java.util.function.Supplier;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.rjh.blog.model.RoleType;
import com.rjh.blog.model.User;
import com.rjh.blog.repository.UserRepository;

@RestController //html이 아닌 data를 리턴 
public class DummyControllerTest {

	//의존성주입(DI)
	@Autowired //DummyControllerTest가 메모리에 뜰때 userRepository도 같이 뜸 
	//UserRepository 형태로 spring이 관리하고 있는게 있다면 userRepository에 넣어라.
	//UserRepository는 JpaRepository에 의해 스프링이 실행될때 스캔되어 생성되어져있음 
	private UserRepository userRepository;
	
	//http://localhost:8000/blog/dummy/join(요청)
	//http의 body에 username, password, email 데이터를 가지고 요청 
	@PostMapping("/dummy/join")
	//public String join(String username, String password, String email) { //key=value 형태의 값을 받아줌 (약속된 규칙. x-www-form형태로 전송되는 형태는 함수의 파라미터로 파싱해서 넣어줌  )
	//System.out.println("username: " + username);
	public String join(User user) { //오브젝트 형태로 받을수도 있음 	 //DB insert
		System.out.println("id: " + user.getId());
		System.out.println("username: " + user.getUsername());
		System.out.println("password: " + user.getPassword());
		System.out.println("email: " + user.getEmail());
		System.out.println("role: " + user.getRole());
		System.out.println("creatDate: " + user.getCreateDate());
		
		//user.setRole("user"); string으로 못쓰도록 제한을 걸어놨기때문에 오류발생 
		user.setRole(RoleType.USER);
		userRepository.save(user);
		return "회원가입이 완료 되었습니다.";
	}
	
	//{id} 주로 파라미터를 전달받을수 있음   
	//http://localhost:8000/blog/dummy/user/3
	@GetMapping("/dummy/user/{id}")
	public User detail(@PathVariable int id) { //DB select 
		//userRepository.findById(id); 반환타입이 옵셔널. / .get()-null이 가능성이 없을때 값을 뽑아옴 / .orElseGet(new Supplier<User>) @override해서 get을 만 - null이면 null값이 들어간 객체하나 만들어줘. 
		//null이면 오류 알림 
		User user = userRepository.findById(id).orElseThrow(new Supplier<IllegalArgumentException>() {
		@Override
		 public IllegalArgumentException get() {
			return new IllegalArgumentException("해당 유저는 없습니다. id: "+id);
		}
		});
		//람다식 표현:  49-54 번줄 코드 간략화 
		//User user = userRepository.findById(id).orElseThrow(()->{
		//return new IllegalArgumentException("해당사용자가 없습니다.");
		//});
		
		//요청 : 웹브라우저 
		//user 객체 = 자바 오브젝트 
		//변환 (웹브라우저가 이해할수있는 데이터) -> json (gson라이브러리)
		//스프링부트 = MessageConverter 가 응답시에 자동 작동
		// 자바 오브젝트를 리턴하게 되면  MessageConverter가 Jackson 라이브러리를 호출해서 user오브젝트를 json으로 브라우저에게 전달해줌 
		return user;
	}
	
	//http://localhost:8000/blog/dummy/user/
	@GetMapping("/dummy/users") //모든 유저정보받아오기 
	public List<User> list(){
		return userRepository.findAll();
		
	}
	
	// 한페이지당 2건에 유저 데이터를 리턴 //paging
	// http://localhost:8000/blog/dummy/user?page=0  첫페이지 
	@GetMapping("dummy/user")
	public List<User> pageList(@PageableDefault(size=2,sort="id", direction = Sort.Direction.DESC) Pageable pageable){
													//페이징 전략: 2개씩, id를 기준으로 정렬, 최신순으로 
		Page<User> pagingUsers = userRepository.findAll(pageable); //세세한 정보들이 들어있음 
		List<User> users = pagingUsers.getContent();
		return users;
		
	}
	
	//email, password를 받아서 수정  //DB update
	@PutMapping("/dummy/user/{id}") //주소가 detail이랑 같지만 요청하는 방법이 다르기때문에 구분됨 
	@Transactional //Transactional을 쓰면 save를 사용하지 않아도됨.  함수 종료시에 자동으로 commit이 됨/.commit이 될때 영속화 된 변경된 값이 새로 적용됨   
	public User updateUser(@PathVariable int id, @RequestBody User requestUser) { // @RequestBody가 json을 받아서 java로 변환해서 받음 (MessageConverter의 Jackson 라이브러리)
		System.out.println("id: " + id);
		System.out.println("password: " + requestUser.getPassword());
		System.out.println("email: " + requestUser.getEmail());
		
		 
		User user = userRepository.findById(id).orElseThrow(()->{
			return new IllegalArgumentException("수정에 실패하였습니다.");
		});
		user.setPassword(requestUser.getPassword());
		user.setEmail(requestUser.getEmail());
		//userRepository.save(user); //save 는 주로 Insert 할때 쓰기때문에 update할때 쓰면 값이 변경되지 않는 것은 null로 입력될수 있기때문에 쓰고 싶다면 user정보를 모두 불러와 입력부분만 변경하고 유저정보를 다시 넣어주어야함.
		//save 함수는 id를 전달하지 않으면 insert, id를 전달하여 해당 id에 데이터가 있으면 update, 해당 id에 대한 데이터가 없으면 insert
		
		
		//더티 체킹
		
		return user;
		
	}
	
	@DeleteMapping("/dummy/user/{id}") //DB delete
	public String delete(@PathVariable int id) { 
	try {
		userRepository.deleteById(id);
		}catch(EmptyResultDataAccessException e) { //EmptyResultDataAccessException 말고 그냥 Exception을 걸어줘도 되지만 원하는 Exception이 아닐때도 걸릴수도 있다.
			return "삭제에 실패하였습니다. 해당 id는 DB에 없습니다.";
		}
		return "삭제되었습니다. id:" +id ;
		
	}
	
}
