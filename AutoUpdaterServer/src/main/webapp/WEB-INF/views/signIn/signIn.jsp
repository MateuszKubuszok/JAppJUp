<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page session="false" %>
<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<title><spring:message code="repository.name" /> - <spring:message code="navigation.signIn" /></title>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<link rel="stylesheet" href="<c:url value="/resources/css/default.css" />" type="text/css" />
</head>
<body>
	<div id="breadcrumbs">
		<p>
			<a href="<c:url value="/api/upload_file" />"><spring:message code="remoteUpload.quickUpload" /></a>
		</p>
	</div>

	<h1><spring:message code="navigation.signIn" /></h1>
	
	<fieldset>
		<legend><spring:message code="navigation.signIn" /></legend>
			<form method="post" action="<c:url value="/j_spring_security_check" />">
			<p>
				<label for="j_username"><spring:message code="model.user.username" />:</label>
				<c:if test="${not empty param.error}"> 
					<span class="error">Wrong credentials</span>
				</c:if>
				<input name="j_username" />
			</p>
			<p>
				<label for="j_password"><spring:message code="model.user.password" />:</label>
				<input name="j_password" type="password" />
			</p>
			<p>
				<label for="_spring_security_remember_me"><spring:message code="navigation.rememberMe" />:</label>
				<input type="checkbox" name="_spring_security_remember_me" />
			</p>
			<p>
				<input name="send" type="submit" value="<spring:message code="navigation.signIn" />" />
			</p>
		</form>
	</fieldset>
</body>
</html>
