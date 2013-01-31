<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page session="false" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<title>Add Bug for ${bug.program.name}</title>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<link rel="stylesheet" href="<c:url value="/resources/css/default.css" />" type="text/css" />
</head>
<body>
	<div id="breadcrumbs">
		<p>
			<a href="<c:url value="/bugs/${bug.program.id}" />">Back</a>
		</p>
	</div>

	<h1>Add Bug for ${bug.program.name}</h1>
	
	<fieldset>
		<legend>New Bug</legend>
		<c:url value="/bugs/add" var="actionURL" />
		<form:form modelAttribute="bug" method="POST" commandName="bug" action="${actionURL}">
			<p>
				<form:label path="description" for="description">Bug description:</form:label>
				<form:errors path="description" class="error" />
				<form:textarea path="description" />
			</p>
			<p>
				<input name="send" type="submit" value="Add Bug" />
			</p>
		</form:form>
	</fieldset>
</body>
</html>