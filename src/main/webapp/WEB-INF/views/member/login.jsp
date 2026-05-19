<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<html>
<head>
<meta charset="UTF-8">
<title>로그인</title>
</head>
<body>
	
	<%@include file="../include/header.jsp" %>
	
	<main class="auth-wrap">
		<section class="auth-card">
			<h1 class="auth-title">로그인</h1>
			<p class="auth-subtitle">계정으로 로그인하고 게시글과 댓글을 작성해보세요.</p>
			
			<c:if test="${not empty msg }">
				<div class="alert">${msg }</div>
			</c:if>
			
			<form action="${root }/member/login" method="post">
				<div class="form-group">
					<label>아이디</label>
					<input type="text" name="writer" class="form-control" placeholder="아이디를 입력하세요">
				</div>	

				<div class="form-group">
					<label>비밀번호</label>
					<input type="password" name="password" class="form-control" placeholder="비밀번호를 입력하세요">
				</div>	
				
				<button type="submit" class="btn btn-primary btn-full">로그인</button>
			</form>
			
			<p class="auth-footer">
				아직 회원이 아니신가요?
				<a href="${root }/member/join">회원가입</a>
			</p>
		</section>
	</main>
	
</body>
</html>