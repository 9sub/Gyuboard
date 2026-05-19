<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
 
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>GUE Board</title>
</head>
<body>
	<%@ include file="include/header.jsp" %>

	<main class="hero">
		<section class="hero-card">
			<h1>깔끔하게 기록하고<br>가볍게 소통하세요.</h1>
			<p>
				GUE Board는 게시글과 댓글을 통해 생각을 공유하는 심플한 게시판입니다.
				로그인 후 글을 작성하고 다른 사람의 이야기에 참여해보세요.
			</p>

			<div class="action-row" style="justify-content:center;">
				<c:choose>
					<c:when test="${empty user }">
						<a href="${root }/member/login" class="btn btn-primary">로그인하고 시작하기</a>
						<a href="${root }/member/join" class="btn btn-secondary">회원가입</a>
					</c:when>

					<c:otherwise>
						<a href="${root }/board/list" class="btn btn-primary">게시글 보러가기</a>
						<a href="${root }/board/write" class="btn btn-secondary">글쓰기</a>
					</c:otherwise>
				</c:choose>
			</div>
		</section>
	</main>
</body>
</html>