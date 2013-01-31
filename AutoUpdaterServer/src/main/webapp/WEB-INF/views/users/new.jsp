<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page session="false" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<title>Register</title>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<link rel="stylesheet" href="<c:url value="/resources/css/default.css" />" type="text/css" />
</head>
<body>
	<div id="breadcrumbs">
		<p>
			<a href="<c:url value="/users" />">Back</a>
		</p>
	</div>

	<h1>Create New User</h1>
	
	<fieldset>
	<legend>Create new user</legend>
	<form:form modelAttribute="newUser" method="POST" commandName="newUser">
		<p>
			<form:label path="username" for="username">Username:</form:label>
			<form:errors path="username" class="error" />
			<form:input path="username" />
		</p>
		<p>
			<form:errors path="password" class="error" />
			<form:label path="password" for="password">Password:</form:label>
			<form:password path="password" showPassword="false" />
		</p>
		<p>
			<form:label path="confirmPassword" for="confirmPassword">Confirm password:</form:label>
			<form:errors path="confirmPassword" class="error" />
			<form:password path="confirmPassword" showPassword="false" />
		</p>
		<p>
			<form:label path="name" for="name">Full name:</form:label>
			<form:errors path="name" class="error" />
			<form:input path="name" />
		</p>
		<p>
			<form:label path="userType" for="userType">Role:</form:label>
			<form:errors path="userType" class="error" />
			<form:select path="userType">
	        	<form:options items="${types}" />
			</form:select>
		</p>
		<p>
			<input name="send" type="submit" value="Save" />
		</p>
	</form:form>
	</fieldset>
</body>
</html>