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
					<c:set var="count" value="${fn:length(boardList) }" />
					<c:forEach items="${boardList }" var="vo" varStatus="status">
					<tr>
						<td>${totalPosts - (page - 1) * 10 - status.index}</td>
						<td style="text-align:left; padding-left:${vo.depth * 20}px">
						<c:if test='${vo.depth > 0}'>
						<img src="${pageContext.request.contextPath}/assets/images/reply.png" alt="reply"/>
						</c:if>
						<a href="${pageContext.request.contextPath}/board?a=view&page=${page }&pId=${vo.id }">${vo.title }</a></td>
						<td>${vo.writerName }</td>
						<td>${vo.hit}</td>
						<td>${vo.regDate }</td>
						<td>
						<c:if test='${authUser.id == vo.userId}'>
						<a href="${pageContext.request.contextPath}/board?a=delete&pId=${vo.id}" class="del">삭제</a></td>
						</c:if>
					</tr>
					</c:forEach>
				</table>
				
				<!-- pager 추가 -->
				<c:set var="currentPage" value="${page }" />
				<c:set var="beginPage" value="${beginPage }" />
				<c:set var="endPage" value="${endPage }" />
				<div class="pager">
					<ul>
					<li>
					<a href="${pageContext.request.contextPath}/board?a=list&page=${currentPage - 1}" ${currentPage == 1 ? 'style="pointer-events:none;opacity:0.5;"' : ''}>◀</a>
					</li>
					 <c:forEach var="i" begin="${beginPage}" end="${endPage}">
					 	<c:choose>
							<c:when test="${i == currentPage}">
								<li class="selected">${i}</li>
							</c:when>
							<c:otherwise>
								<li>
	                			<a href="${pageContext.request.contextPath}/board?a=list&page=${i}">${i}</a>
	            				</li>
							</c:otherwise>
						</c:choose>
        			</c:forEach>
        			<li>
        			<a href="${pageContext.request.contextPath}/board?a=list&page=${currentPage + 1}" ${currentPage == totalPages ? 'style="pointer-events:none;opacity:0.5;"' : ''}>▶</a>
        			</li>
					</ul>
				</div>					
				<!-- pager 추가 -->

				<c:if test="${not empty authUser }">
					<div class="bottom">
						<a href="${pageContext.request.contextPath}/board?a=writeform&page=${currentPage }" id="new-book">글쓰기</a>
					</div>
				</c:if>
			</div>
		</div>
		<c:import url="/WEB-INF/views/includes/navigation.jsp"/>
		<c:import url="/WEB-INF/views/includes/footer.jsp"/>
	</div>
</body>
</html>