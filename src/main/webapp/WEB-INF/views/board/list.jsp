<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    

<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fn" uri="jakarta.tags.functions" %>


<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>게시글 목록</title>
<style>

    table {

        border-collapse: collapse;

        width: 80%;

    }

    th, td {

        border: 1px solid black;

        padding: 8px;

        text-align: center;

    }

    th {

        background-color: #eee;

    }

</style>

</head>

<body>
<%@include file="../include/header.jsp" %>

<h1>게시글 목록</h1>

<table>
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
					<a href="/board/detail?id=${board.id }">
						${board.title }
					</a>
				</td>
				<td>${board.guecontents }</td>
				<td>${fn:substring(board.writedate,0,10) }</td>
			</tr>
		</c:forEach>
	</tbody>
	
	
</table>

</body>
</html>