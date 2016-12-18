

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
	<security:authorize access="!hasRole('POLYGLOT')">
<form:form requestURI="${requestURI}" modelAttribute="description">
<input type="text" id="textSearch" value="">
<input type="button" id="buttonSearch" value="<spring:message code="languageExchange.search"/>"/>
<fieldset>
<acme:selectForID code="languageExchange.descriptionLanguage" items="${languages}" itemLabel="code" itemValue="id" id="language"/> 
<script type="text/javascript">
$(document).ready(function() {
    $("form").keypress(function(e) {
        if (e.which == 13) {
            return false;
        }
    });
});
</script>
<script type="text/javascript">
	$(document).ready(function(){
		$("#buttonSearch").click(function(){
			window.location.replace('languageExchange/search.do?entrante=' + $("#textSearch").val()+'&&languageId='+ language.value);
		});

	});
</script>
</fieldset>
</form:form>
</security:authorize>
<security:authorize access="hasRole('POLYGLOT')">
<script type="text/javascript">
$(document).ready(function() {
    $("form").keypress(function(e) {
        if (e.which == 13) {
            return false;
        }
    });
});
</script>
<form:form requestURI="${requestURI}" modelAttribute="languageExchange">
<input type="text" id="textSearch2" value="">
<input type="button" id="buttonSearch2" value="<spring:message code="languageExchange.search"/>"/>

<fieldset>
<acme:selectForID code="languageExchange.descriptionLanguage" items="${languages}" itemLabel="code" itemValue="id" id="language"/> 


<script type="text/javascript">
	$(document).ready(function(){
		$("#buttonSearch2").click(function(){
			window.location.replace('languageExchange/polyglot/search.do?entrante=' + $("#textSearch2").val()+'&&languageId='+ language.value);
		});

	});
</script>
	</fieldset>
</form:form>
</security:authorize>

<display:table name="languageExchanges" pagesize="5" class="displaytag" requestURI="${requestURI}" id="row">

	<acme:columnWithFormatDate code="languageExchange.date" property="date" />
	<spring:message code="languageExchange.place" var="placevar"></spring:message>

	<display:column title="${placevar}" >
	<a href="${row.place}">${row.place}</a>
	</display:column>
	
	
		<spring:message code="descriptions.list" var="descriptions.list"/>
		<display:column titleKey="descriptions.list" >

			
			
			<security:authorize access="isAnonymous()">	
		
		<jstl:forEach items="${row.descriptions}" var="description">
			 <jstl:out value="${language}" /> <a href="description/languageExchange.do?languageExchangeId=${row.id}&&languageId=${description.language.id}">
		 ${description.language.code}</a>	
			</jstl:forEach>
			</security:authorize>
			
			
				<security:authorize access="isAuthenticated()">
				
			<jstl:forEach items="${row.descriptions}" var="description">
		 <jstl:out value="${language}" /> <a href="description/languageExchange/polyglot.do?languageExchangeId=${row.id}&&languageId=${description.language.id}">
		 ${description.language.code}</a>	
			</jstl:forEach>
			
			</security:authorize>
			
			
		</display:column>
		
			<spring:message code="languageExchange.ExpectedTalk" var="expectedtalkes"/>
		<display:column title="${expectedtalkes}" >

					
			<jstl:forEach items="${row.expectedTalks}" var="expectedTalk">
		 <jstl:out value="${language}" />
		 ${expectedTalk.language.code}	
			</jstl:forEach>
			
		
			
		</display:column>
		
		
		
		<spring:message code="languageExchange.descriptions" var="descriptiones"></spring:message>

	<display:column title="${descriptiones}" >

	<a href="languageExchange/electionLanguage.do?languageExchangeId=${row.id}"><spring:message
							code="languageExchange.descriptions" /></a>
	</display:column>

	<security:authorize access="hasRole('POLYGLOT')">
		<spring:message code="languageExchange.join" var="joines"></spring:message>
	<display:column title="${joines}"  sortable="true" >
		<jstl:forEach items="${row.joinExchanges}" var="joinExchanges">
	
		 <jstl:if test="${polyglotprincipal == joinExchanges.polyglot}">
		 <jstl:set var="variable" value= "true" />
		
		</jstl:if>
		 		 
			</jstl:forEach>
				
			<jstl:if test="${variable == true}">
				<input type="button" value=<spring:message code="languageExchange.unjoin" />   
			onclick="location='languageExchange/polyglot/unjoin.do?languageExchangeId=${row.id}'" />
	
		 
			
			</jstl:if>
			
			<jstl:if test="${variable == false}">
		<input type="button" value=<spring:message code="languageExchange.join" />   
			onclick="location='languageExchange/polyglot/join.do?languageExchangeId=${row.id}'" />
		
			
			</jstl:if>
			<jstl:set var="variable" value= "false" />
	</display:column>
</security:authorize>



<security:authorize access="hasRole('POLYGLOT')">
		<spring:message code="languageExchange.edit" var="edites"></spring:message>
	<display:column title="${edites}" >
	
		 <jstl:if test="${polyglotprincipal == row.polyglotOrganise}">
		 <jstl:set var="isMine" value= "true" />
		
		</jstl:if>
		 		 
				
			<jstl:if test="${isMine == true}">
					<input type="button" value=<spring:message code="languageExchange.edit" />   
			onclick="location='languageExchange/polyglot/edit.do?languageExchangeId=${row.id}'" />
	
		 
			
			</jstl:if>
			
			<jstl:if test="${isMine == false}">
	<spring:message code="languageExchange.editNo"></spring:message>
			
			</jstl:if>
			<jstl:set var="isMine" value= "false" />
	</display:column>
</security:authorize>
<security:authorize access="hasRole('POLYGLOT')">


	<spring:message code="languageExchange.descriptions.add" var="descri"/>   
	<display:column title="${descri}" >

	
		 <jstl:if test="${polyglotprincipal == row.polyglotOrganise}">
		 <jstl:set var="isMine" value= "true" />
		
		</jstl:if>
		 		 
				
			<jstl:if test="${isMine == true}">
				<input type="button" value=<spring:message code="languageExchange.descriptions.add" />   
			onclick="location='languageExchange/polyglot/addDescription.do?languageExchangeId=${row.id}'" />
	
		 
			
			</jstl:if>
			
			<jstl:if test="${isMine == false}">
	<spring:message code="languageExchange.editNo"></spring:message>
			
			</jstl:if>
			<jstl:set var="isMine" value= "false" />
	</display:column>
	

	


	

		<spring:message code="languageExchange.cancel" var="cancele"/>   
	<display:column title="${cancele}" >

	
		 <jstl:if test="${polyglotprincipal == row.polyglotOrganise}">
		 <jstl:set var="isMine" value= "true" />
		
		</jstl:if>
		 		 
				
			<jstl:if test="${isMine == true}">
				<input type="button" value=<spring:message code="languageExchange.cancel" />   
			onclick="location='languageExchange/polyglot/cancel.do?languageExchangeId=${row.id}'" />
	
		 
			
			</jstl:if>
			
			<jstl:if test="${isMine == false}">
	<spring:message code="languageExchange.editNo"></spring:message>
			
			</jstl:if>
			<jstl:set var="isMine" value= "false" />
	</display:column>
	

	
</security:authorize>


	</display:table>
	
	