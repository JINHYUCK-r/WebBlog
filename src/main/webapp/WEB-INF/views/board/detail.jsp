<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../layout/header.jsp"%>

<div class="container">

	<button class="btn btn-secondary" onclick="history.back()">돌아가기</button>
	<c:if test="${board.user.id == principal.user.id }">
		<a href="/board/${board.id }/updateForm" class="btn btn-warning">수정</a>
		<button id="btn-delete" class="btn btn-danger">삭제</button>
	</c:if>
	<br /> <br />
	<div>
		글 번호 : <span id="id"><i>${board.id } </i></span> 작성자: <i>${board.user.username} </i>
	</div>
	<br />

	<div>
		<h3>${board.title }</h3>
	</div>
	<hr />
	<div>${board.content }</div>
	<hr />

	<div class="card">
		<form>
		<input type="hidden" id="boardId" value="${ board.id}" />
		<div class="card-body">
			<textarea id="reply-content" class="form-control" row="1"></textarea>
		</div>
		<div class="card-footer">
			<button id="btn-reply-save"class="bnt btn-primary">등록</button>
		</div>
		</form>
	</div>
	<br />
	<div class="card">
		<div class="card-header">댓글 리스트</div>
		<ul id="reply--box" class="list-group">
			<!-- 내가 만든 아이디는 -- 작대기 두개를 그어놓으면 기존에 제공되는 라이브러리랑 구분되기 좋다  -->
			<c:forEach var="reply" items="${board.replys }">
				<li id="reply--1" class="list-group-item d-flex justify-content-between">
					<!--d-flex: class가 블럭속성으로 한줄 전체를차지하고 있는것을 플레스로 바꿈  --> <!--justify-content : 안에 내용들의 좌우정렬 속성 변경   --> <!---between : 양쪽으로 떨어뜨려 놓음   -->
					<div>${reply.content }</div>
					<div class="d-flex">
						<div class="font-italic">작성자: ${reply.user.username } &nbsp;</div>
						<button class="badge">삭제</button>
					</div>
				</li>

			</c:forEach>
		</ul>
	</div>
</div>


<script src="/js/board.js"></script>


<%@ include file="../layout/footer.jsp"%>