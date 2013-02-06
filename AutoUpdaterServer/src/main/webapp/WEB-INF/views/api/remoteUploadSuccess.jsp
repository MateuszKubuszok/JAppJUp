<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page session="false" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>

<spring:message code="remoteUpload.newFileUpload" var="title" />

<c:set var="breadcrumbs">
	<p>
		<a href="<c:url value="/api/upload_file" />"><spring:message code="remoteUpload.newFileUpload" /></a>
	</p>
</c:set>

<c:set var="content">
	<fieldset>
		<legend><spring:message code="model.update" /></legend>
		<p><b><spring:message code="model.program" /></b>: ${remoteUpdateUpload.program}</p>
		<p><b><spring:message code="model.package" /></b>: ${remoteUpdateUpload.thePackage}</p>
		<p><b><spring:message code="model.update.version" /></b>: ${remoteUpdateUpload.update.version}</p>
		<p><b><spring:message code="model.update.developmentVersion" /></b>: ${remoteUpdateUpload.update.developmentVersion}</p>
		<p><b><spring:message code="model.update.changelog" /></b>: ${remoteUpdateUpload.update.changelog}</p>
		<p><b><spring:message code="model.update.type" /></b>: ${remoteUpdateUpload.update.type}</p>
		<p><b><spring:message code="model.update.relativePath" /></b>: ${remoteUpdateUpload.update.relativePath}</p>
		<p><b><spring:message code="model.update.updaterCommand" /></b>: ${remoteUpdateUpload.update.updaterCommand}</p>
	</fieldset>
</c:set>

<tiles:insertDefinition name="default">
	<tiles:putAttribute name="title" value="${title}" />
	<tiles:putAttribute name="breadcrumbs" value="${breadcrumbs}" />
	<tiles:putAttribute name="content" value="${content}" />
</tiles:insertDefinition>