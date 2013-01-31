<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page session="false" %>
<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<title><spring:message code="repository.name" /> - <spring:message code="model.program.add" /></title>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<link rel="stylesheet" href="<c:url value="/resources/css/default.css" />" type="text/css" />
</head>
<body>
	<div id="breadcrumbs">
		<p>
			<a href="<c:url value="/programs" />"><spring:message code="navigation.back" /></a>
		</p>
	</div>

	<h1><spring:message code="model.program.add" /></h1>
	
	<fieldset>
		<legend><spring:message code="model.program.add" /></legend>
		<form:form modelAttribute="program" method="POST" commandName="program">
			<p>
				<form:label path="name" for="name"><spring:message code="model.program.name" />:</form:label>
				<form:errors path="name" class="error" />
				<form:input path="name" />
			</p>
			<p>
				<input name="send" type="submit" value="<spring:message code="navigation.add" />" />
			</p>
		</form:form>
	</fieldset>
</body>
</html>