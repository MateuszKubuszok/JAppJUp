<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<title>Updates for ${thePackage.program.name}/${thePackage.name}</title>
	<link rel="stylesheet" href="<c:url value="/resources/css/default.css" />" type="text/css" />
</head>
<body>
	<div id="breadcrumbs">
		<p>
			<a href="<c:url value="/logout" />">Logout</a> |
			<a href="<c:url value="/changepw" />">Change Password</a>
			<c:if test="${user.admin}">
				<c:url var="usersUrl" value="/users/" /> |
				<a href="${usersUrl}">Show System Users</a>
			</c:if>
		</p>
		<p>
			<c:url var="programsUrl" value="/programs/" />
			<a href="${programsUrl}">Programs</a> &gt;
			<c:url var="packagesUrl" value="/packages/${thePackage.program.id}" />
			<a href="${packagesUrl}">${thePackage.program.name}'s packages</a> &gt;
			${thePackage.name}'s updates
			<c:if test="${user.packageAdmin}">
				<c:url var="newUpdateUrl" value="/updates/add/${thePackage.id}" /> &gt;
				<a href="${newUpdateUrl}">Add update</a>
			</c:if>
		</p>
	</div>
	
	<h1>Updates for ${thePackage.program.name}/${thePackage.name}</h1>
	
	<c:choose>
		<c:when test="${not empty updates}">
			<table>
				<thead>
					<tr>
						<th>Version</th>
						<th>Development Version</th>
						<th>Type</th>
						<th>Original name</th>
						<th>Relative path</th>
						<th>Command</th>
						<th>Changelog</th>
						<th colspan="2">Manage</th>
					</tr>
				</thead>
				<tbody>
					<c:forEach items="${updates}" var="update">
						<tr>
							<td><c:out value="${update.version}" /></td>
							<td><c:out value="${update.developmentVersion}" /></td>
							<td><c:out value="${update.type}" /></td>
							<td><c:out value="${update.fileName}" /></td>
							<td><c:choose>
								<c:when test="${empty update.relativePath}">
									<c:out value="default" />
								</c:when>
								<c:otherwise>
									<c:out value="${update.relativePath}" />
								</c:otherwise>
							</c:choose></td>
							<td><c:choose>
								<c:when test="${empty update.updaterCommand}">
									<c:out value="none" />
								</c:when>
								<c:otherwise>
									<c:out value="${update.updaterCommand}" />
								</c:otherwise>
							</c:choose></td>
							<td><c:out value="${update.changelog}" /></td>
							<c:url value="/updates/edit/${update.id}" var="editURL" />
							<td><a href="${editURL}">Edit</a></td>
							<c:url value="/updates/delete/${update.id}" var="deleteURL" />
							<td><a href="${deleteURL}">Delete</a></td>
						</tr>
					</c:forEach>
				</tbody>
			</table>
		</c:when>
		<c:otherwise>
			There are currently no updates in the system for specified package.
		</c:otherwise>
	</c:choose>
</body>
</html>