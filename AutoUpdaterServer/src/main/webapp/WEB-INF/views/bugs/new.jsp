<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page session="false" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>

<spring:message code="model.bug.add" arguments="${bug.program.name}" var="title" />

<c:set var="breadcrumbs">
	<p>
		<a href="<c:url value="/bugs/${bug.program.id}" />">Back</a>
	</p>
</c:set>

<c:set var="content">
	<fieldset>
		<legend><spring:message code="model.bug.add" arguments="${bug.program.name}" /></legend>
		<c:url value="/bugs/add" var="actionURL" />
		<form:form modelAttribute="bug" method="POST" commandName="bug" action="${actionURL}">
			<p>
				<form:label path="description" for="description"><spring:message code="model.bug.description"  />:</form:label>
				<form:errors path="description" class="error" />
				<form:textarea path="description" cssErrorClass="errorField" />
			</p>
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