<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<title>Users</title>
	<link rel="stylesheet" href="<c:url value="/resources/css/default.css" />" type="text/css" />
</head>
<body>
	<div id="breadcrumbs">
		<p>
			<a href="<c:url value="/logout" />">Logout</a>
			| <a href="<c:url value="/changepw" />">Change Password</a>
			| <a href="<c:url value="/programs" />">Show Programs</a>
		</p>
		<p>
			<c:url var="addUrl" value="/users/add" />
			<a href="${addUrl}">Add new User</a>
		</p>
	</div>
	
	<h1>Users</h1>
	
	<c:choose>
		<c:when test="${not empty users}">
			<table>
				<thead>
					<tr>
						<th>Full Name</th>
						<th>Username</th>
						<th>Admin Privileges</th>
						<th>Package Admin Privileges</th>
						<th colspan="2">Manage</th>
					</tr>
				</thead>
				<tbody>
					<c:forEach items="${users}" var="user">
						<tr>
							<td><c:out value="${user.name}" /></td>
							<td><c:out value="${user.username}" /></td>
							<td><c:out value="${user.admin}" /></td>
							<td><c:out value="${user.packageAdmin}" /></td>
							<c:url var="editUrl" value="/users/edit/${user.id}" />
							<td><a href="${editUrl}">Edit</a></td>
							<c:url var="deleteUrl" value="/users/delete/${user.id}" />
							<td><a href="${deleteUrl}">Delete</a></td>
						</tr>
			  		</c:forEach>
				</tbody>
			</table>
		</c:when>
		<c:otherwise>
			There are currently no users in the system.
		</c:otherwise>
	</c:choose> 
</body>
</html>