<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<title>Programs</title>
</head>
<body>
	<a href="<c:url value="/logout" />">Logout</a> |
	<a href="<c:url value="/changepw" />">Change Password</a>
	<c:if test="${user.admin}">
		<c:url var="usersUrl" value="/users/" /> |
		<a href="${usersUrl}">Show System Users</a>
	</c:if>
	<br />
	<c:if test="${user.packageAdmin}">
		<c:url var="addUrl" value="/programs/add" />
		<a href="${addUrl}">Add new Program</a>
	</c:if>
	
	<h1>Programs</h1>
	<c:choose>
		<c:when test="${not empty programs}">
			<table style="border: 1px solid; width: 400px; text-align:center">
				<thead style="background:#fcf">
					<tr>
						<th>Program Name</th>
						<th></th>
						<th></th>
						<c:if test="${user.packageAdmin}">
						<th></th>
						<th></th>
						</c:if>
					</tr>
				</thead>
				<tbody> 	
					<c:forEach items="${programs}" var="program">
				  		<tr>
							<td><c:out value="${program.name}" /></td>
						 	<c:url var="packagesUrl" value="/packages/${program.id}" />
						   	<td><a href="${packagesUrl}">Show Packages</a></td>
						 	<c:url var="bugsUrl" value="/bugs/${program.id}" />
						   	<td><a href="${bugsUrl}">Show Bugs</a></td>
						   	<c:if test="${user.packageAdmin}">
					    	<c:url var="editUrl" value="/programs/edit/${program.id}" />
						   	<td><a href="${editUrl}">Edit</a></td>
							<c:url var="deleteUrl" value="/programs/delete/${program.id}" />
						   	<td><a href="${deleteUrl}">Delete</a></td>
						   	</c:if>
				  		</tr>
				 	</c:forEach>
				</tbody>
			</table>
	 	</c:when>
	 	<c:otherwise>
	 		There are currently no programs in the system.
	 	</c:otherwise>
 	</c:choose>
</body>
</html>