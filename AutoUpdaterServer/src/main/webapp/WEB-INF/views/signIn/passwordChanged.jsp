<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page session="false" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>

<spring:message code="navigation.changedpw" var="title" />

<c:set var="breadcrumbs">
	<p>
		<a href="<c:url value="/logout" />"><spring:message code="navigation.logout" /></a>
		| <a href="<c:url value="/changepw" />"><spring:message code="navigation.changepw" /></a>
		| <a href="<c:url value="/users" />"><spring:message code="navigation.users" /></a>
		| <a href="<c:url value="/programs" />"><spring:message code="navigation.programs" /></a>
	</p>
</c:set>

<c:set var="content">
	<p><spring:message code="model.user.passwordChanged.long" /></p>
</c:set>

<tiles:insertDefinition name="default">
	<tiles:putAttribute name="title" value="${title}" />
	<tiles:putAttribute name="breadcrumbs" value="${breadcrumbs}" />
	<tiles:putAttribute name="content" value="${content}" />
</tiles:insertDefinition>