

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


<form:form requestURI="${requestURI}" modelAttribute="banner">
<fieldset>
<acme:selectForID code="sponsorship.descriptionLanguage" items="${languages}" itemLabel="code" itemValue="id" id="language"/> 
<br>
	
		<input type="button" value=<spring:message code="sponsorship.descriptions" />   
		onclick="location='banner/sponsorship/agent.do?sponsorshipId=${sponsorship.id}&&languageId='+ language.value" />
	
			

			


	</fieldset>
</form:form>
