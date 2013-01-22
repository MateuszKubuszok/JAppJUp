<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>Edit User</title>
</head>
<body>
 
<h1>Edit User Data</h1>
 
<fieldset>
<legend>Sign up</legend>
<c:url var="actionUrl" value="/users/edit" />
<form:form modelAttribute="user" method="POST" commandName="user" action="${actionUrl}">
	<p>
		<form:errors path="username" cssStyle="color : red;" />
		<form:label path="username" for="username">Username:</form:label>
		<form:input path="username"/>
	</p>
	<p>
		<form:errors path="name" cssStyle="color : red;" />
		<form:label path="name" for="name">Full name:</form:label>
		<form:input path="name"/>
	</p>
	<p>
		<form:errors path="userType" cssStyle="color : red;" />
		<form:label path="userType" for="userType">Role:</form:label>
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