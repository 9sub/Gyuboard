<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<html>
<head>
<meta charset="UTF-8">
<title>회원가입</title>
</head>
<body>
	
	<%@include file="../include/header.jsp" %>
	
	<main class="auth-wrap">
		<section class="auth-card">
			<h1 class="auth-title">회원가입</h1>
			<p class="auth-subtitle">간단한 정보만 입력하면 바로 게시판을 사용할 수 있어요.</p>
			
			<c:if test="${not empty msg }">
				<div class="alert">${msg }</div>
			</c:if>
			
			<form action="${root }/member/join" method="post">
				<div class="form-group">
					<label>아이디</label>
					<input type="text" name="writer" class="form-control" placeholder="사용할 아이디" required>
				</div>	

				<div class="form-group">
					<label>비밀번호</label>
					<input type="password" name="password" class="form-control" placeholder="비밀번호" required>
				</div>	

				<div class="form-group">
					<label>이름</label>
					<input type="text" name="name" class="form-control" placeholder="이름" required>
				</div>

				<div class="form-group">
					<label>이메일</label>
					<input type="email" name="email" class="form-control" placeholder="example@email.com">
				</div>
				
				<button type="submit" class="btn btn-primary btn-full">가입하기</button>
			</form>

			<p class="auth-footer">
				이미 계정이 있으신가요?
				<a href="${root }/member/login">로그인</a>
			</p>
		</section>
	</main>
	
</body>
</html>