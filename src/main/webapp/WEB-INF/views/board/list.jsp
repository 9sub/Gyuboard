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
	<p class="page-desc">다른 사용자가 작성한 글을 확인하고 이야기에 참여해보세요.</p>

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
			</tbody>
		</table>
	</section>
</main>

</body>
</html>