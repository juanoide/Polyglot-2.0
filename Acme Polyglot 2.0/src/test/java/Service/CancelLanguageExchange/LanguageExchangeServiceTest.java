package Service.CancelLanguageExchange;


import java.util.Collection;

import javax.transaction.Transactional;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.TestingAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;


import security.LoginService;
import services.LanguageExchangeService;
import services.PolyglotService;

import utilities.PopulateDatabase;

import domain.LanguageExchange;
import domain.Polyglot;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:spring/datasource.xml",
		"classpath:spring/config/packages.xml" })
public class LanguageExchangeServiceTest {
	@Autowired
	private LanguageExchangeService languageExchangeService;

	
	@Autowired
	private PolyglotService polyglotService;

	@Autowired
	private LoginService loginService;

	@Before
	public void setUp() {
		PopulateDatabase.main(null);
	}
	// A user who is authenticated as an polyglot
	// must be able to manage the LanguageExchange.
	// Managing implies creating, modifying, or deleting them.

	// POSITIVE TEST CASE: An polyglot can cancel
	// a languageExchange
	@Transactional
	@Test
	public void testEditLanguageExchange() {
		
		authentificate("polyglot1");

		Polyglot pol=polyglotService.findByUserAccountUsername("polyglot1");
		LanguageExchange langExch=null;
		Collection <LanguageExchange> languageExchanges = languageExchangeService.findAllPolyglotLogin(pol.getId());
		for(LanguageExchange langExchAux: languageExchanges){
			langExch=langExchAux;
			break;
		}
		
		
		
		
		
		
		languageExchangeService.delete(langExch);

	}
	// Negative TEST CASE: An anonymous cannot cancel
	// a languageExchange
	@Transactional
	@Test(expected = IllegalArgumentException.class)
	public void testCreateLanguageExchangeNotAutehntificated() {
		desauthentificate();
	
		Polyglot pol=polyglotService.findByUserAccountUsername("polyglot1");
		LanguageExchange langExch=null;
		Collection <LanguageExchange> languageExchanges = languageExchangeService.findAllPolyglotLogin(pol.getId());
		for(LanguageExchange langExchAux: languageExchanges){
			langExch=langExchAux;
			break;
		}
		
		
		
		
		languageExchangeService.delete(langExch);
	}

	// Negative TEST CASE: An polyglot cannot cancel
	// a languageExchange  null
	@Transactional
	@Test(expected = IllegalArgumentException.class)
	public void testCreateLanguageExchangeNull() {
		authentificate("polyglot2");
		languageExchangeService.delete(null);
	}



	public void authentificate(String username) {
		UserDetails userDetails;
		TestingAuthenticationToken authenticationToken;
		SecurityContext context;
		userDetails = loginService.loadUserByUsername(username);
		authenticationToken = new TestingAuthenticationToken(userDetails, null);
		context = SecurityContextHolder.getContext();
		context.setAuthentication(authenticationToken);

	}

	public void desauthentificate() {
		UserDetails userDetails;
		TestingAuthenticationToken authenticationToken;
		SecurityContext context;
		userDetails = loginService.loadUserByUsername(null);
		authenticationToken = new TestingAuthenticationToken(userDetails, null);
		context = SecurityContextHolder.getContext();
		context.setAuthentication(authenticationToken);

	}

}
