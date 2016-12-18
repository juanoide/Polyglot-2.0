<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@ taglib prefix="acme" tagdir="/WEB-INF/tags" %>

<form:form requestURI="${requestURI}"  modelAttribute="polyglotForm">
	<fieldset>
		<legend align="left">
			<spring:message code="polyglot.userAccount" />
		</legend>	
		<acme:textbox code="polyglot.userAccount.username" path="username"/>
		<acme:password code="polyglot.userAccount.password" path="password"/>
		<acme:password code="polyglot.userAccount.repeatPassword" path="repeatPassword"/>
	</fieldset>
	<fieldset>
		<legend align="left">
			<spring:message code="polyglot.personalInfo" />
		</legend>
			
		<acme:textbox code="polyglot.name" path="name"/>
		<acme:textbox code="polyglot.surname" path="surname"/>
		<acme:textbox code="polyglot.emailAddress" path="email"/>
		<acme:textbox code="polyglot.phoneNumber" path="phoneNumber"/>
	
		</fieldset>	
	<p><acme:checkbox code="polyglot.acceptConditions" path="valid"/>
	
	<a href="legalTerms/legalTerms.do"><spring:message
			code="polyglot.registrarion" /> </a>
	<br />
	<br />
	
	<input type="submit" name="save" value="<spring:message code="save"/>" />
	&nbsp;
	<input type="button" name="cancel" value="<spring:message code="return"/>" 
	onclick="javascript: window.location.replace('')"/>
</form:form>