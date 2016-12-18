<%--
 * action-1.jsp
 *
 * Copyright (C) 2013 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the 
 * TDG Licence, a copy of which you may download from 
 * http://www.tdg-seville.info/License.html
 --%>

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
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<b><spring:message code="dashboard.Languages.title" var="languagesTitle" /></b>
<display:table name="allLanguages" pagesize="5" class="displaytag" requestURI="administrator/dashboard2.do" id="row">
	
	<acme:column code="language.code" property="code" />
	
	
	<spring:message code="dashboard.LanguagesForMessage" var="message"/>
		<display:column title="${message}" sortable="true">
	${row.messages.size()}
	</display:column>
	
</display:table>

	
	
<display:table name="numerica" pagesize="5" class="displaytag" requestURI="administrator/dashboard2.do" id="row">
	<spring:message code="dashboard.messageFolder.AVG" var="avg"/>
		<display:column title="${avg}">
	
	
	
	
				<jstl:out value="${avgDouble}"/>

	</display:column>
	
		<spring:message code="dashboard.messageFolder.MAX" var="max"/>
		<display:column title="${max}" sortable="true">
	
	
	
		<jstl:out value="${maxInteger}"/>
	</display:column>
	
	
		<spring:message code="dashboard.messageFolder.MIN" var="min"/>
		<display:column title="${min}" sortable="true">
	
	
	
		
				<jstl:out value="${minInteger}"/>

	</display:column>
	
	
</display:table>

<display:table name="actores" pagesize="5" class="displaytag" requestURI="administrator/dashboard2.do" id="row">
	
	<acme:column code="dashboard.name" property="name" />
	
		<spring:message code="dashboard.Keywords" var="keyword" />
	<display:column title="${keyword}" sortable="false">
		<jstl:forEach var="search" items="${row.searchs}">
			
				<jstl:out value="${search.name}" />
		&nbsp;
		<jstl:out value="${search.count}" />
		<br>
		
		</jstl:forEach>
	</display:column>
	
	
	</display:table>
	
	


	<display:table name="searchs3" pagesize="5" class="displaytag" requestURI="administrator/dashboard2.do" >
    <acme:column code="dashboard.word" property="name" />
	<acme:column code="dashboard.count" property="count" />
   
</display:table>


	<!--  
	<display:table name="searchs2" pagesize="5" class="displaytag" requestURI="administrator/dashboard2.do" id="row">
	
	<acme:column code="dashboard.name" property="name" />
	<acme:column code="dashboard.count" property="count" />
		
	
	
	</display:table>
	-->

