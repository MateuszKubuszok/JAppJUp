<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page session="false" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>

<spring:message code="model.user.edit" var="title" />

<c:set var="breadcrumbs">
	<p>
		<a href="<c:url value="/users" />"><spring:message code="navigation.back" /></a>
	</p>
</c:set>

<c:set var="content">
	<fieldset>
		<legend><spring:message code="model.user.edit" /></legend>
		<c:url value="/users/edit" var="actionURL" />
		<form:form modelAttribute="user" method="POST" commandName="user" action="${actionURL}">
			<p>
				<form:label path="username" for="username"><spring:message code="model.user.username" />:</form:label>
				<form:errors path="username" class="error" />
				<form:input path="username" cssErrorClass="errorField" />
			</p>
			<p>
				<form:label path="name" for="name"><spring:message code="model.user.name" />:</form:label>
				<form:errors path="name" class="error" />
				<form:input path="name" cssErrorClass="errorField" />
			</p>
			<p>
				<form:label path="userType" for="userType"><spring:message code="model.user.type" />:</form:label>
				<form:errors path="userType" class="error" />
				<form:select path="userType" cssErrorClass="errorField">
		        <c:forEach items="${types}" var="type">
					<form:option value="${type}"><spring:message code="enum.EUserType.${type}" /></form:option>
				</c:forEach>
				</form:select>
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