<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../layout/header.jsp"%>
<div class="container">

<!--/auth/loginProc 를 만들지 않아도 스프링 시큐리티가 주소를 가로챔   -->
	<form action="/auth/loginProc" method="post" >
		
			<div class="form-group">
			<label for="username">Username</label> 
			<input type="text" name="username"class="form-control" placeholder="Enter username" id="username">
		</div>
	
		<div class="form-group">
			<label for="password">Password</label> 
			<input type="password" name="password" class="form-control" placeholder="Enter password" id="password">
		</div>

		
		<div class="form-group form-check">
			<label class="form-check-label"> 
			<input class="form-check-input" name="remember" type="checkbox"> Remember me
			</label>
		</div>
		
		<button id="btn-login" class="btn btn-primary">로그인</button>
	</form>
</div>

<!-- 전통적 로그인 할때 사용함 
<script src = "/js/user.js"></script>  -->

<%@ include file="../layout/footer.jsp"%>