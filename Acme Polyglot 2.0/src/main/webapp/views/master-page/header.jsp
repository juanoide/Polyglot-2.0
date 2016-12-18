<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>

<div>
	<img src="images/logo.png" alt="Sample Co., Inc." />
</div>

<div>
	<ul id="jMenu">
		<!-- Do not forget the "fNiv" class for the first level links !! -->
				<li>
				<a class="fNiv"> 
					<spring:message code="master.page.Exchange" /> 
				</a>
				<ul>
					<li class="arrow"></li>
					<security:authorize access="!hasRole('POLYGLOT')">
						<li><a href="languageExchange/past3months/list.do"><spring:message code="master.page.past3months" /> </a></li>
						<li><a href="languageExchange/future3months/list.do"><spring:message code="master.page.future3months" /> </a></li>
						</security:authorize>
				
	<security:authorize access="hasRole('POLYGLOT')">
	<li><a href="languageExchange/polyglot/register.do"><spring:message code="master.page.register.languageExchange" /></a></li>
			<li><a href="languageExchange/polyglot/list.do"><spring:message code="master.page.ExchangeAll" /> </a></li>
			<li><a href="languageExchange/polyglot/past3months/list.do"><spring:message code="master.page.past3months" /> </a></li>
			<li><a href="languageExchange/polyglot/future3months/list.do"><spring:message code="master.page.future3months" /> </a></li>
			<li><a href="languageExchange/polyglot/joined/list.do"><spring:message code="master.page.joined.languageExchange" /> </a></li>
			
	</security:authorize>
				</ul>
			</li>
			<security:authorize access="hasRole('AGENT')">
					<li>
				<a class="fNiv"> 
					<spring:message code="master.page.Sponsorship" /> 
				</a>
				<ul>
					<li class="arrow"></li>
					
				
	
	<li><a href="sponsorship/agent/register.do"><spring:message code="master.page.register.Sponsorship" /></a></li>
			<li><a href="sponsorship/agent/list.do"><spring:message code="master.page.SponsorshipMy" /> </a></li>
		
	
				</ul>
			</li>
			</security:authorize>
			<security:authorize access="hasRole('ADMIN')">
			<li>
				<a class="fNiv"> 
					<spring:message code="master.page.Language" /> 
				</a>
				<ul>
					<li class="arrow"></li>	
			
					<li><a href="language/admin/register.do"><spring:message code="master.page.register.language" /></a></li>
					<li><a href="language/admin/list.do"><spring:message code="master.page.All" /></a></li>
					
				</ul>
			</li>
						
			<li><a class="fNiv"><spring:message	code="master.page.dashboard" /></a>
				<ul>
					<li class="arrow"></li>
					<li><a href="administrator/dashboard.do"><spring:message code="master.page.dashboard" /></a></li>
					<li><a href="administrator/dashboard2.do"><spring:message code="master.page.dashboard2" /></a></li>
				</ul>
			</li>
			
		</security:authorize>

		
		
		<security:authorize access="isAnonymous()">
			<li><a class="fNiv" href="polyglot/register.do"><spring:message code="master.page.polyglot.register" /></a></li>
			<li><a class="fNiv" href="security/login.do"><spring:message code="master.page.login" /></a></li>
		</security:authorize>
		
		
	<security:authorize access="isAuthenticated()">
			<li>
				<a class="fNiv"> 
					<spring:message code="master.page.profile" /> 
			        (<security:authentication property="principal.username" />)
				</a>
				<ul>
					<li class="arrow"></li>		
							<li><a href="folder/list.do">					
						<spring:message code="master.page.user.mail" /></a>
					</li>	</security:authorize>
					<security:authorize access="hasRole('ADMIN')">
							
							<li><a href="administrator/ban/list.do">					
						<spring:message code="master.page.bannerUser" /></a>
					</li>			
						</security:authorize>		<security:authorize access="isAuthenticated()">
					<li><a href="j_spring_security_logout"><spring:message code="master.page.logout" /> </a></li>
				</ul>
			</li>
		</security:authorize>
	</ul>
</div>

<div>
	<a href="?language=en">en</a> | <a href="?language=es">es</a>
</div>

