

<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@taglib prefix="acme" tagdir="/WEB-INF/tags"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>


<form:form requestURI="${requestURI}" modelAttribute="description">
<fieldset>
<acme:selectForID code="languageExchange.descriptionLanguage" items="${languages}" itemLabel="code" itemValue="id" id="language"/> 
<br>
		<security:authorize access="isAnonymous()">	
		<input type="button" value=<spring:message code="languageExchange.descriptions" />   
		onclick="location='description/languageExchange.do?languageExchangeId=${languageExchange.id}&&languageId='+ language.value" />
		</security:authorize>
			
			
		<security:authorize access="isAuthenticated()">
		<input type="button" value=<spring:message code="languageExchange.descriptions" />   
		onclick="location='description/languageExchange/polyglot.do?languageExchangeId=${languageExchange.id}&&languageId='+ language.value" />
		</security:authorize>
			


	</fieldset>
</form:form>
