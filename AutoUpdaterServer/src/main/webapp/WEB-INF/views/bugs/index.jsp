<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page session="false" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>

<spring:message code="model.bug.for" arguments="${program.name}" var="title" />

<c:set var="breadcrumbs">
	<p>
		<c:if test="${user.admin}">
			<a href="<c:url value="/users/" />"><spring:message code="navigation.users" /></a>
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
</c:set>

<c:set var="content">
	<c:choose>
		<c:when test="${not empty bugs}">
			<table class="tablesorter">
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
</c:set>

<tiles:insertDefinition name="default">
	<tiles:putAttribute name="title" value="${title}" />
	<tiles:putAttribute name="breadcrumbs" value="${breadcrumbs}" />
	<tiles:putAttribute name="content" value="${content}" />
</tiles:insertDefinition>