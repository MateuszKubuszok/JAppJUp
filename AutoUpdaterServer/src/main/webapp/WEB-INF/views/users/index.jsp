<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<title><spring:message code="repository.name" /> - <spring:message code="model.user.plural" /></title>
	<link rel="stylesheet" href="<c:url value="/resources/css/default.css" />" type="text/css" />
</head>
<body>
	<div id="breadcrumbs">
		<p>
			<a href="<c:url value="/logout" />"><spring:message code="navigation.logout" /></a>
			| <a href="<c:url value="/changepw" />"><spring:message code="navigation.changepw" /></a>
			| <a href="<c:url value="/programs" />"><spring:message code="navigation.programs" /></a>
		</p>
		<p>
			<a href="<c:url value="/users/add" />"><spring:message code="navigation.user.add" /></a>
		</p>
	</div>
	
	<h1><spring:message code="model.user.plural" /></h1>
	
	<c:choose>
		<c:when test="${not empty users}">
			<table>
				<thead>
					<tr>
						<th><spring:message code="model.user.name" /></th>
						<th><spring:message code="model.user.username" /></th>
						<th><spring:message code="model.user.admin" /></th>
						<th><spring:message code="model.user.repoAdmin" /></th>
						<th colspan="2"><spring:message code="navigation.manage" /></th>
					</tr>
				</thead>
				<tbody>
					<c:forEach items="${users}" var="user">
						<tr>
							<td>${user.name}</td>
							<td>${user.username}</td>
							<td>${user.admin}</td>
							<td>${user.packageAdmin}</td>
							<td><a href="<c:url value="/users/edit/${user.id}" />"><spring:message code="navigation.edit" /></a></td>
							<td><a href="<c:url value="/users/delete/${user.id}" />"><spring:message code="navigation.delete" /></a></td>
						</tr>
			  		</c:forEach>
				</tbody>
			</table>
		</c:when>
		<c:otherwise>
			<spring:message code="model.user.none" />
		</c:otherwise>
	</c:choose> 
</body>
</html>