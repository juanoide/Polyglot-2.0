

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

<jstl:out value="${banner}"/>
<display:table name="descriptions" pagesize="5" class="displaytag"
	requestURI="${requestURI}" id="row">
	<spring:message code="description.links" var="titlelinks" />

	<acme:column code="description.title" property="title" />

	<acme:column code="description.text" property="text" />
	
	
	
		<spring:message code="description.links" var="titlelinks" />
	<display:column title="${titlelinks}" sortable="false">
		<jstl:forEach var="links" items="${row.links}">
			
<a href="<jstl:out value="${links}" />"><jstl:out value="${links}" /></a>
			&nbsp;
		
		
		</jstl:forEach>
	</display:column>
	
		<spring:message code="description.tag" var="title" />
	<display:column title="${title}" sortable="false">
		<jstl:forEach var="tag" items="${row.tag}">
			
				<jstl:out value="${tag}" />
		&nbsp;
		
		
		</jstl:forEach>
	</display:column>
	
	

<security:authorize access="hasRole('ADMIN')">





<spring:message code="description.edit" var="edites"></spring:message>
	<display:column title="${edites}">
	
		
	<input type="button" value=<spring:message code="description.edit" />   
			onclick="location='description/language/admin/edit.do?languageId=${language.id}&&descriptionId=${row.id}'" />
	
		 
	</display:column>

<spring:message code="description.delete" var="deletes"></spring:message>
	<display:column title="${deletes}" >
	
		
		<input type="button" value=<spring:message code="description.delete" />   
			onclick="location='description/language/admin/delete.do?languageId=${language.id}&&languageDescriptionId=${languageDescription.id}&&descriptionId=${row.id}'" />
	
		 
			
		
	</display:column>


</security:authorize>

<security:authorize access="hasRole('POLYGLOT')">





<spring:message code="description.edit" var="edites"></spring:message>
	<display:column title="${edites}">
	
		 <jstl:if test="${polyglotprincipal == languageExchange.polyglotOrganise}">
		 <jstl:set var="isMine" value= "true" />
		
		</jstl:if>
		 		 
				
			<jstl:if test="${isMine == true}">
	<input type="button" value=<spring:message code="description.edit" />   
			onclick="location='description/languageExchange/polyglot/edit.do?languageExchangeId=${languageExchange.id}&&descriptionId=${row.id}'" />
	
		 
			
			</jstl:if>
			
			<jstl:if test="${isMine == false}">
	<spring:message code="languageExchange.editNo"></spring:message>
			
			</jstl:if>
			<jstl:set var="isMine" value= "false" />
	</display:column>

<spring:message code="description.delete" var="deletes"></spring:message>
	<display:column title="${deletes}" >
	
		 <jstl:if test="${polyglotprincipal == languageExchange.polyglotOrganise}">
		 <jstl:set var="isMine" value= "true" />
		
		</jstl:if>
		 		 
				
			<jstl:if test="${isMine == true}">
		<input type="button" value=<spring:message code="description.delete" />   
			onclick="location='description/languageExchange/polyglot/delete.do?languageExchangeId=${languageExchange.id}&&languageId=${language.id}&&descriptionId=${row.id}'" />
	
		 
			
			</jstl:if>
			
			<jstl:if test="${isMine == false}">
	<spring:message code="languageExchange.editNo"></spring:message>
			
			</jstl:if>
			<jstl:set var="isMine" value= "false" />
	</display:column>


</security:authorize>



<security:authorize access="hasRole('AGENT')">





<spring:message code="description.edit" var="edites"></spring:message>
	<display:column title="${edites}">
	
		
	<input type="button" value=<spring:message code="description.edit" />   
			onclick="location='description/sponsorship/agent/edit.do?sponsorshipId=${sponsorship.id}&&descriptionId=${row.id}'" />
	
		 
	</display:column>

<spring:message code="description.delete" var="deletes"></spring:message>
	<display:column title="${deletes}" >
	
		
		<input type="button" value=<spring:message code="description.delete" />   
			onclick="location='description/sponsorship/agent/delete.do?sponsorshipId=${sponsorship.id}&&languageId=${language.id}&&descriptionId=${row.id}'" />
	
		 
			
		
	</display:column>


</security:authorize>


	
</display:table>
