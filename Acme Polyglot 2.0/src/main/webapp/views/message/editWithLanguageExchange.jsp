

<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="acme"	tagdir="/WEB-INF/tags"%>
<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
   
<form:form action="message/edit/languageExchange.do" modelAttribute="messageLanguageExchangeForm" >  

<acme:select code="message.LanguageExchange" path="languageExchange" items="${languageExchanges}" itemLabel="date" id="languageExchanges" />
	<acme:textbox code="message.title" path="title"/>
	<acme:textarea code="message.text" path="text"/>
	<acme:textbox code="message.links" path="links"/>
	<spring:message code="languageExchange.comma"></spring:message>
	<acme:textbox code="message.tags" path="tag"/>
	<spring:message code="languageExchange.comma"></spring:message>
	<acme:select code="message.Language" path="Language" items="${languages}" itemLabel="code" id="languages" /> 
	<acme:submit name="save" code="message.save"/>

	<acme:cancel url="folder/list.do" code="message.cancel"/>

</form:form>