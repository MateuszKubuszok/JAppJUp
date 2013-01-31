<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<title><spring:message code="repository.name" /> - <spring:message code="model.bug.for" arguments="${program.name}" /></title>
	<link rel="stylesheet" href="<c:url value="/resources/css/default.css" />" type="text/css" />
</head>
<body>
	<div id="breadcrumbs">
		<p>
			<a href="<c:url value="/logout" />"><spring:message code="navigation.logout" /></a>
			| <a href="<c:url value="/changepw" />"><spring:message code="navigation.changepw" /></a>
			<c:if test="${user.admin}">
				| <a href="<c:url value="/users/" />"><spring:message code="navigation.users" /></a>
			</c:if>
		</p>
		<p>
			<a href="<c:url value="/programs" />"><spring:message code="navigation.programs" /></a>	
			<c:if test="${not empty program}">
				&gt; <spring:message code="navigation.bugs" arguments="${program.name}" />
			</c:if>
			<c:if test="${user.packageAdmin}">	
				&gt; <a href="<c:url value="/bugs/add/${program.id}" />"><spring:message code="navigation.bug.add" /></a>
			</c:if>
		</p>
	</div>
	
	<h1><spring:message code="model.bug.for" arguments="${program.name}" /></h1>
	
	<c:choose>
		<c:when test="${not empty bugs}">
			<table>
				<thead>
					<tr>
						<th><spring:message code="model.bug.description" /></th>
						<c:if test="${user.packageAdmin}">
							<th colspan="2"><spring:message code="navigation.manage" /></th>
						</c:if>
					</tr>
				</thead>
				<tbody>
					<c:forEach items="${bugs}" var="bug">
						<tr>
							<td>${bug.description}</td>
							<c:if test="${user.packageAdmin}">
								<td><a href="<c:url value="/bugs/edit/${bug.id}" />"><spring:message code="navigation.edit" /></a></td>
								<td><a href="<c:url value="/bugs/delete/${bug.id}" />"><spring:message code="navigation.delete" /></a></td>
							</c:if>
						</tr>
					</c:forEach>
				</tbody>
			</table>
	 	</c:when>
	 	<c:otherwise>
	 		<spring:message code="model.bug.none" />
	 	</c:otherwise>
	 </c:choose>
</body>
</html>