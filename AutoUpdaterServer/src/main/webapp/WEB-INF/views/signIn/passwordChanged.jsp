<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page import="java.util.Date" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<title>Password changed</title>
	<link rel="stylesheet" href="<c:url value="/resources/css/default.css" />" type="text/css" />
</head>
<body>
	<div id="breadcrumbs">
		<p>
			<a href="<c:url value="/logout" />">Logout</a>
			| <a href="<c:url value="/changepw" />">Change Password</a>
			| <a href="<c:url value="/users" />">Show System Users</a>
			| <a href="<c:url value="/programs" />">Show Programs</a>
		</p>
	</div>

	<h1>Password changed</h1>
 
	<p>You have successfully changed your password</p>
</body>
</html>