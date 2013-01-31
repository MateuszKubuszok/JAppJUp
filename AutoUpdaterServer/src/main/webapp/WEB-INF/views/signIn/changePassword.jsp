<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<title><spring:message code="navigation.changepw" /></title>
	<link rel="stylesheet" href="<c:url value="/resources/css/default.css" />" type="text/css" />
</head>
<body>
	<div id="breadcrumbs">
		<p>
			<a href="<c:url value="/logout" />"><spring:message code="navigation.logout" /></a>
			| <a href="<c:url value="/users" />"><spring:message code="navigation.users" /></a>
			| <a href="<c:url value="/programs" />"><spring:message code="navigation.programs" /></a>
		</p>
	</div>
 
	<h1><spring:message code="navigation.changepw" /></h1>
	 
	<fieldset>
		<legend><spring:message code="navigation.changepw" /></legend>
		<form:form modelAttribute="passwordEditionCommand" method="POST" commandName="passwordEditionCommand">
			<form:hidden path="userId" />
			<p>
				<form:label path="currentPassword" for="currentPassword"><spring:message code="model.user.currentPassword" />:</form:label>
				<form:errors path="currentPassword" class="error" />
				<form:password path="currentPassword" showPassword="false" cssErrorClass="errorField" />
			</p>
			<p>
				<form:label path="password" for="password"><spring:message code="model.user.password" />:</form:label>
				<form:errors path="password" class="error" />
				<form:password path="password" showPassword="false" cssErrorClass="errorField" />
			</p>
			<p>
				<form:label path="confirmPassword" for="confirmPassword"><spring:message code="model.user.confirmPassword" />:</form:label>
				<form:errors path="confirmPassword" class="error" />
				<form:password path="confirmPassword" showPassword="false" cssErrorClass="errorField" />
			</p>	
			<p>
				<input name="send" type="submit" value="<spring:message code="navigation.edit" />" />
			</p>
		</form:form>
	</fieldset>

</body>
</html>