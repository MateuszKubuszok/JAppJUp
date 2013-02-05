<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page session="false" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<title><spring:message code="repository.name" /> - <tiles:insertAttribute name="title" /></title>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<link rel="stylesheet" href="<c:url value="/resources/css/default.css" />" type="text/css" />
	<script type="text/javascript" src="<c:url value="/resources/js/jquery-1.9.1.js" />"></script>
	<script type="text/javascript" src="<c:url value="/resources/js/jquery-tablesorter.js" />"></script>
</head>
<body>
	<div id="breadcrumbs">
		<tiles:insertAttribute name="breadcrumbs" />
	</div>

	<h1><tiles:insertAttribute name="title" /></h1>
	
	<tiles:insertAttribute name="content" />
</body>
</html>