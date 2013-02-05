<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page session="false" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>

<spring:message code="model.program.add" var="title" />

<c:set var="breadcrumbs">
	<p>
		<a href="<c:url value="/programs" />"><spring:message code="navigation.back" /></a>
	</p>
</c:set>

<c:set var="content">
	<fieldset>
		<legend><spring:message code="model.program.add" /></legend>
		<form:form modelAttribute="program" method="POST" commandName="program">
			<p>
				<form:label path="name" for="name"><spring:message code="model.program.name" />:</form:label>
				<form:errors path="name" class="error" />
				<form:input path="name" cssErrorClass="errorField" />
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