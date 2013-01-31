<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ page session="false" %>
<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<title><spring:message code="repository.name" /> - <spring:message code="model.package.add" arguments="${_package.program.name}" /></title>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<link rel="stylesheet" href="<c:url value="/resources/css/default.css" />" type="text/css" />
</head>
<body>
	<div id="breadcrumbs">
		<p>
			<a href="<c:url value="/packages/${_package.program.id}" />"><spring:message code="navigation.back" /></a>
		</p>
	</div>

	<h1><spring:message code="model.package.add" arguments="${_package.program.name}" /></h1>
	
	<fieldset>
		<legend><spring:message code="model.package.add" arguments="${_package.program.name}" /></legend>
		<c:url value="/packages/add" var="actionURL" />
		<form:form modelAttribute="_package" method="POST" commandName="_package" action="${actionURL}">
			<p>
				<form:label path="name" for="name"><spring:message code="model.package.name" />:</form:label>
				<form:errors path="name" class="error" />
				<form:input path="name" cssErrorClass="errorField" />
			</p>
			<p>
				<input name="send" type="submit" value="<spring:message code="navigation.add" />" />
			</p>
		</form:form>
	</fieldset>
</body>
</html>