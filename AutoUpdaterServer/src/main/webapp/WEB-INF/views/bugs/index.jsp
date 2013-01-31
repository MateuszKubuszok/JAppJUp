<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<title>Bugs for ${program.name}</title>
	<link rel="stylesheet" href="<c:url value="/resources/css/default.css" />" type="text/css" />
</head>
<body>
	<div id="breadcrumbs">
		<p>
			<a href="<c:url value="/logout" />">Logout</a>
			| <a href="<c:url value="/changepw" />">Change Password</a>
			<c:if test="${user.admin}">
				<c:url var="usersUrl" value="/users/" />
				| <a href="${usersUrl}">Show System Users</a>
			</c:if>
		</p>
		<p>
			<c:url var="backUrl" value="/programs" />
			<a href="${backUrl}">Back to Programs List</a> &gt;	
			<c:if test="${not empty program}">
				${program.name}'s bugs
			</c:if>	
			<c:if test="${user.packageAdmin}">	
				<c:url var="addUrl" value="/bugs/add/${program.id}" /> &gt;
				<a href="${addUrl}">Add new Bug</a>
			</c:if>
		</p>
	</div>
	
	<h1>Bugs for ${program.name}</h1>
	
	<c:choose>
		<c:when test="${not empty bugs}">
			<table>
				<thead>
					<tr>
						<th>Bug</th>
						<c:if test="${user.packageAdmin}">
						<th colspan="2">Manage</th>
						</c:if>
					</tr>
				</thead>
				<tbody>
					<c:forEach items="${bugs}" var="bug">
						<tr>
							<td><c:out value="${bug.description}" /></td>
							<c:if test="${user.packageAdmin}">
							<c:url var="editUrl" value="/bugs/edit/${bug.id}" />
							<td><a href="${editUrl}">Edit</a></td>
							<c:url var="deleteUrl" value="/bugs/delete/${bug.id}" />
							<td><a href="${deleteUrl}">Delete</a></td>
							</c:if>
						</tr>
					</c:forEach>
				</tbody>
			</table>
	 	</c:when>
	 	<c:otherwise>
	 		There are currently no bugs in the system for the specified program.
	 	</c:otherwise>
	 </c:choose>
</body>
</html>