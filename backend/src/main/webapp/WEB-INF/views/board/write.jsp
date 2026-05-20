<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>게시글 쓰기</title>
</head>
<body>

<%@ include file="../include/header.jsp" %>

<main class="container">
	<h1 class="page-title">새 글 작성</h1>
	<p class="page-desc">공유하고 싶은 내용을 자유롭게 작성해보세요.</p>

	<section class="card">
		<form action="${root }/board/write" method="post">
			<div class="form-group">
				<label>작성자</label>
				<input type="text" name="writer" class="form-control" value="${user.writer }" readonly>
			</div>

			<div class="form-group">
				<label>제목</label>
				<input type="text" name="title" class="form-control" placeholder="제목을 입력하세요">
			</div>

			<div class="form-group">
				<label>내용</label>
				<textarea name="guecontents" class="form-control" placeholder="내용을 입력하세요"></textarea>
			</div>
			
			<div class="action-row">
				<button type="submit" class="btn btn-primary">등록</button>
				<a href="${root }/board/list" class="btn btn-secondary">목록으로</a>
			</div>
		</form>
	</section>
</main>

</body>
</html>