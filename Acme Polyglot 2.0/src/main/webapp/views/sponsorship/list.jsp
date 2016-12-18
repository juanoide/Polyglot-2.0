

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


<display:table name="sponsorships" pagesize="5" class="displaytag" requestURI="${requestURI}" id="row">

<acme:column code="sponsorship.id" property="id"  />
<acme:columnWithFormatDate code="sponsorship.languageExchange" property="languageExchange.date"  />
	<%--Inicio de los banner --%>
		<spring:message code="banners.list" var="banners.list"/>
		<display:column titleKey="banners.list" >

				
			<jstl:forEach items="${row.banners}" var="banner">
		 <jstl:out value="${language}" /> <a href="banner/sponsorship/agent.do?sponsorshipId=${row.id}&&languageId=${banner.language.id}">
		 ${banner.language.code}</a>	
			</jstl:forEach>
			
		</display:column>
		
		
		<spring:message code="sponsorship.banners" var="banneres"></spring:message>

	<display:column title="${banneres}" >

	<a href="sponsorship/banner/agent/electionLanguage.do?sponsorshipId=${row.id}"><spring:message
							code="sponsorship.banners" /></a>
	</display:column>






	<spring:message code="sponsorship.banners.add" var="bannereres"/>   
	<display:column title="${bannereres}" >

	
		 
				
		
				<input type="button" value=<spring:message code="sponsorship.banners.add" />   
			onclick="location='sponsorship/agent/addBanner.do?sponsorshipId=${row.id}'" />
	
		 
			
			
	</display:column>
	<%--fin de los banner --%>
	<%--Inicio de las descriptions --%>
	
		<spring:message code="descriptions.list" var="descriptions.list"/>
		<display:column titleKey="descriptions.list" >

				
			<jstl:forEach items="${row.descriptions}" var="description">
		 <jstl:out value="${language}" /> <a href="description/sponsorship/agent.do?sponsorshipId=${row.id}&&languageId=${description.language.id}">
		 ${description.language.code}</a>	
			</jstl:forEach>
			
		</display:column>
		
		
		<spring:message code="sponsorship.descriptions" var="descriptiones"></spring:message>

	<display:column title="${descriptiones}" >

	<a href="sponsorship/description/agent/electionLanguage.do?sponsorshipId=${row.id}"><spring:message
							code="sponsorship.descriptions" /></a>
	</display:column>




	<spring:message code="sponsorship.descriptions.add" var="descri"/>   
	<display:column title="${descri}" >

	
		 
				
		
				<input type="button" value=<spring:message code="sponsorship.descriptions.add" />   
			onclick="location='sponsorship/agent/addDescription.do?sponsorshipId=${row.id}'" />
	
		 
			
			
	</display:column>
	

	

	
		<spring:message code="sponsorship.edit" var="edites"></spring:message>
	<display:column title="${edites}" >
	
		
					<input type="button" value=<spring:message code="sponsorship.edit" />   
			onclick="location='sponsorship/agent/edit.do?sponsorshipId=${row.id}'" />
	
		 

	</display:column>

	

		<spring:message code="sponsorship.delete" var="deleteee"/>   
	<display:column title="${deleteee}" >

	
	
		 		 
				
		
				<input type="button" value=<spring:message code="sponsorship.delete" />   
			onclick="location='sponsorship/agent/delete.do?sponsorshipId=${row.id}'" />
	
		 
		
	</display:column>
	

	



	</display:table>
	
	