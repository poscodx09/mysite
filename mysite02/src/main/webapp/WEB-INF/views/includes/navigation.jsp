<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<div id="navigation">
	<ul>
		<li><a href="${pageContext.request.contextPath}">${authUser.name }</a></li>
		<li><a href="${pageContext.request.contextPath}/guestbook">방명록</a></li>
		<li><a href="${pageContext.request.contextPath}/board?a=list&page=1">게시판</a></li>
	</ul>
</div>