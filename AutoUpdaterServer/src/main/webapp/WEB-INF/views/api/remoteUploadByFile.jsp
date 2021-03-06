<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page session="false" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>

<spring:message code="remoteUpload.newFileUpload" var="title" />

<c:set var="breadcrumbs">
	<a href="<c:url value="/api/upload_file" />"><spring:message code="remoteUpload.newFileUpload" /></a>
</c:set>
	
<c:set var="content">
	<fieldset>
		<legend><spring:message code="remoteUpload.newFileUpload" /></legend>
		<c:url value="/api/upload_file" var="actionURL" />
		<form:form modelAttribute="remoteUpdateUpload" method="POST" enctype="multipart/form-data" commandName="remoteUpdateUpload" action="${actionURL}">
			<p>
				<form:label path="username" for="username"><spring:message code="model.user.username" />:</form:label>
				<form:errors path="username" class="error" />
				<form:errors path="update.uploader" class="error" />
				<form:input path="username" cssErrorClass="errorField" />
			</p>
			<p>
				<form:label path="password" for="password"><spring:message code="model.user.password" />:</form:label>
				<form:errors path="password" class="error" />
				<form:password path="password" cssErrorClass="errorField" />
			</p>
			<p>
				<form:label path="program" for="program"><spring:message code="model.program" />:</form:label>
				<form:errors path="program" class="error" />
				<form:input path="program" cssErrorClass="errorField" />
			</p>
			<p>
				<form:label path="thePackage" for="thePackage"><spring:message code="model.package" />:</form:label>
				<form:errors path="thePackage" class="error" />
				<form:errors path="update.thePackage" class="error" />
				<form:input path="thePackage" cssErrorClass="errorField" />
			</p>
			<p>
				<form:label path="version" for="version"><spring:message code="model.update.version" />:</form:label>
				<form:errors path="update.version" class="error" />
				<form:input path="version" cssErrorClass="errorField" />
			</p>
			<p>
				<form:label path="developmentVersion" for="developmentVersion"><spring:message code="model.update.developmentVersion" />:</form:label>
				<form:errors path="update.developmentVersion" class="error" />
				<form:checkbox path="developmentVersion" cssErrorClass="errorField" />
			</p>
			<p>
				<form:label path="changelog" for="changelog"><spring:message code="model.update.changelog" />:</form:label>
				<form:errors path="update.changelog" class="error" />
				<form:textarea path="changelog" rows="10" cssErrorClass="errorField" />
			</p>
			<p>
				<form:label path="type" for="type"><spring:message code="model.update.type" />:</form:label>
				<form:errors path="update.type" class="error" />
				<form:select path="type" cssErrorClass="errorField">
		        	<c:forEach items="${updateTypes}" var="type">
						<form:option value="${type}"><spring:message code="enum.EUpdateStrategy.${type}" /></form:option>
					</c:forEach>
				</form:select>
			</p>
			<p>
				<form:label for="file" path="file"><spring:message code="model.update.file" />:</form:label>
				<form:errors path="update.file" class="error" />
		        <form:input path="file" type="file" cssErrorClass="errorField" />
			</p>
			<p>
				<form:label path="relativePath" for="relativePath"><spring:message code="model.update.relativePath.long" />:</form:label>
				<form:errors path="update.relativePath" class="error" />
				<form:input path="relativePath" cssErrorClass="errorField" />
			</p>
			<p>
				<form:label path="updaterCommand" for="updaterCommand"><spring:message code="model.update.updaterCommand.long" />:</form:label>
				<form:errors path="update.updaterCommand" class="error" />
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