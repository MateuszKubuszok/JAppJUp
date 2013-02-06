<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page session="false" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>

<spring:message code="model.package.edit" arguments="${_package.program.name}" var="title" />

<c:set var="breadcrumbs">
	<a href="<c:url value="/packages/${_package.program.id}" />"><spring:message code="navigation.back" /></a>
</c:set>

<c:set var="content">
	<fieldset>
		<legend><spring:message code="model.package.edit" arguments="${_package.program.name}" /></legend>
		<c:url value="/packages/edit" var="actionURL" />
		<form:form modelAttribute="_package" method="POST" commandName="_package" action="${actionURL}">
			<p>
				<form:label path="name" for="name"><spring:message code="model.package.name" />:</form:label>
				<form:errors path="name" class="error" />
				<form:input path="name" cssErrorClass="errorField" />
			</p>
			<p>
				<input name="send" type="submit" value="<spring:message code="navigation.edit" />" />
			</p>
		</form:form>
	</fieldset>
</c:set>

<tiles:insertDefinition name="default">
	<tiles:putAttribute name="title" value="${title}" />
	<tiles:putAttribute name="breadcrumbs" value="${breadcrumbs}" />
	<tiles:putAttribute name="content" value="${content}" />
</tiles:insertDefinition>