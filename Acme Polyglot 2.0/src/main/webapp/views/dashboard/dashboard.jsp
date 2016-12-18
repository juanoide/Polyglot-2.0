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
<display:table name="allLanguages" pagesize="5" class="displaytag" requestURI="administrator/dashboard.do" id="row">
	
	<acme:column code="language.code" property="code" />
	
	
	<spring:message code="dashboard.Languages.expectedTalk" var="expected"/>
		<display:column title="${expected}" sortable="true">
	${row.expectedTalks.size()}
	</display:column>
	
</display:table>

<b><spring:message code="dashboard.Languages.polyglotOrganised" var="polyglotOrganisedTitle" /></b>
<display:table name="allPolyglot" pagesize="5" class="displaytag" requestURI="administrator/dashboard.do" id="row">
	
	<acme:column code="polyglot.name" property="name" />
	
	
	<spring:message code="dashboard.Languages.polyglotOrganisedCount" var="organised"/>
		<display:column title="${organised}" sortable="true">
	${row.organiseExchanges.size()}
	</display:column>
	
</display:table>

<b><spring:message code="dashboard.Languages.polyglotjoined" var="polyglotOrganisedTitle" /></b>
<display:table name="allPolyglot" pagesize="5" class="displaytag" requestURI="administrator/dashboard.do" id="row">
	
	<acme:column code="polyglot.name" property="name" />
	
	
	<spring:message code="dashboard.Languages.polyglotjoinedCount" var="joined"/>
		<display:column title="${joined}" sortable="true">
	${row.joinExchanges.size()}
	</display:column>
	
</display:table>
	
<b><spring:message code="dashboard.Languages.ExchangesSponsorship" var="ExchangesSponsorshipeee" /></b>
<display:table name="allLanguageExchanges" pagesize="5" class="displaytag" requestURI="administrator/dashboard.do" id="row">
	
	<acme:column code="languageExchange.date" property="date" />
	
	
	<spring:message code="dashboard.Languages.ExchangesSponsorship" var="ExchangesSponsorshipeee"/>
		<display:column title="${ExchangesSponsorshipeee}" sortable="true">
	${row.sponsorships.size()}
	</display:column>
	
</display:table>

<b><spring:message code="dashboard.Languages.polyglotCounterSponsor" var="polyglotCounterSponsorTitle" /></b>
<display:table name="allPolyglot" pagesize="5" class="displaytag" requestURI="administrator/dashboard.do" id="row">
	
	<acme:column code="polyglot.name" property="name" />
	
	
	<spring:message code="dashboard.Languages.polyglotCounterSponsor" var="counterSponsor"/>
		<display:column title="${counterSponsor}" sortable="true">
	
	
	
		<jstl:forEach items="${row.organiseExchanges}" var="languageExchange">
	
		 <jstl:if test="${languageExchange.sponsorships.size()>0}">
		 <jstl:set var="numerica" value="${numerica+1}" />
		
		</jstl:if>
		 		 
			</jstl:forEach>
				<jstl:out value="${numerica}"/>
	 <jstl:set var="numerica" value="${0}" />
	</display:column>
	</display:table>
	
	<b><spring:message code="dashboard.LanguagesExchangeSponsorizado" var="SponsorTitle" /></b>
<display:table name="numerica" pagesize="5" class="displaytag" requestURI="administrator/dashboard.do" id="row">
	<spring:message code="dashboard.LanguagesExchangeSponsorizado.AVG" var="avg"/>
		<display:column title="${avg}">
	
	
	
	
				<jstl:out value="${avgDouble}"/>

	</display:column>
	
		<spring:message code="dashboard.LanguagesExchangeSponsorizado.MAX" var="max"/>
		<display:column title="${max}" sortable="true">
	
	
	
		<jstl:out value="${maxInteger}"/>
	</display:column>
	
	
		<spring:message code="dashboard.LanguagesExchangeSponsorizado.MIN" var="min"/>
		<display:column title="${min}" sortable="true">
	
	
	
		
				<jstl:out value="${minInteger}"/>

	</display:column>
	
	
</display:table>


