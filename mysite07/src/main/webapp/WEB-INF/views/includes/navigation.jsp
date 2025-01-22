<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<div id="navigation">
	<ul>
		<li><a href="${pageContext.request.contextPath}"><spring:message code="navigation.li.main" /></a></li>
		<li><a href="${pageContext.request.contextPath}/guestbook/list"><spring:message code="navigation.li.guestbook" /></a></li>
		<li><a href="${pageContext.request.contextPath}/board/list"><spring:message code="navigation.li.board" /></a></li>
		<li><a href="${pageContext.request.contextPath}/gallery"><spring:message code="navigation.li.gallery" /></a></li>
	</ul>
</div>