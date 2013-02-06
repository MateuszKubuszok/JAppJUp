<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page session="false" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>

<spring:message code="model.package.for" arguments="${program.name}" var="title" />

<c:set var="breadcrumbs">
	<a href="<c:url value="/programs" />"><spring:message code="navigation.programs" /></a>	
	<c:if test="${not empty program}">
		&gt; <spring:message code="navigation.packages" arguments="${program.name}" />
	</c:if>
	<c:if test="${user.packageAdmin}">
		&gt; <a href="<c:url value="/packages/add/${program.id}" />"><spring:message code="navigation.package.add" /></a>
	</c:if>
</c:set>

<c:set var="content">
	<c:choose>
		<c:when test="${not empty packages}">
			<table class="tablesorter">
				<thead>
					<tr>
						<th><spring:message code="model.package" /></th>
						<th><spring:message code="model.update.plural" /></th>
						<c:if test="${user.packageAdmin}">
							<th colspan="2"><spring:message code="navigation.manage" /></th>
						</c:if>
					</tr>
				</thead>
				<tbody>
					<c:forEach items="${packages}" var="_package">
						<tr>
							<td>${_package.name}</td>
							<td><a href="<c:url value="/updates/${_package.id}" />"><spring:message code="model.update.show" /></a></td>
							<c:if test="${user.packageAdmin}">
								<td><a href="<c:url value="/packages/edit/${_package.id}" />"><spring:message code="navigation.edit" /></a></td>
								<td><a href="<c:url value="/packages/delete/${_package.id}" />"><spring:message code="navigation.delete" /></a></td>
							</c:if>
						</tr>
					</c:forEach>
				</tbody>
			</table>
	 	</c:when>
	 	<c:otherwise>
	 		<spring:message code="model.package.none" />
	 	</c:otherwise>
	 </c:choose>
</c:set>

<tiles:insertDefinition name="default">
	<tiles:putAttribute name="title" value="${title}" />
	<tiles:putAttribute name="breadcrumbs" value="${breadcrumbs}" />
	<tiles:putAttribute name="content" value="${content}" />
</tiles:insertDefinition>