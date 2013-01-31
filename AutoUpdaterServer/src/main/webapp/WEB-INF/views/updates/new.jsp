<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page session="false" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<title><spring:message code="repository.name" /> - <spring:message code="model.update.add" arguments="${newUpdate.thePackage.program.name},${newUpdate.thePackage.name}" /></title>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<link rel="stylesheet" href="<c:url value="/resources/css/default.css" />" type="text/css" />
</head>
<body>
	<div id="breadcrumbs">
		<p>
			<a href="<c:url value="/updates/${newUpdate.thePackage.id}" />"><spring:message code="navigation.back" /></a>
		</p>
	</div>

	<h1><spring:message code="model.update.add" arguments="${newUpdate.thePackage.program.name},${newUpdate.thePackage.name}" /></h1>
	
	<fieldset>
		<legend><spring:message code="model.update.add" arguments="${newUpdate.thePackage.program.name},${newUpdate.thePackage.name}" /></legend>
		<c:url value="/updates/add" var="actionURL" />
		<form:form modelAttribute="newUpdate" method="POST" enctype="multipart/form-data" commandName="newUpdate" action="${actionURL}">
			<p>
				<form:label path="version" for="version"><spring:message code="model.update.version" />:</form:label>
				<form:errors path="version" class="error" />
				<form:input path="version" />
			</p>
			<p>
				<form:label path="developmentVersion" for="developmentVersion"><spring:message code="model.update.developmentVersion" />:</form:label>
				<form:errors path="developmentVersion" class="error" />
				<form:checkbox path="developmentVersion" />
			</p>
			<p>
				<form:label path="changelog" for="changelog"><spring:message code="model.update.changelog" />:</form:label>
				<form:errors path="changelog" class="error" />
				<form:textarea path="changelog" rows="10" />
			</p>
			<p>
				<form:label path="type" for="type"><spring:message code="model.update.type" />:</form:label>
				<form:errors path="type" class="error" />
				<form:select path="type">
		        	<form:options items="${updateTypes}" />
				</form:select>
			</p>
			<p>
				<form:label for="file" path="file"><spring:message code="model.update.file" />:</form:label>
				<form:errors path="file" class="error" />
		        <form:input path="file" type="file" />
			</p>
			<p>
				<form:label path="relativePath" for="relativePath"><spring:message code="model.update.relativePath.long" />:</form:label>
				<form:errors path="relativePath" class="error" />
				<form:input path="relativePath" />
			</p>
			<p>
				<form:label path="updaterCommand" for="updaterCommand"><spring:message code="model.update.updaterCommand.long" />:</form:label>
				<form:errors path="updaterCommand" class="error" />
				<form:input path="updaterCommand" />
				
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
</body>
</html>