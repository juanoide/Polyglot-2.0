

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


<display:table name="banners" pagesize="5" class="displaytag"
	requestURI="${requestURI}" id="row">

	<acme:column code="sponsorship.img" property="img" />
	<acme:column code="sponsorship.CodeLanguage" property="language.code" />
	
	
	




<spring:message code="banner.edit" var="edites"></spring:message>
	<display:column title="${edites}">
	
		
	<input type="button" value=<spring:message code="edit" />   
			onclick="location='banner/sponsorship/agent/edit.do?sponsorshipId=${sponsorship.id}&&bannerId=${row.id}'" />
	
		 
	</display:column>

<spring:message code="banner.delete" var="deletes"></spring:message>
	<display:column title="${deletes}" >
	
		
		<input type="button" value=<spring:message code="banner.delete" />   
			onclick="location='banner/sponsorship/agent/delete.do?sponsorshipId=${sponsorship.id}&&languageId=${language.id}&&bannerId=${row.id}'" />
	
		 
			
		
	</display:column>


	
</display:table>
