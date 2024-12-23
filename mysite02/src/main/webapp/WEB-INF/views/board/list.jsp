<%@ taglib uri="jakarta.tags.core" prefix="c"%>
<%@ taglib uri="jakarta.tags.fmt" prefix="fmt"%>
<%@ taglib uri="jakarta.tags.functions" prefix="fn"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<title>mysite</title>
<meta http-equiv="content-type" content="text/html; charset=utf-8">
<link href="${pageContext.request.contextPath }/assets/css/board.css" rel="stylesheet" type="text/css">
</head>
<body>
	<div id="container">
		<c:import url="/WEB-INF/views/includes/header.jsp"/>
		<div id="content">
			<div id="board">
				<form id="search_form" action="" method="post">
					<input type="text" id="kwd" name="kwd" value="">
					<input type="submit" value="찾기">
				</form>
				<table class="tbl-ex">
					<tr>
						<th>번호</th>
						<th>제목</th>
						<th>글쓴이</th>
						<th>조회수</th>
						<th>작성일</th>
						<th>&nbsp;</th>
					</tr>
					<c:forEach items="${boardList }" var="vo" varStatus="status">
					<tr>
						<td>3</td>
						<td style="text-align:left; padding-left:${vo.depth * 20}px">
						<a href="${pageContext.request.contextPath}/board?a=view&pId=${vo.id}&gId=${vo.groupNo}&oId=${vo.orderNo}&depth=${vo.depth}">${vo.title }</a></td>
						<td>${vo.writerName }</td>
						<td>${vo.id}</td>
						<td>${vo.regDate }</td>
						<td><a href="${pageContext.request.contextPath}/board?a=delete&pId=${vo.id}" class="del">삭제</a></td>
					</tr>
					</c:forEach>
				</table>
				
				<!-- pager 추가 -->
				<c:set var="totalPages" value="${fn:length(boardList) }" />
				<c:set var="currentPage" value="${request.getAttribute('page')}" />
				<c:set var="beginPage" value="${request.getAttribute('page')}" />
				<c:set var="endPage" value="${request.getAttribute('page')}" />
				<div class="pager">
					<ul>
						<li><a href="${pageContext.request.contextPath}/board?a=list&page=">◀</a></li>
						<li><a href="">1</a></li>
						<li class="selected">2</li>
						<li><a href="">3</a></li>
						<li>4</li>
						<li>5</li>
						<li><a href="">▶</a></li>
					</ul>
				</div>					
				<!-- pager 추가 -->

				<div class="bottom">
					<a href="${pageContext.request.contextPath}/board?a=writeform" id="new-book">글쓰기</a>
				</div>				
			</div>
		</div>
		<c:import url="/WEB-INF/views/includes/navigation.jsp"/>
		<c:import url="/WEB-INF/views/includes/footer.jsp"/>
	</div>
</body>
</html>