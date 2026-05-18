<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>게시글 쓰기</title>
</head>
<body>

<%@ include file="../include/header.jsp" %>

<form action="/board/write" method="post">
	<div>
		<label>작성자</label>
		<input type="text" name="writer">
	</div>
	<div>
		<label>제목</label>
		<input type="text" name="title">
	</div>
	<div>
		<label>내용</label>
		<textarea name="guecontents" rows="10" cols="50"></textarea>
	</div>
	
	<button type="submit">등록</button>
</form>

</body>
</html>