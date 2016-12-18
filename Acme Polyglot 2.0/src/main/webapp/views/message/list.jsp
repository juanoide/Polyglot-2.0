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


<!-- Listing messages -->
<h1>
	<jstl:out value="${messages[0].folder.name }"></jstl:out>
</h1>

<display:table name="messages" id="row" class="displaytag" requestURI="message/list.do" pagesize="5" keepStatus="true" >
	
	<spring:message	code="message.title"  var="title"/>
	<display:column property="title" title="${title}" sortable="true" />
	
	<spring:message	code="message.text"  var="text"/>
	<display:column property="text" title="${text}" sortable="true" />
	
	<spring:message	code="message.moment"  var="moment"/>
	<display:column property="moment" title="${moment}" sortable="true" />
	
	<spring:message	code="message.sender"  var="sender"/>
	<display:column property="sender.name" title="${sender}" sortable="true" />
	
	<spring:message	code="message.recipient"  var="recipient"/>
	<display:column property="recipient.name" title="${recipient}" sortable="true" />
	
		<spring:message code="message.links" var="titlelinks" />
	<display:column title="${titlelinks}" sortable="false">
		<jstl:forEach var="links" items="${row.links}">
			
<a href="<jstl:out value="${links}" />"><jstl:out value="${links}" /></a>
			&nbsp;
		
		
		</jstl:forEach>
	</display:column>
	
		<spring:message code="message.tags" var="title" />
	<display:column title="${title}" sortable="false">
		<jstl:forEach var="tag" items="${row.tag}">
			
				<jstl:out value="${tag}" />
		&nbsp;
		
		
		</jstl:forEach>
	</display:column>
	
	<spring:message	code="message.manage" var="management"/>
	<display:column title="${management}">
			<a href='message/delete.do?messageId=<jstl:out value="${row.id}"/>'>
				<spring:message	code="message.delete" />
			</a>
	</display:column>
	
	<jstl:if test="${flagged==false}">
	<spring:message	code="message.flagHeader" var="flagHeader"/>
	<display:column title="${flagHeader}">
			<a href='message/flag.do?messageId=<jstl:out value="${row.id}"/>'>
				<spring:message	code="message.flag" />
			</a>
	</display:column>
	</jstl:if>
	
	
	</display:table>