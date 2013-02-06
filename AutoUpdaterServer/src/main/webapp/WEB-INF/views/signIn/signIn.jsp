<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page session="false" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>

<spring:message code="navigation.signIn" var="title" />

<c:set var="breadcrumbs">
	<a href="<c:url value="/api/upload_file" />"><spring:message code="remoteUpload.quickUpload" /></a>
</c:set>

<c:set var="content">
	<fieldset>
		<legend><spring:message code="navigation.signIn" /></legend>
		<form method="post" action="<c:url value="/j_spring_security_check" />">
			<p>
				<label for="j_username"><spring:message code="model.user.username" />:</label>
				<c:if test="${not empty param.error}"> 
					<span class="error">Wrong credentials</span>
				</c:if>
				<input name="j_username" />
			</p>
			<p>
				<label for="j_password"><spring:message code="model.user.password" />:</label>
				<input name="j_password" type="password" />
			</p>
			<p>
				<label for="_spring_security_remember_me"><spring:message code="navigation.rememberMe" />:</label>
				<input type="checkbox" name="_spring_security_remember_me" />
			</p>
			<p>
				<input name="send" type="submit" value="<spring:message code="navigation.signIn" />" />
			</p>
		</form>
	</fieldset>
</c:set>

<tiles:insertDefinition name="default">
	<tiles:putAttribute name="title" value="${title}" />
	<tiles:putAttribute name="breadcrumbs" value="${breadcrumbs}" />
	<tiles:putAttribute name="content" value="${content}" />
</tiles:insertDefinition>