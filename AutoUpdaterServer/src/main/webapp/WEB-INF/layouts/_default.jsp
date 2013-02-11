<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page session="false" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<title><spring:message code="repository.name" /> - <tiles:insertAttribute name="title" /></title>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<link rel="stylesheet" href="<c:url value="/resources/css/default.css" />" type="text/css" />
	<script type="text/javascript" src="<c:url value="/resources/js/jquery-1.9.1.js" />"></script>
	<script type="text/javascript" src="<c:url value="/resources/js/jquery-tablesorter.js" />"></script>
</head>
<body>
	<div id="header">
		<h1><a href="<c:url value="/" />"><spring:message code="repository.name" /></a></h1>
		<div id="profile">
		<c:choose>
			<c:when test="${not empty currentUser}">
				<p><spring:message code="navigation.welcome" arguments="${currentUser.name}" /></p>
				<p>
					<a href="<c:url value="/changepw" />"><spring:message code="navigation.changepw" /></a>
					| <a href="<c:url value="/logout" />"><spring:message code="navigation.logout" /></a>
				</p>
			</c:when>
			<c:otherwise>
				<spring:message code="navigation.guest" var="guest" />
				<p><spring:message code="navigation.welcome" arguments="${guest}" /></p>
				<p><a href="<c:url value="/sign_in" />"><spring:message code="navigation.signIn" /></a></p>
			</c:otherwise>
		</c:choose>
		</div>
		<div id="breadcrumbs">
			<p>
			<c:choose>
				<c:when test="${not empty currentUser}">
				<c:choose>
					<c:when test="${currentUser.admin}">
						<a href="<c:url value="/users/" />"><spring:message code="navigation.users" /></a>
						| <a href="<c:url value="/programs/" />"><spring:message code="navigation.programs" /></a>
					</c:when>
					<c:when test="${currentUser.packageAdmin}">
						<a href="<c:url value="/programs/" />"><spring:message code="navigation.programs" /></a>
					</c:when>
					<c:otherwise>
						<spring:message code="navigation.noPrivileges" />
					</c:otherwise>
				</c:choose>
				</c:when>
				<c:otherwise>
					<spring:message code="navigation.encourageToSignIn" />
				</c:otherwise>
			</c:choose>
			</p>
			<p>
				<tiles:insertAttribute name="breadcrumbs" />
			</p>
		</div>
	</div>

	<div id="wrapper">
		<div id="padding">
			<h2><tiles:insertAttribute name="title" /></h2>
			<tiles:insertAttribute name="content" />
		</div>
	</div>
	
	<div id="footer">
		<spring:message code="repository.footer" />
	</div>
</body>
</html>