package com.rjh.blog.model;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;

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

	@ColumnDefault("0")
	private int count; //조회수 
	

	//private int userId; //ORM에서는 이렇게 적고 조인해서 사용하지 않음
	
	@ManyToOne //연관관계. Board가 Many 고 User 가 One 
	//한명의 유저는 여러개의 게시글을 쓸수 있음 
	@JoinColumn(name="userId") //userId라는 필드값으로 생성됨 
	private User user; //DB는 오브젝트를 저장할수 없다. FK(Foreign key)를 사용. 자바는 오브젝트를 저장할수 있다. 자바가 DB에 맞춰야함
	//User 객체를 참조하여 데이터베이스에서 만들어 질때는 FK를 생성 
	
	@CreationTimestamp
	private Timestamp createDate;

}
