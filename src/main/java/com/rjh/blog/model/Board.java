package com.rjh.blog.model;

import java.sql.Timestamp;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class Board {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id; 
	
	@Column(nullable = false, length=100)
	private String title; //제목 
	
	
	@Lob //대용량 데이터에 사용 
	private String content; //내용 .  섬머노트 라이브러리 사용 <html>태그가 섞여서 디자인이 됨 

	//@ColumnDefault("0")
	private int count; //조회수 
	

	//private int userId; //ORM에서는 이렇게 적고 조인해서 사용하지 않음
	
	@ManyToOne//연관관계. Board가 Many 고 User 가 One . 기본 fetch전략이 eager-> board가 사용될때 같이 불러와짐. 
	//한명의 유저는 여러개의 게시글을 쓸수 있음 
	@JoinColumn(name="userId") //userId라는 필드값으로 생성됨 
	private User user; //DB는 오브젝트를 저장할수 없다. FK(Foreign key)를 사용. 자바는 오브젝트를 저장할수 있다. 자바가 DB에 맞춰야함
	//User 객체를 참조하여 데이터베이스에서 만들어 질때는 FK를 생성 
	
	//board를 select 할때 reply불러와서 조인시켜 보여주기위해서 
	//reply 는 하나가 하니기 때문에 리스트형태로 만들어줌
	//@JoinColumn(name="replyId") fk가 필요없음. 데이터베이스에 만들어지면 안됨. 왜냐하면 하나의 board에 여러개의 댓글이 참조되어  원자성을 위배하기때문  
	@OneToMany(mappedBy = "board",fetch = FetchType.EAGER) //Onetomany:하나의 게시글에는 여러개의 댓글이 달리기 때문 . 기본fetch전략이 lazy임(필요할때 들고올게). eager로 하는 이유는 위에 user과 content처럼 select할때 한번에 읽어오기 위해  
	// mappedBy: 연관관계의주인이 아니다(fk가 아님 ).  db에 컬럼을 만들지 마라  Reply테이블에 있는 board를 가져와라 (이게 fk키가 된다.)
	//selete 할때 join을 통해 값만 얻기위함. 
	private  List<Reply> reply;
	
	@CreationTimestamp
	private Timestamp createDate;

}
