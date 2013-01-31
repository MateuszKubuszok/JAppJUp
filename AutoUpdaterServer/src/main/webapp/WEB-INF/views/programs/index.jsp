<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<title><spring:message code="repository.name" /> - <spring:message code="model.program.plural" /></title>
	<link rel="stylesheet" href="<c:url value="/resources/css/default.css" />" type="text/css" />
</head>
<body>
	<div id="breadcrumbs">
		<p>
			<a href="<c:url value="/logout" />"><spring:message code="navigation.logout" /></a> |
			<a href="<c:url value="/changepw" />"><spring:message code="navigation.changepw" /></a>
			<c:if test="${user.admin}">
				<c:url var="usersUrl" value="/users/" /> |
				<a href="${usersUrl}"><spring:message code="navigation.users" /></a>
			</c:if>
		</p>
		<c:if test="${user.packageAdmin}">
		<p>
			<c:url var="addUrl" value="/programs/add" />
			<a href="${addUrl}"><spring:message code="navigation.program.add" /></a>
		</p>
		</c:if>
	</div>
	
	<h1><spring:message code="model.program.plural" /></h1>
	
	<c:choose>
		<c:when test="${not empty programs}">
			<table>
				<thead>
					<tr>
						<th><spring:message code="model.program" /></th>
						<th><spring:message code="model.package.plural" /></th>
						<th><spring:message code="model.bug.plural" /></th>
						<c:if test="${user.packageAdmin}">
						<th colspan="2"><spring:message code="navigation.manage" /></th>
						</c:if>
					</tr>
				</thead>
				<tbody> 	
					<c:forEach items="${programs}" var="program">
				  		<tr>
							<td><c:out value="${program.name}" /></td>
						 	<c:url var="packagesUrl" value="/packages/${program.id}" />
						   	<td><a href="${packagesUrl}"><spring:message code="model.package.show" /></a></td>
						 	<c:url var="bugsUrl" value="/bugs/${program.id}" />
						   	<td><a href="${bugsUrl}"><spring:message code="model.bug.show" /></a></td>
						   	<c:if test="${user.packageAdmin}">
					    	<c:url var="editUrl" value="/programs/edit/${program.id}" />
						   	<td><a href="${editUrl}"><spring:message code="navigation.edit" /></a></td>
							<c:url var="deleteUrl" value="/programs/delete/${program.id}" />
						   	<td><a href="${deleteUrl}"><spring:message code="navigation.delete" /></a></td>
						   	</c:if>
				  		</tr>
				 	</c:forEach>
				</tbody>
			</table>
	 	</c:when>
	 	<c:otherwise>
	 		<spring:message code="model.program.none" />
	 	</c:otherwise>
 	</c:choose>
</body>
</html>