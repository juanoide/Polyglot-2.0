<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%> 
<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@ taglib prefix="acme" tagdir="/WEB-INF/tags" %>

<form:form requestURI="${requestURI}" modelAttribute="languageExchangeEditForm">
	<fieldset>
		<legend align="left">
			<spring:message code="languageExchange.register" />
		</legend>
		<acme:textbox code="languageExchange.date" placeholder="dd/mm/yyyy hh:mm" path="date"/>
		<acme:textbox code="languageExchange.place" path="place"/>
		<spring:message code="languageExchange.ExpectedTalk"></spring:message>
		<acme:multiCheckbox path="languages" items="${languages}" itemLabel="code"/>
	
			
	
	</fieldset>
	
	<input type="submit" name="save" value="<spring:message code="save"/>" />
	&nbsp;
	<input type="button" name="cancel" value="<spring:message code="return"/>" 
	onclick="javascript: window.location.replace('languageExchange/polyglot/list.do')"/>
</form:form>