<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page session="false" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<title>Add New Update</title>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<link rel="stylesheet" href="<c:url value="/resources/css/default.css" />" type="text/css" />
</head>
<body>
	<div id="breadcrumbs">
		<p>
			<a href="<c:url value="/updates/${newUpdate.thePackage.id}" />">Back</a>
		</p>
	</div>

	<h1>Add New Update for ${newUpdate.thePackage.program.name}/${newUpdate.thePackage.name}</h1>
	
	<fieldset>
	<legend>New Update</legend>
	<c:url value="/updates/add" var="actionURL" />
	<form:form modelAttribute="newUpdate" method="POST" enctype="multipart/form-data" commandName="newUpdate" action="${actionURL}">
		<p>
			<form:label path="version" for="version">Version:</form:label>
			<form:errors path="version" class="error" />
			<form:input path="version"/>
		</p>
		<p>
			<form:label path="developmentVersion" for="developmentVersion">Development version:</form:label>
			<form:errors path="developmentVersion" class="error" />
			<form:checkbox path="developmentVersion" />
		</p>
		<p>
			<form:label path="changelog" for="changelog">Changelog:</form:label>
			<form:errors path="changelog" class="error" />
			<form:textarea path="changelog" rows="10" />
		</p>
		<p>
			<form:label path="type" for="type">Type:</form:label>
			<form:errors path="type" class="error" />
			<form:select path="type">
	        <form:options items="${updateTypes}" />
			</form:select>
		</p>
		<p>
			<form:label for="file" path="file">File:</form:label>
			<form:errors path="file" class="error" />
	        <form:input path="file" type="file" />
		</p>
		<p>
			<form:label path="relativePath" for="relativePath">Path to extract/copy update relative to programs main directory (optional):</form:label>
			<form:errors path="relativePath" class="error" />
			<form:input path="relativePath" />
		</p>
		<p>
			<form:label path="updaterCommand" for="updaterCommand">Command that should be called: after Unzip/Copy (optional) or as Execution command (required):</form:label>
			<form:errors path="updaterCommand" class="error" />
			<form:input path="updaterCommand" />
		</p>
		<p>Allowed variables:</p>
		<ul>
			<li><b>{F}</b> - original filename</li>
			<li><b>{U}</b> - absolute path to uploaded file on Client's platform</li>
			<li><b>{I}</b> - program's installation directory</li>
			<li><b>{R}</b> - relative path (defined above)</li>
			<li><b>{T}</b> - target directory (installation_directory / relative_path)</li>
		</ul>
		<p>
			<input name="send" type="submit" value="Save" />
		</p>
	</form:form>
	</fieldset>
</body>
</html>