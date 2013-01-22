<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>Edit Password</title>
</head>
<body>
 
<h1>Change Your Password</h1>
 
<c:url var="saveUrl" value="/users/changepw" />
<fieldset>
	<legend>Change Password</legend>
	<form:form modelAttribute="passwordEditionCommand" method="POST" commandName="passwordEditionCommand">
		<form:hidden path="userId" />
		<p>
			<form:errors path="currentPassword" cssStyle="color : red;" />
			<form:label path="currentPassword" for="currentPassword">Current Password:</form:label>
			<form:password path="currentPassword" showPassword="false" />
		</p>
		<p>
			<form:errors path="password" cssStyle="color : red;" />
			<form:label path="password" for="password">New Password:</form:label>
			<form:password path="password" showPassword="false" />
		</p>
		<p>
			<form:errors path="confirmPassword" cssStyle="color : red;" />
			<form:label path="confirmPassword" for="confirmPassword">Confirm New Password:</form:label>
			<form:password path="confirmPassword" showPassword="false" />
		</p>	
		<p>
			<input name="send" type="submit" value="Save" />
		</p>
	</form:form>
</fieldset>
 
</body>
</html>