<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<title>Edit Bug for ${bug.program.name}</title>
	<link rel="stylesheet" href="<c:url value="/resources/css/default.css" />" type="text/css" />
</head>
<body>
	<div id="breadcrumbs">
		<p>
			<a href="<c:url value="/bugs/${bug.program.id}" />">Back</a>
		</p>
	</div>

	<h1>Edit Bug for ${bug.program.name}</h1>
	 
	<fieldset>
		<legend>Edit Bug</legend>
		<c:url value="/bugs/edit" var="actionURL" />
		<form:form modelAttribute="bug" method="POST" commandName="bug" action="${actionURL}">
			<p>
				<form:label path="description" for="description">Bug description:</form:label>
				<form:errors path="description" class="error" />
				<form:textarea path="description" />
			</p>
			<p>
				<input name="send" type="submit" value="Save" />
			</p>
		</form:form>
	</fieldset>
</body>
</html>