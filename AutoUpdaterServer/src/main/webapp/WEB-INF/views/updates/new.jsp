<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page session="false" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>

<spring:message code="model.update.add" arguments="${newUpdate.thePackage.program.name},${newUpdate.thePackage.name}" var="title" />

<c:set var="breadcrumbs">
	<a href="<c:url value="/updates/${newUpdate.thePackage.id}" />"><spring:message code="navigation.back" /></a>
</c:set>

<c:set var="content">
	<fieldset>
		<legend><spring:message code="model.update.add" arguments="${newUpdate.thePackage.program.name},${newUpdate.thePackage.name}" /></legend>
		<c:url value="/updates/add" var="actionURL" />
		<form:form modelAttribute="newUpdate" method="POST" enctype="multipart/form-data" commandName="newUpdate" action="${actionURL}">
			<form:errors path="uploader" />
			<p>
				<form:label path="version" for="version"><spring:message code="model.update.version" />:</form:label>
				<form:errors path="version" class="error" />
				<form:input path="version" cssErrorClass="errorField" />
			</p>
			<p>
				<form:label path="developmentVersion" for="developmentVersion"><spring:message code="model.update.developmentVersion" />:</form:label>
				<form:errors path="developmentVersion" class="error" />
				<form:checkbox path="developmentVersion" cssErrorClass="errorField" />
			</p>
			<p>
				<form:label path="changelog" for="changelog"><spring:message code="model.update.changelog" />:</form:label>
				<form:errors path="changelog" class="error" />
				<form:textarea path="changelog" rows="10" cssErrorClass="errorField" />
			</p>
			<p>
				<form:label path="type" for="type"><spring:message code="model.update.type" />:</form:label>
				<form:errors path="type" class="error" />
				<form:select path="type" cssErrorClass="errorField">
				<c:forEach items="${updateTypes}" var="type">
					<form:option value="${type}"><spring:message code="enum.EUpdateStrategy.${type}" /></form:option>
				</c:forEach>
				</form:select>
			</p>
			<p>
				<form:label for="file" path="file"><spring:message code="model.update.file" />:</form:label>
				<form:errors path="file" class="error" />
		        <form:input path="file" type="file" cssErrorClass="errorField" />
			</p>
			<p>
				<form:label path="relativePath" for="relativePath"><spring:message code="model.update.relativePath.long" />:</form:label>
				<form:errors path="relativePath" class="error" />
				<form:input path="relativePath" cssErrorClass="errorField" />
			</p>
			<p>
				<form:label path="updaterCommand" for="updaterCommand"><spring:message code="model.update.updaterCommand.long" />:</form:label>
				<form:errors path="updaterCommand" class="error" />
				<form:input path="updaterCommand" cssErrorClass="errorField" />
				
			</p>
			<p><spring:message code="model.update.updaterCommand.allowedVariables" />:</p>
			<ul>
				<li><b>{F}</b> - <spring:message code="model.update.updaterCommand.filename" /></li>
				<li><b>{U}</b> - <spring:message code="model.update.updaterCommand.absolute" /></li>
				<li><b>{I}</b> - <spring:message code="model.update.updaterCommand.installation" /></li>
				<li><b>{R}</b> - <spring:message code="model.update.updaterCommand.relative" /></li>
				<li><b>{T}</b> - <spring:message code="model.update.updaterCommand.target" /></li>
			</ul>
			<p>
				<input name="send" type="submit" value="<spring:message code="navigation.add" />" />
			</p>
		</form:form>
	</fieldset>
</c:set>

<tiles:insertDefinition name="default">
	<tiles:putAttribute name="title" value="${title}" />
	<tiles:putAttribute name="breadcrumbs" value="${breadcrumbs}" />
	<tiles:putAttribute name="content" value="${content}" />
</tiles:insertDefinition>