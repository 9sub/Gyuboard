<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fn" uri="jakarta.tags.functions" %>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>게시글 목록</title>
</head>

<body>
<%@include file="../include/header.jsp" %>

<main class="container">
	<h1 class="page-title">게시글 목록</h1>
	<p class="page-desc">검색 조건을 선택해서 원하는 게시글을 찾아보세요.</p>

	<form action="${root }/board/list" method="get" class="search-form">
		<select name="type" class="form-control search-select">
			<option value="all" <c:if test="${empty type or type == 'all'}">selected</c:if>>제목+내용</option>
			<option value="title" <c:if test="${type == 'title'}">selected</c:if>>제목</option>
			<option value="writer" <c:if test="${type == 'writer'}">selected</c:if>>작성자</option>
			<option value="content" <c:if test="${type == 'content'}">selected</c:if>>내용</option>
		</select>

		<input type="text"
			   name="keyword"
			   class="form-control search-input"
			   value="${keyword }"
			   placeholder="검색어를 입력하세요">

		<button type="submit" class="btn btn-primary">검색</button>
		<a href="${root }/board/list" class="btn btn-secondary">초기화</a>
	</form>

	<section class="card table-card">
		<table class="board-table">
			<thead>
				<tr>
					<th>번호</th>
					<th>작성자</th>
					<th>제목</th>
					<th>내용</th>
					<th>작성일</th>
				</tr>
			</thead>

			<tbody>
				<c:choose>
					<c:when test="${empty list }">
						<tr>
							<td colspan="5" class="empty-text">검색 결과가 없습니다.</td>
						</tr>
					</c:when>

					<c:otherwise>
						<c:forEach var="board" items="${list }">
							<tr>
								<td>${board.id }</td>
								<td>${board.writer }</td>
								<td>
									<a href="${root }/board/detail?id=${board.id }" class="board-title-link">
										${board.title }
									</a>
								</td>
								<td class="board-content-preview">${board.guecontents }</td>
								<td>${fn:substring(board.writedate,0,10) }</td>
							</tr>
						</c:forEach>
					</c:otherwise>
				</c:choose>
			</tbody>
		</table>
	</section>
</main>

</body>
</html>