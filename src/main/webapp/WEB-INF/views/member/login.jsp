<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<html>
<head>
<meta charset="UTF-8">
<title>로그인 화면</title>
</head>
<body>
	
	<%@include file="../include/header.jsp" %>
	
	<h1>로그인</h1>
	
	<c:if test="${not empty msg }">
		<p style="color:red;">${msg }</p>
	</c:if>
	
	<form action="/member/login" method="post">
		<div>
			<label>아이디</label>
			<input type="text" name="writer">
		</div>	
		<div>
			<label>비밀번호</label>
			<input type="password" name="password">
		</div>	
		
		<button type="submit">로그인</button>
	</form>
	
</body>
</html>