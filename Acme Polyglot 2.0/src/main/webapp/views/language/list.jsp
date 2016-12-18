

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


<display:table name="languages" pagesize="5" class="displaytag" requestURI="${requestURI}" id="row">

	<acme:columnWithFormatDate code="language.code" property="code" />

	
	
		<spring:message code="descriptions.list" var="descriptions.list"/>
		<display:column titleKey="descriptions.list" >

			
			
			
		
		<jstl:forEach items="${row.descriptionsOwners}" var="description">
			 <jstl:out value="${language}" /> <a href="description/language/admin.do?languageId=${row.id}&&languageDescriptionId=${description.language.id}">
		 ${description.language.code}</a>	
			</jstl:forEach>
	
			
			
		</display:column>
		
		
		
		<spring:message code="language.descriptions" var="descriptiones"></spring:message>

	<display:column title="${descriptiones}" >

	<a href="language/admin/electionLanguage.do?languageId=${row.id}"><spring:message
							code="language.descriptions" /></a>
	</display:column>


		<spring:message code="language.edit" var="edites"></spring:message>
	<display:column title="${edites}" >
	
		
					<input type="button" value=<spring:message code="language.edit" />   
			onclick="location='language/admin/edit.do?languageId=${row.id}'" />
	
		 

	</display:column>

	<spring:message code="language.descriptions.add" var="descri"/>   
	<display:column title="${descri}" >

	
	
				<input type="button" value=<spring:message code="language.descriptions.add" />   
			onclick="location='language/admin/addDescription.do?languageId=${row.id}'" />
	
		 
		
	</display:column>
	

	


	

		<spring:message code="language.delete" var="cancele"/>   
	<display:column title="${cancele}" >

	
	
				<input type="button" value=<spring:message code="language.delete" />   
			onclick="location='language/admin/delete.do?languageId=${row.id}'" />
	
		 
			
	</display:column>
	

	



	</display:table>
	
	