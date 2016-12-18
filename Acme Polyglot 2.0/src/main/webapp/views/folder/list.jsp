<%--
 * action-1.jsp
 *
 * Copyright (C) 2013 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the 
 * TDG Licence, a copy of which you may download from 
 * http://www.tdg-seville.info/License.html
 --%>

<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>



<!-- Listing folders -->

<display:table name="folders" id="row" class="displaytag" requestURI="folder/list.do" pagesize="5" keepStatus="false" >
	
	<spring:message	code="folder.name"  var="name"/>
	<display:column title="${name}">
		<a href='message/list.do?folderId=<jstl:out value="${row.id}"/>'>
			<jstl:out value="${row.name}"/></a>
	</display:column>
	
	<jstl:if test="${fn: length(folders)>4}">
		<spring:message	code="folder.edit" var="edit"/>
		<display:column title="${edit}">
			<jstl:if test="${row.systemFolder==false}">
				<a href='folder/edit.do?folderId=<jstl:out value="${row.id}"/>'>
					<spring:message	code="folder.edit" />
				</a>
			</jstl:if>
		</display:column>
		
		<spring:message	code="folder.delete" var="delete"/>
		<display:column title="${delete}">
			<jstl:if test="${row.systemFolder==false}">
				<a href='folder/delete.do?folderId=<jstl:out value="${row.id}"/>'>
					<spring:message	code="folder.delete" />
				</a>
			</jstl:if>
		</display:column>
	</jstl:if>
</display:table>
		


<br/>


<a href="message/send.do"><spring:message code="message.send"/></a>
&nbsp;
&nbsp;
	<security:authorize access="hasRole('POLYGLOT')">
<a href="message/send/languageExchange.do"><spring:message code="message.send"/>&nbsp;
<spring:message code="message.LanguageExchange"/></a>
&nbsp;
&nbsp;
</security:authorize>
<a href="folder/edit.do"><spring:message code="folder.create"/></a>
