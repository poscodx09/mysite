<%@ taglib uri="jakarta.tags.core" prefix="c"%>
<%@ taglib uri="jakarta.tags.fmt" prefix="fmt"%>
<%@ taglib uri="jakarta.tags.functions" prefix="fn"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<title>mysite03</title>
<meta http-equiv="content-type" content="text/html; charset=utf-8">
<link href="${pageContext.request.contextPath }/assets/css/user.css"
	rel="stylesheet" type="text/css">
<script src="${pageContext.request.contextPath }/assets/js/jquery/jquery-1.9.0.js"></script>
<script>
$(function(){
	console.log("dom loaded");
	var el = $("#btn-check");
	el.click(function() {
		var email = $("#email").val();
		if (email == ""){
			return;
		}
		
		$.ajax({
			url: "${pageContext.request.contextPath }/api/user/checkemail?email=" + email,
			typea: "get",
			dataType: "json",
			success: function(response) {
				if (response.result != "success"){
					console.error(response.message);
					return;
				}
				
				if (response.data.exist){
					alert("해당 이메일이 이미 존재합니다. 다른 이메일을 사용하세요.");
					$("#email").val("");
					$("#email").focus();
					return;
				}
				
				$("#img-check").show();
				$("#btn-check").hide();
			},
			error: function(xhr, status, err){
				console.error(err);
			}
		});
	});
});
</script>
</head>
<body>
	<div id="container">
		<jsp:include page="/WEB-INF/views/includes/header.jsp" />
		<div id="content">
			<div id="user">
				<form:form modelAttribute="userVo" id="join-form" name="joinForm"
					method="post"
					action="${pageContext.request.contextPath }/user/join">
					<label class="block-label" for="name"><spring:message
							code="user.join.label.name" /></label>
					<form:input path="name" />
					<p style="color: red;">
						<form:errors path="name" />
					</p>

					<label class="block-label" for="email"><spring:message
							code="user.join.label.email" /></label>
					<form:input path="email" />
					<spring:message code="user.join.label.email.check"
						var="userSignupEmailCheck" />
					<input id="btn-check" type="button" value="${userSignupEmailCheck }" method="get"
						action="${pageContext.request.contextPath }/user?a=duplication" style="display:;">
					<img id="img-check" src="${pageContext.request.contextPath }/assets/images/check.png" style="vertical-align:bottom; width:24px; display:none;" />
					<p style="color: red;">
						<form:errors path="email" />
					</p>
					
					<label class="block-label"><spring:message
							code="user.join.label.password" /></label>
					<form:password path="password" />
					<p style="color: red;">
						<form:errors path="password" />
					</p>

					<fieldset>
						<legend>
							<spring:message code="user.join.label.gender" />
						</legend>
						<form:radiobutton path="gender" value="female" checked="checked"/>
						<label><spring:message
								code="user.join.label.gender.female" /></label>
						<form:radiobutton path="gender" value="male"/>
						<label><spring:message
								code="user.join.label.gender.male" /></label>
					</fieldset>

					<fieldset>
						<legend>
							<spring:message code="user.join.label.terms" />
						</legend>
						<form:checkbox path="agree" />
						<label><spring:message
								code="user.join.label.terms.message" /></label>
						<p style="color: red;">
        					<form:errors path="agree" />
    					</p>
					</fieldset>
					
					<spring:message code="user.join.button.signup"
						var="userSignupButtonText" />
					<input type="submit" value="${userSignupButtonText }" />

				</form:form>
			</div>
		</div>
		<jsp:include page="/WEB-INF/views/includes/navigation.jsp" />
		<jsp:include page="/WEB-INF/views/includes/footer.jsp" />
	</div>
</body>
</html>