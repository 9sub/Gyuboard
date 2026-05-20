<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fn" uri="jakarta.tags.functions" %>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>게시글 상세</title>
</head>
<body>
	<%@ include file="../include/header.jsp" %>
	
	<main class="container">
		<h1 class="page-title">게시글 상세</h1>
		<p class="page-desc">게시글 내용과 댓글을 확인할 수 있어요.</p>

		<section class="card">
			<div class="detail-grid">
				<div class="detail-row">
					<div class="detail-label">번호</div>
					<div class="detail-value">${board.id }</div>
				</div>

				<div class="detail-row">
					<div class="detail-label">작성자</div>
					<div class="detail-value">${board.writer }</div>
				</div>

				<div class="detail-row">
					<div class="detail-label">제목</div>
					<div class="detail-value">${board.title }</div>
				</div>

				<div class="detail-row">
					<div class="detail-label">내용</div>
					<div class="detail-value">${board.guecontents }</div>
				</div>

				<div class="detail-row">
					<div class="detail-label">작성일</div>
					<div class="detail-value">${fn:substring(board.writedate,0,10) }</div>
				</div>
				
				<div class="detail-row">
					<div class="detail-label">조회수</div>
					<div class="detail-value">${board.viewCount }</div>
				</div>
			</div>
			
			<div class="action-row">
				<a href="${root }/board/list" class="btn btn-secondary">목록</a>
				
				<c:if test="${not empty user and user.writer eq board.writer }">
					<a href="${root }/board/detail/update?id=${board.id }" class="btn btn-primary">수정</a>

					<form action="${root }/board/detail/delete" method="post" style="display:inline;">
						<input type="hidden" name="id" value="${board.id }">
						<button type="submit" class="btn btn-danger">삭제</button>
					</form>
				</c:if>
			</div>
		</section>

		<section class="card comment-box">
			<h2 class="page-title" style="font-size:24px;">댓글</h2>

			<c:if test="${not empty user}">
			    <form action="${root }/board/detail/write" method="post">
			        <input type="hidden" name="boardId" value="${board.id}">

					<div class="form-group">
			        	<textarea name="content" class="form-control" placeholder="댓글을 입력하세요"></textarea>
					</div>

			        <button type="submit" class="btn btn-primary">댓글 등록</button>
			    </form>
			</c:if>

			<c:if test="${empty user}">
			    <p class="empty-text">댓글을 작성하려면 로그인하세요.</p>
			</c:if>

			<div>
				<c:forEach var="comment" items="${comments }">
				    <div class="comment-item">
						<div class="comment-meta">
					        <strong class="comment-writer">${comment.writer}</strong>
					        <span class="comment-date">${fn:substring(board.writedate,0,10) } ${fn:substring(board.writedate,11,16) }</span> 
						</div>

				        <p>${comment.content}</p>
				    </div>
				</c:forEach>
			</div>
		</section>
	</main>
	
</body>
</html>