<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ page session="false" %>
<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<title><spring:message code="repository.name" /> - <spring:message code="remoteUpload.newFileUpload" /></title>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<link rel="stylesheet" href="<c:url value="/resources/css/default.css" />" type="text/css" />
</head>
<body>
	<div id="breadcrumbs">
		<p>
			<a href="<c:url value="/api/upload_file" />"><spring:message code="remoteUpload.newFileUpload" /></a>
		</p>
	</div>

	<h1><spring:message code="remoteUpload.successfulUpload" /></h1>
	
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
</body>
</html>