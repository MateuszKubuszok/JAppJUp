<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<title>Edit User</title>
	<link rel="stylesheet" href="<c:url value="/resources/css/default.css" />" type="text/css" />
</head>
<body>
	<div id="breadcrumbs">
		<p>
			<a href="<c:url value="/users" />">Back</a>
		</p>
	</div>
 
	<h1>Edit User Data</h1>
	 
	<fieldset>
	<legend>Sign up</legend>
	<c:url var="actionUrl" value="/users/edit" />
	<form:form modelAttribute="user" method="POST" commandName="user" action="${actionUrl}">
		<p>
			<form:label path="username" for="username">Username:</form:label>
			<form:errors path="username" class="error" />
			<form:input path="username"/>
		</p>
		<p>
			<form:label path="name" for="name">Full name:</form:label>
			<form:errors path="name" class="error" />
			<form:input path="name"/>
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