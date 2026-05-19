<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<html>
<head>
<meta charset="UTF-8">
<title>회원가입 화면</title>
</head>
<body>
	
	<%@include file="../include/header.jsp" %>
	
	<h1>회원가입</h1>
	
	<c:if test="${not empty msg }">
		<p style="color:red;">${msg }</p>
	</c:if>
	
	<form action="${root }/member/join" method="post">
		<div>
			<label>아이디</label>
			<input type="text" name="writer" required>
		</div>	

		<div>
			<label>비밀번호</label>
			<input type="password" name="password" required>
		</div>	

		<div>
			<label>이름</label>
			<input type="text" name="name" required>
		</div>

		<div>
			<label>이메일</label>
			<input type="email" name="email">
		</div>
		
		<button type="submit">가입하기</button>
	</form>
	
</body>
</html>