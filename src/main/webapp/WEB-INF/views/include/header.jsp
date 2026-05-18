<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<c:set var="root" value="${pageContext.request.contextPath }" />    
    
<header>
	<nav>
		<a href="/">홈</a>
		
		<c:choose>
			<c:when test="${empty user }">
				<a href="/member/login">로그인</a>
			</c:when>
			<c:otherwise>
				<a href="${root }/board/list">게시글 목록</a>
				<a href="${root }/board/write">글쓰기</a>
				<span>${user.name }님</span>
				<a href="/logout">로그아웃</a>
			</c:otherwise>
		</c:choose>
		
	</nav>
</header>