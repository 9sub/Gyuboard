<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>게시글 수정</title>
</head>
<body>

<%@ include file="../include/header.jsp" %>

<h1>게시글 수정</h1>

<form action="/board/detail/update" method="post">
    <input type="hidden" name="id" value="${board.id}">

    <div>
        <label>작성자</label>
        <input type="text" value="${board.writer}" readonly>
    </div>

    <div>
        <label>제목</label>
        <input type="text" name="title" value="${board.title}">
    </div>

    <div>
        <label>내용</label>
        <textarea name="guecontents" rows="10" cols="50">${board.guecontents}</textarea>
    </div>

    <button type="submit">수정 완료</button>
</form>

<a href="/board/detail?id=${board.id}">취소</a>

</body>
</html>