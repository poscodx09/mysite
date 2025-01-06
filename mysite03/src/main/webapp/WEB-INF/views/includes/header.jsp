<%@ taglib uri="jakarta.tags.core" prefix="c"%>
<%@ taglib uri="jakarta.tags.fmt" prefix="fmt"%>
<%@ taglib uri="jakarta.tags.functions" prefix="fn"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<div id="header">
	<h1>${siteTitle }</h1>
	<ul>
		<c:choose>
			<c:when test="${empty authUser }" >
				<li><a href="${pageContext.request.contextPath }/user/login"><spring:message code="header.gnb.login" /></a></li>
				<li><a href="${pageContext.request.contextPath }/user/join"><spring:message code="header.gnb.join" /></a></li>
			</c:when>
			<c:otherwise>			
				<li><a href="${pageContext.request.contextPath }/user/update"><spring:message code="header.gnb.settings" /></a></li>
				<li><a href="${pageContext.request.contextPath }/user/logout"><spring:message code="header.gnb.logout" /></a></li>
				<li>${authUser.name }<spring:message code="header.gnb.greeting" /></li>
			</c:otherwise>
		</c:choose>	
	</ul>
</div>