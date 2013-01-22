<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page session="false" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<title>Add New Update</title>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
</head>
<body>
	<a href="<c:url value="/updates/${newUpdate.thePackage.id}" />">Back</a>

	<h1>Add New Update for ${newUpdate.thePackage.program.name}/${newUpdate.thePackage.name}</h1>
	
	<fieldset>
	<legend>New Update</legend>
	<c:url value="/updates/add" var="actionURL" />
	<form:form modelAttribute="newUpdate" method="POST" enctype="multipart/form-data" commandName="newUpdate" action="${actionURL}">
		<p>
			<form:errors path="version" cssStyle="color : red;" />
			<form:label path="version" for="version">Version:</form:label>
			<form:input path="version"/>
		</p>
		<p>
			<form:errors path="developmentVersion" cssStyle="color : red;" />
			<form:label path="developmentVersion" for="developmentVersion">Development version:</form:label>
			<form:checkbox path="developmentVersion" />
		</p>
		<p>
			<form:errors path="changelog" cssStyle="color : red;" />
			<form:label path="changelog" for="changelog">Changelog:</form:label>
			<form:textarea path="changelog" rows="10" />
		</p>
		<p>
			<form:errors path="type" cssStyle="color : red;" />
			<form:label path="type" for="type">Type:</form:label>
			<form:select path="type">
	        <form:options items="${updateTypes}" />
			</form:select>
		</p>
		<p>
			<form:errors path="file" cssStyle="color : red;" />
			<form:label for="file" path="file">File:</form:label>
	        <form:input path="file" type="file" />
		</p>
		<p>
			<form:errors path="relativePath" cssStyle="color : red;" />
			<form:label path="relativePath" for="relativePath">Path to extract/copy update relative to programs main directory (optional):</form:label>
			<form:input path="relativePath" />
		</p>
		<p>
			<form:errors path="updaterCommand" cssStyle="color : red;" />
			<form:label path="updaterCommand" for="updaterCommand">Command that should be called: after Unzip/Copy (optional) or as Execution command (required):</form:label>
			<form:input path="updaterCommand" />
			<br />
			Allowed variables:
			<ul>
				<li><b>{F}</b> - original filename</li>
				<li><b>{U}</b> - absolute path to uploaded file on Client's platform</li>
				<li><b>{I}</b> - program's installation directory</li>
				<li><b>{R}</b> - relative path (defined above)</li>
				<li><b>{T}</b> - target directory (installation_directory / relative_path)</li>
			</ul>
		</p>
		<p>
			<input name="send" type="submit" value="Send" />
		</p>
	</form:form>
	</fieldset>
</body>
</html>