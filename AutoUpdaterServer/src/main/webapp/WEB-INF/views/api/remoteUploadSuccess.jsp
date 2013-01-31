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
			<a href="<c:url value="/api/upload_file" />">New remote upload by direct file upload</a>
		</p>
	</div>

	<h1>Update added successfully</h1>
	
	<fieldset>
		<legend>Update</legend>
		<p><b>Program</b>: ${remoteUpdateUpload.program}</p>
		<p><b>Package</b>: ${remoteUpdateUpload.thePackage}</p>
		<p><b>Version</b>: ${remoteUpdateUpload.update.version}</p>
		<p><b>Development version</b>: ${remoteUpdateUpload.update.developmentVersion}</p>
		<p><b>Changelog</b>: ${remoteUpdateUpload.update.changelog}</p>
		<p><b>Type</b>: ${remoteUpdateUpload.update.type}</p>
		<p><b>Relative path</b>: ${remoteUpdateUpload.update.relativePath}</p>
		<p><b>Updater command</b>: ${remoteUpdateUpload.update.updaterCommand}</p>
	</fieldset>
</body>
</html>