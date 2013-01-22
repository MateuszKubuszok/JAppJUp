<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<title>Edit Program</title>
</head>
<body>
	<a href="<c:url value="/programs" />">Back</a>

	<h1>Edit Program Data</h1>
	
	<fieldset>
		<legend>Edit Program</legend>
		<c:url value="/programs/edit" var="actionURL" />
		<form:form modelAttribute="program" method="POST" commandName="program" action="${actionURL}">
			<p>
				<form:errors path="name" cssStyle="color : red;" />
				<form:label path="name" for="name">Program Name:</form:label>
				<form:input path="name" value="${program.name}"/>
			</p>
			<p>
				<input name="send" type="submit" value="Save" />
			</p>
		</form:form>
	</fieldset>
</body>
</html>