<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page session="false" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>

<spring:message code="model.program.plural" var="title" />

<c:set var="breadcrumbs">
	<spring:message code="navigation.programs" />
	<c:if test="${currentUser.packageAdmin}">
		&gt; <a href="<c:url value="/programs/add" />"><spring:message code="navigation.program.add" /></a>
	</c:if>
</c:set>

<c:set var="content">
	<c:choose>
		<c:when test="${not empty programs}">
			<table class="tablesorter">
				<thead>
					<tr>
						<th><spring:message code="model.program" /></th>
						<th><spring:message code="model.package.plural" /></th>
						<th><spring:message code="model.bug.plural" /></th>
						<c:if test="${currentUser.packageAdmin}">
							<th colspan="2"><spring:message code="navigation.manage" /></th>
						</c:if>
					</tr>
				</thead>
				<tbody> 	
					<c:forEach items="${programs}" var="program">
				  		<tr>
							<td>${program.name}</td>
						   	<td><a href="<c:url value="/packages/${program.id}" />"><spring:message code="model.package.show" /></a></td>
						   	<td><a href="<c:url value="/bugs/${program.id}" />"><spring:message code="model.bug.show" /></a></td>
						   	<c:if test="${currentUser.packageAdmin}">
						   		<td><a href="<c:url value="/programs/edit/${program.id}" />"><spring:message code="navigation.edit" /></a></td>
						   		<td><a href="<c:url value="/programs/delete/${program.id}" />"><spring:message code="navigation.delete" /></a></td>
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
</c:set>

<tiles:insertDefinition name="default">
	<tiles:putAttribute name="title" value="${title}" />
	<tiles:putAttribute name="breadcrumbs" value="${breadcrumbs}" />
	<tiles:putAttribute name="content" value="${content}" />
</tiles:insertDefinition>