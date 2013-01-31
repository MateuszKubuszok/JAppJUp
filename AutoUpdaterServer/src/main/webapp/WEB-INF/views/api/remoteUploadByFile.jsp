<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page session="false" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<title>Add New Update by direct upload</title>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<link rel="stylesheet" href="<c:url value="/resources/css/default.css" />" type="text/css" />
</head>
<body>
	<div id="breadcrumbs">
		<p>
			<a href="<c:url value="/api/upload_file" />">New remote upload by direct file upload</a>
		</p>
	</div>

	<h1>Add New Update by direct upload</h1>
	
	<fieldset>
	<legend>New Update</legend>
	<c:url value="/api/upload_file" var="actionURL" />
	<form:form modelAttribute="remoteUpdateUpload" method="POST" enctype="multipart/form-data" commandName="remoteUpdateUpload" action="${actionURL}">
		<p>
			<form:label path="username" for="username">Username:</form:label>
			<form:errors path="username" class="error" />
			<form:errors path="update.uploader" class="error" />
			<form:input path="username"/>
		</p>
		<p>
			<form:label path="password" for="password">Password:</form:label>
			<form:errors path="password" class="error" />
			<form:password path="password"/>
		</p>
		<p>
			<form:label path="program" for="program">Program's name:</form:label>
			<form:errors path="program" class="error" />
			<form:input path="program"/>
		</p>
		<p>
			<form:label path="thePackage" for="thePackage">Package's name:</form:label>
			<form:errors path="thePackage" class="error" />
			<form:errors path="update.thePackage" class="error" />
			<form:input path="thePackage"/>
		</p>
		<p>
			<form:label path="version" for="version">Version:</form:label>
			<form:errors path="update.version" class="error" />
			<form:input path="version"/>
		</p>
		<p>
			<form:label path="developmentVersion" for="developmentVersion">Development version:</form:label>
			<form:errors path="update.developmentVersion" class="error" />
			<form:checkbox path="developmentVersion" />
		</p>
		<p>
			<form:label path="changelog" for="changelog">Changelog:</form:label>
			<form:errors path="update.changelog" class="error" />
			<form:textarea path="changelog" rows="10" />
		</p>
		<p>
			<form:label path="type" for="type">Type:</form:label>
			<form:errors path="update.type" class="error" />
			<form:select path="type">
	        <form:options items="${updateTypes}" />
			</form:select>
		</p>
		<p>
			<form:label for="file" path="file">File:</form:label>
			<form:errors path="update.file" class="error" />
	        <form:input path="file" type="file" />
		</p>
		<p>
			<form:label path="relativePath" for="relativePath">Path to extract/copy update relative to programs main directory (optional):</form:label>
			<form:errors path="update.relativePath" class="error" />
			<form:input path="relativePath" />
		</p>
		<p>
			<form:label path="updaterCommand" for="updaterCommand">Command that should be called: after Unzip/Copy (optional) or as Execution command (required):</form:label>
			<form:errors path="update.updaterCommand" class="error" />
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
			<input name="send" type="submit" value="Upload" />
		</p>
	</form:form>
	</fieldset>
</body>
</html>