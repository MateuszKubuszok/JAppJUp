<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page session="false" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>

<spring:message code="model.user.plural" var="title" />

<c:set var="breadcrumbs">
	<a href="<c:url value="/users/add" />"><spring:message code="navigation.user.add" /></a>
</c:set>

<c:set var="content">
	<c:choose>
		<c:when test="${not empty users}">
			<table class="tablesorter">
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
</c:set>

<tiles:insertDefinition name="default">
	<tiles:putAttribute name="title" value="${title}" />
	<tiles:putAttribute name="breadcrumbs" value="${breadcrumbs}" />
	<tiles:putAttribute name="content" value="${content}" />
</tiles:insertDefinition>