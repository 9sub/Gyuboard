<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<c:set var="root" value="${pageContext.request.contextPath }" />

<link rel="stylesheet" href="${root}/css/style.css">

<header class="site-header">
	<nav class="navbar">
		<a href="${root}/" class="logo">GUE Board</a>
		
		<div class="nav-menu">
			<a href="${root}/" class="nav-link">홈</a>
			
			<c:choose>
				<c:when test="${empty user }">
					<a href="${root}/member/login" class="nav-link">로그인</a>
					<a href="${root}/member/join" class="nav-button">회원가입</a>
				</c:when>

				<c:otherwise>
					<a href="${root}/board/list" class="nav-link">게시글 목록</a>
					<a href="${root}/board/write" class="nav-button">글쓰기</a>
					<span class="nav-user">${user.name }님</span>
					<a href="${root}/logout" class="nav-link">로그아웃</a>
				</c:otherwise>
			</c:choose>
		</div>
	</nav>
</header>