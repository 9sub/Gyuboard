<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>게시글 수정</title>
</head>
<body>

<%@ include file="../include/header.jsp" %>

<main class="container">
	<h1 class="page-title">게시글 수정</h1>
	<p class="page-desc">작성한 내용을 다시 다듬어보세요.</p>

	<section class="card">
		<form action="${root }/board/detail/update" method="post">
		    <input type="hidden" name="id" value="${board.id}">

		    <div class="form-group">
		        <label>작성자</label>
		        <input type="text" class="form-control" value="${board.writer}" readonly>
		    </div>

		    <div class="form-group">
		        <label>제목</label>
		        <input type="text" name="title" class="form-control" value="${board.title}">
		    </div>

		    <div class="form-group">
		        <label>내용</label>
		        <textarea name="guecontents" class="form-control">${board.guecontents}</textarea>
		    </div>

			<div class="action-row">
			    <button type="submit" class="btn btn-primary">수정 완료</button>
			    <a href="${root }/board/detail?id=${board.id}" class="btn btn-secondary">취소</a>
			</div>
		</form>
	</section>
</main>

</body>
</html>