package Service.EditLanguageExchange;


import java.util.Collection;
import java.util.Date;

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
import security.UserAccount;
import services.LanguageExchangeService;
import services.PolyglotService;

import services.LanguageService;
import utilities.PopulateDatabase;

import domain.Language;
import domain.LanguageExchange;
import domain.Polyglot;
import forms.LanguageExchangeEditForm;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:spring/datasource.xml",
		"classpath:spring/config/packages.xml" })
public class LanguageExchangeServiceTest {
	@Autowired
	private LanguageExchangeService languageExchangeService;
	@Autowired
	private LanguageService languageService;
	
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

	// POSITIVE TEST CASE: An polyglot can edit
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
		
		
		
		
		LanguageExchangeEditForm languageExchangeEditForm = new LanguageExchangeEditForm();
		languageExchangeEditForm.setDate(new Date(2017, 9, 9));
		languageExchangeEditForm.setPlace("https://www.google.es/maps/@37.3503807,-6.0626248,15z?hl=es");
		Collection<Language> languages = languageService.findAll();		
		languageExchangeEditForm.setLanguages(languages);

		
		LanguageExchange languageExchange= languageExchangeService.reconstructEdit(languageExchangeEditForm, langExch.getId());
		
		languageExchangeService.saveModified(languageExchange);

	}
	// Negative TEST CASE: An polyglot cannot edit
	// a languageExchange without date 
	@Test(expected = IllegalArgumentException.class)
	public void testCreateLanguageExchangeWithoutDate() {
		authentificate("polyglot1");

		Polyglot pol=polyglotService.findByUserAccountUsername("polyglot1");
		LanguageExchange langExch=null;
		Collection <LanguageExchange> languageExchanges = languageExchangeService.findAllPolyglotLogin(pol.getId());
		for(LanguageExchange langExchAux: languageExchanges){
			langExch=langExchAux;
			break;
		}
		
		
		
		
		LanguageExchangeEditForm languageExchangeEditForm = new LanguageExchangeEditForm();
		languageExchangeEditForm.setPlace("https://www.google.es/maps/@37.3503807,-6.0626248,15z?hl=es");
		Collection<Language> languages = languageService.findAll();		
		languageExchangeEditForm.setLanguages(languages);

		
		LanguageExchange languageExchange= languageExchangeService.reconstructEdit(languageExchangeEditForm, langExch.getId());
		
		languageExchangeService.saveModified(languageExchange);
	}
	// Negative TEST CASE: An anonymous cannot edit
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
		
		
		
		
		LanguageExchangeEditForm languageExchangeEditForm = new LanguageExchangeEditForm();
		languageExchangeEditForm.setDate(new Date(2017, 9, 9));
		languageExchangeEditForm.setPlace("https://www.google.es/maps/@37.3503807,-6.0626248,15z?hl=es");
		Collection<Language> languages = languageService.findAll();		
		languageExchangeEditForm.setLanguages(languages);

		
		LanguageExchange languageExchange= languageExchangeService.reconstructEdit(languageExchangeEditForm, langExch.getId());
		
		languageExchangeService.saveModified(languageExchange);
	}
	// Negative TEST CASE: An polyglot cannot edit
	// a languageExchange with data wrong
	@Transactional
	@Test(expected = IllegalArgumentException.class)
	public void testCreateLanguageExchangeWithLanguageExchangeNull() {
		authentificate("polyglot2");
		Polyglot pol=polyglotService.findByUserAccountUsername("polyglot1");
		LanguageExchange langExch=null;
		Collection <LanguageExchange> languageExchanges = languageExchangeService.findAllPolyglotLogin(pol.getId());
		for(LanguageExchange langExchAux: languageExchanges){
			langExch=langExchAux;
			break;
		}
		
		
		
		
		LanguageExchangeEditForm languageExchangeEditForm = new LanguageExchangeEditForm();
		languageExchangeEditForm.setDate(new Date(2017, 9, 9));
		languageExchangeEditForm.setPlace("https://www.google.es/maps/@37.3503807,-6.0626248,15z?hl=es");
		Collection<Language> languages = languageService.findAll();		
		languageExchangeEditForm.setLanguages(languages);

		
		LanguageExchange languageExchange= languageExchangeService.reconstructEdit(languageExchangeEditForm, 1000);
		
		languageExchangeService.saveModified(languageExchange);
	}
	// Negative TEST CASE: An polyglot cannot create
	// a languageExchange null
	@Transactional
	@Test(expected = IllegalArgumentException.class)
	public void testCreateLanguageExchangeNull() {
		authentificate("polyglot2");
		languageExchangeService.saveModified(null);
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
