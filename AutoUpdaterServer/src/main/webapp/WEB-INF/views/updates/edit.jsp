<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<title><spring:message code="repository.name" /> - <spring:message code="model.update.edit" arguments="${update.thePackage.program.name},${update.thePackage.name}" /></title>
	<link rel="stylesheet" href="<c:url value="/resources/css/default.css" />" type="text/css" />
</head>
<body>
	<div id="breadcrumbs">
		<p>
			<a href="<c:url value="/updates/${update.thePackage.id}" />"><spring:message code="navigation.back" /></a>
		</p>
	</div>
 
	<h1><spring:message code="model.update.edit" arguments="${update.thePackage.program.name},${update.thePackage.name}" /></h1>
	 
	<fieldset>
	<legend><spring:message code="model.update.edit" arguments="${update.thePackage.program.name},${update.thePackage.name}" /></legend>
	<c:url value="/updates/edit" var="actionURL" />
	<form:form modelAttribute="update" method="POST" enctype="multipart/form-data" commandName="update" action="${actionURL}">
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
			<input name="send" type="submit" value="<spring:message code="navigation.edit" />" />
		</p>
	</form:form>
	</fieldset>
</body>
</html>