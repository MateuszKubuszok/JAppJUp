<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page session="false" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>

<spring:message code="model.update.for" arguments="${thePackage.program.name},${thePackage.name}" var="title" />

<c:set var="breadcrumbs">
	<a href="<c:url value="/programs/" />"><spring:message code="navigation.programs" /></a>
	&gt; <a href="<c:url value="/packages/${thePackage.program.id}" />"><spring:message code="navigation.packages" arguments="${thePackage.program.name}" /></a>
	&gt; <spring:message code="navigation.updates" arguments="${thePackage.name}" />
	<c:if test="${currentUser.packageAdmin}">
		 &gt; <a href="<c:url value="/updates/add/${thePackage.id}" />"><spring:message code="navigation.update.add" /></a>
	</c:if>
</c:set>

<c:set var="content">
	<c:choose>
		<c:when test="${not empty updates}">
			<table class="tablesorter">
				<thead>
					<tr>
						<th><spring:message code="model.update.version" /></th>
						<th><spring:message code="model.update.developmentVersion" /></th>
						<th><spring:message code="model.update.type" /></th>
						<th><spring:message code="model.update.originalName" /></th>
						<th><spring:message code="model.update.relativePath" /></th>
						<th><spring:message code="model.update.updaterCommand" /></th>
						<th><spring:message code="model.update.changelog" /></th>
						<th><spring:message code="model.update.download" /></th>
						<c:if test="${currentUser.packageAdmin}">
							<th colspan="2"><spring:message code="navigation.manage" /></th>
						</c:if>
					</tr>
				</thead>
				<tbody>
					<c:forEach items="${updates}" var="update">
						<tr>
							<td>${update.version}</td>
							<td>${update.developmentVersion}</td>
							<td><spring:message code="enum.EUpdateStrategy.${update.type}" /></td>
							<td>${update.fileName}</td>
							<td><c:choose>
								<c:when test="${empty update.relativePath}">
									<spring:message code="value.default" />
								</c:when>
								<c:otherwise>
									${update.relativePath}
								</c:otherwise>
							</c:choose></td>
							<td><c:choose>
								<c:when test="${empty update.updaterCommand}">
									<spring:message code="value.none" />
								</c:when>
								<c:otherwise>
									${update.updaterCommand}
								</c:otherwise>
							</c:choose></td>
							<td>${update.changelog}</td>
							<td><a href="<c:url value="/api/download/${update.id}"/>"><spring:message code="model.update.download" /></a></td>
							<c:if test="${currentUser.packageAdmin}">
								<td><a href="<c:url value="/updates/edit/${update.id}" />"><spring:message code="navigation.edit" /></a></td>
								<td><a href="<c:url value="/updates/delete/${update.id}" />"><spring:message code="navigation.delete" /></a></td>
							</c:if>
						</tr>
					</c:forEach>
				</tbody>
			</table>
		</c:when>
		<c:otherwise>
			<spring:message code="model.update.none" />
		</c:otherwise>
	</c:choose>
</c:set>

<tiles:insertDefinition name="default">
	<tiles:putAttribute name="title" value="${title}" />
	<tiles:putAttribute name="breadcrumbs" value="${breadcrumbs}" />
	<tiles:putAttribute name="content" value="${content}" />
</tiles:insertDefinition>