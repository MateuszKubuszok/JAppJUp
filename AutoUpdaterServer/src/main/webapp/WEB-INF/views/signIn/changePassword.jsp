<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page session="false" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>

<spring:message code="navigation.changepw" var="title" />

<c:set var="breadcrumbs">
	<p>
		<a href="<c:url value="/users" />"><spring:message code="navigation.users" /></a>
		| <a href="<c:url value="/programs" />"><spring:message code="navigation.programs" /></a>
	</p>
</c:set>

<c:set var="content">
	<fieldset>
		<legend><spring:message code="navigation.changepw" /></legend>
		<form:form modelAttribute="passwordEditionCommand" method="POST" commandName="passwordEditionCommand">
			<form:hidden path="userId" />
			<p>
				<form:label path="currentPassword" for="currentPassword"><spring:message code="model.user.currentPassword" />:</form:label>
				<form:errors path="currentPassword" class="error" />
				<form:password path="currentPassword" showPassword="false" cssErrorClass="errorField" />
			</p>
			<p>
				<form:label path="password" for="password"><spring:message code="model.user.password" />:</form:label>
				<form:errors path="password" class="error" />
				<form:password path="password" showPassword="false" cssErrorClass="errorField" />
			</p>
			<p>
				<form:label path="confirmPassword" for="confirmPassword"><spring:message code="model.user.confirmPassword" />:</form:label>
				<form:errors path="confirmPassword" class="error" />
				<form:password path="confirmPassword" showPassword="false" cssErrorClass="errorField" />
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