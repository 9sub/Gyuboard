<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>게시글 상</title>
</head>
<body>
	<%@ include file="../include/header.jsp" %>
	
	<h1>게시글 상세</h1>
	
	<table border="1">
		<tr>
			<th>번호</th>
			<th>${board.id }</th>
		</tr>
		<tr>
			<th>작성자</th>
			<th>${board.writer }</th>
		</tr>
		<tr>
			<th>제목</th>
			<th>${board.title }</th>
		</tr>
		<tr>
			<th>내용</th>
			<th>${board.guecontents }</th>
		</tr>
		<tr>
			<th>작성일</th>
			<th>${board.writedate }</th>
		</tr>
	</table>
	
	<br>
	
	<a href="/board/list">목록</a>
	
	<hr>
	
	<c:if test="${not empty user and user.writer eq board.writer }">
		<a href="/board/detail/update?id=${board.id }">수정</a>
		<form action="/board/detail/delete" method="post" style="display:inline;">
			<input type="hidden" name="id" value="${board.id }">
			<button type="submit">삭제</button>
		</form>
	</c:if>
	
	<hr>

<h2>댓글</h2>

<c:if test="${not empty user}">

    <form action="/board/detail/write" method="post">

        <input type="hidden" name="boardId" value="${board.id}">

        <textarea name="content" rows="3" cols="50"></textarea>

        <button type="submit">댓글 등록</button>

    </form>

</c:if>

<c:if test="${empty user}">

    <p>댓글을 작성하려면 로그인하세요.</p>

</c:if>

<hr>

<c:forEach var="comment" items="${comments }">

    <div style="border-bottom:1px solid #ccc; padding:10px;">

        <strong>${comment.writer}</strong>

        <span>${comment.writedate}</span> 

        <p>${comment.content}</p>

    </div>

</c:forEach>
	
</body>
</html>