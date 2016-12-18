

<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@ taglib prefix="acme" tagdir="/WEB-INF/tags" %>


<display:table name="actores" pagesize="5" class="displaytag" requestURI="${requestURI}" id="row">

	

		<acme:column code="administrator.name" property="name" />
	<acme:column code="administrator.surname" property="surname" />
	
	
		
		
		
	
		<spring:message code="administrator.Banned" var="banes"></spring:message>
	<display:column title="${banes}"  sortable="true" >
	
	
		 <jstl:if test="${row.userAccount.active == true}">
		 
				<input type="button" value=<spring:message code="administrator.Banner" />   
					onclick="location='administrator/ban/disable.do?actorId=${row.id}'" />
			
		
		</jstl:if>
		 		 
			 <jstl:if test="${row.userAccount.active == false}">
		 
				<input type="button" value=<spring:message code="administrator.Enabled" />   
		onclick="location='administrator/ban/enable.do?actorId=${row.id}'" />
		
		</jstl:if>
	</display:column>



	</display:table>