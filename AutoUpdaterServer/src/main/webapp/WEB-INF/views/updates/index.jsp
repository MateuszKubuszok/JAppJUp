<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<title><spring:message code="repository.name" /> - <spring:message code="model.update.for" arguments="${thePackage.program.name},${thePackage.name}" /></title>
	<link rel="stylesheet" href="<c:url value="/resources/css/default.css" />" type="text/css" />
</head>
<body>
	<div id="breadcrumbs">
		<p>
			<a href="<c:url value="/logout" />"><spring:message code="navigation.logout" /></a>
			| <a href="<c:url value="/changepw" />"><spring:message code="navigation.changepw" /></a>
			<c:if test="${user.admin}">
				<c:url var="usersUrl" value="/users/" />
				| <a href="${usersUrl}"><spring:message code="navigation.users" /></a>
			</c:if>
		</p>
		<p>
			<c:url var="programsUrl" value="/programs/" />
			<a href="${programsUrl}"><spring:message code="navigation.programs" /></a> &gt;
			<c:url var="packagesUrl" value="/packages/${thePackage.program.id}" />
			<a href="${packagesUrl}"><spring:message code="navigation.packages" arguments="${thePackage.program.name}" /></a> &gt;
			<spring:message code="navigation.packages" arguments="${thePackage.program.name},${thePackage.name}" />
			<c:if test="${user.packageAdmin}">
				<c:url var="newUpdateUrl" value="/updates/add/${thePackage.id}" /> &gt;
				<a href="${newUpdateUrl}"><spring:message code="navigation.update.add" /></a>
			</c:if>
		</p>
	</div>
	
	<h1><spring:message code="model.update.for" arguments="${thePackage.program.name},${thePackage.name}" /></h1>
	
	<c:choose>
		<c:when test="${not empty updates}">
			<table>
				<thead>
					<tr>
						<th><spring:message code="model.update.version" /></th>
						<th><spring:message code="model.update.developmentVersion" /></th>
						<th><spring:message code="model.update.type" /></th>
						<th><spring:message code="model.update.originalName" /></th>
						<th><spring:message code="model.update.relativePath" /></th>
						<th><spring:message code="model.update.updaterCommand" /></th>
						<th><spring:message code="model.update.changelog" /></th>
						<th colspan="2"><spring:message code="navigation.manage" /></th>
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
									<spring:message code="value.default" />
								</c:when>
								<c:otherwise>
									<c:out value="${update.relativePath}" />
								</c:otherwise>
							</c:choose></td>
							<td><c:choose>
								<c:when test="${empty update.updaterCommand}">
									<spring:message code="value.none" />
								</c:when>
								<c:otherwise>
									<c:out value="${update.updaterCommand}" />
								</c:otherwise>
							</c:choose></td>
							<td><c:out value="${update.changelog}" /></td>
							<c:url value="/updates/edit/${update.id}" var="editURL" />
							<td><a href="${editURL}"><spring:message code="navigation.edit" /></a></td>
							<c:url value="/updates/delete/${update.id}" var="deleteURL" />
							<td><a href="${deleteURL}"><spring:message code="navigation.delete" /></a></td>
						</tr>
					</c:forEach>
				</tbody>
			</table>
		</c:when>
		<c:otherwise>
			<spring:message code="model.update.none" />
		</c:otherwise>
	</c:choose>
</body>
</html>