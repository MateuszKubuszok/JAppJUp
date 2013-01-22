<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<title>Packages</title>
</head>
<body>
	<a href="<c:url value="/logout" />">Logout</a> |
	<a href="<c:url value="/changepw" />">Change Password</a>
	<c:if test="${user.admin}">
		<c:url var="usersUrl" value="/users/" /> |
		<a href="${usersUrl}">Show System Users</a>
	</c:if>
	<br />
	<c:url var="backUrl" value="/programs" />
	<a href="${backUrl}">Programs</a> &gt;	
	<c:if test="${not empty program}">
		${program.name}'s packages
	</c:if>
	<c:if test="${user.packageAdmin}">
		<c:url var="addUrl" value="/packages/add/${program.id}" /> &gt;
		<a href="${addUrl}">Add new Package</a>
	</c:if>
	
	<h1>Packages</h1>
	
	<c:choose>
		<c:when test="${not empty packages}">
			<table style="border: 1px solid; width: 500px; text-align:center">
				<thead style="background:#fcf">
					<tr>
						<th>Package Name</th>
						<th></th>
						<c:if test="${user.packageAdmin}">
						<th></th>
						<th></th>
						</c:if>
					</tr>
				</thead>
				<tbody>
					<c:forEach items="${packages}" var="_package">
						<tr>
							<td><c:out value="${_package.name}" /></td>
							<c:url var="updatesUrl" value="/updates/${_package.id}" />
							<td><a href="${updatesUrl}">Show Updates</a></td>
							<c:if test="${user.packageAdmin}">
							<c:url var="editUrl" value="/packages/edit/${_package.id}" />
							<td><a href="${editUrl}">Edit</a></td>
							<c:url var="deleteUrl" value="/packages/delete/${_package.id}" />
							<td><a href="${deleteUrl}">Delete</a></td>
							</c:if>
						</tr>
					</c:forEach>
				</tbody>
			</table>
	 	</c:when>
	 	<c:otherwise>
	 		There are currently no packages in the system for the specified program.
	 	</c:otherwise>
	 </c:choose>
</body>
</html>