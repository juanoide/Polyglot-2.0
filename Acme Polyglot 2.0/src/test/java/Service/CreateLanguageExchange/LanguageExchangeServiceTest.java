package Service.CreateLanguageExchange;


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
import services.LanguageExchangeService;

import services.LanguageService;
import utilities.PopulateDatabase;

import domain.Language;
import domain.LanguageExchange;
import forms.LanguageExchangeForm;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:spring/datasource.xml",
		"classpath:spring/config/packages.xml" })
public class LanguageExchangeServiceTest {
	@Autowired
	private LanguageExchangeService languageExchangeService;
	@Autowired
	private LanguageService languageService;

	@Autowired
	private LoginService loginService;

	@Before
	public void setUp() {
		PopulateDatabase.main(null);
	}
	// A user who is authenticated as an polyglot
	// must be able to manage the LanguageExchange.
	// Managing implies creating, modifying, or deleting them.

	// POSITIVE TEST CASE: An polyglot can create
	// a languageExchange

	@Transactional
	@Test
	public void testCreateLanguageExchange() {
		
		authentificate("polyglot1");
		LanguageExchangeForm languageExchangeForm = new LanguageExchangeForm();
		languageExchangeForm.setDate(new Date(2017, 9, 9));
		languageExchangeForm.setPlace("https://www.google.es/maps/@37.3503807,-6.0626248,15z?hl=es");
		Collection<Language> languages = languageService.findAll();		
		languageExchangeForm.setLanguages(languages);
		String links="https://projetsii.informatica.us.es/projects/623q7vprzje3n44wfxr/issues, https://projetsii.informatica.us.es/projec, https://projetsii.informatica.us.es/issues/71708";
		String tag="cosas, intercambios, sanas";
		Language descriptionLanguage = null;
		languageExchangeForm.setLinks(links);
		languageExchangeForm.setTag(tag);
		for(Language l: languages){
			descriptionLanguage= l;
			break;
		}
		String text="Esto es asi porque si y tiene mucha informacion y tal";
		String title="esto es un titulo con informacion importante";
		languageExchangeForm.setDescriptionLanguage(descriptionLanguage);
		languageExchangeForm.setText(text);
		languageExchangeForm.setTitle(title);
		
		LanguageExchange languageExchange= languageExchangeService.reconstruct(languageExchangeForm);
		
		languageExchangeService.save(languageExchange);

	}
	// Negative TEST CASE: An polyglot cannot create
	// a languageExchange with description null
	@Test(expected = IllegalArgumentException.class)
	public void testCreateLanguageExchangeWithoutLanguageDescription() {
		authentificate("polyglot1");
		LanguageExchangeForm languageExchangeForm = new LanguageExchangeForm();
		languageExchangeForm.setDate(new Date(2017, 9, 9));
		languageExchangeForm.setPlace("https://projetsii.informatica.us.es/projects/623q7vprzje3n44wfxr/issues");
		Collection<Language> languages = languageService.findAll();		
		languageExchangeForm.setLanguages(languages);
		String links="https://projetsii.informatica.us.es/projects/623q7vprzje3n44wfxr/issues, https://projetsii.informatica.us.es/projec, https://projetsii.informatica.us.es/issues/71708";
		String tag="cosas, intercambios, sanas";
		Language descriptionLanguage = null;
		languageExchangeForm.setLinks(links);
		languageExchangeForm.setTag(tag);
		
		String text="Esto es asi porque si y tiene mucha informacion y tal";
		String title="esto es un titulo con informacion importante";
		languageExchangeForm.setDescriptionLanguage(descriptionLanguage);
		languageExchangeForm.setText(text);
		languageExchangeForm.setTitle(title);
		
		LanguageExchange languageExchange= languageExchangeService.reconstruct(languageExchangeForm);
		
		languageExchangeService.save(languageExchange);
	}
	// Negative TEST CASE: An anonymous cannot create
	// a languageExchange
	@Transactional
	@Test(expected = IllegalArgumentException.class)
	public void testCreateLanguageExchangeNotAutehntificated() {
		desauthentificate();
	
		LanguageExchangeForm languageExchangeForm = new LanguageExchangeForm();
		languageExchangeForm.setDate(new Date(2017, 9, 9));
		languageExchangeForm.setPlace("https://www.google.es/maps/@37.3503807,-6.0626248,15z?hl=es");
		Collection<Language> languages = languageService.findAll();		
		languageExchangeForm.setLanguages(languages);
		String links="https://projetsii.informatica.us.es/projects/623q7vprzje3n44wfxr/issues, https://projetsii.informatica.us.es/projec, https://projetsii.informatica.us.es/issues/71708";
		String tag="cosas, intercambios, sanas";
		Language descriptionLanguage = null;
		languageExchangeForm.setLinks(links);
		languageExchangeForm.setTag(tag);
		for(Language l: languages){
			descriptionLanguage= l;
			break;
		}
		String text="Esto es asi porque si y tiene mucha informacion y tal";
		String title="esto es un titulo con informacion importante";
		languageExchangeForm.setDescriptionLanguage(descriptionLanguage);
		languageExchangeForm.setText(text);
		languageExchangeForm.setTitle(title);
		
		LanguageExchange languageExchange= languageExchangeService.reconstruct(languageExchangeForm);
		
		languageExchangeService.save(languageExchange);
	}
	// Negative TEST CASE: An polyglot cannot create
	// a languageExchange with languages expected talk null
	@Transactional
	@Test(expected = IllegalArgumentException.class)
	public void testCreateLanguageExchangeWithLanguagesNull() {
		authentificate("polyglot2");
		LanguageExchangeForm languageExchangeForm = new LanguageExchangeForm();
		languageExchangeForm.setDate(new Date(2017, 9, 9));
		languageExchangeForm.setPlace("https://www.google.es/maps/@37.3503807,-6.0626248,15z?hl=es");
		Collection<Language> languages = languageService.findAll();		
		languageExchangeForm.setLanguages(null);
		String links="https://projetsii.informatica.us.es/projects/623q7vprzje3n44wfxr/issues, https://projetsii.informatica.us.es/projec, https://projetsii.informatica.us.es/issues/71708";
		String tag="cosas, intercambios, sanas";
		Language descriptionLanguage = null;
		languageExchangeForm.setLinks(links);
		languageExchangeForm.setTag(tag);
		for(Language l: languages){
			descriptionLanguage= l;
			break;
		}
		String text="Esto es asi porque si y tiene mucha informacion y tal";
		String title="esto es un titulo con informacion importante";
		languageExchangeForm.setDescriptionLanguage(descriptionLanguage);
		languageExchangeForm.setText(text);
		languageExchangeForm.setTitle(title);
		
		LanguageExchange languageExchange= languageExchangeService.reconstruct(languageExchangeForm);
		
		languageExchangeService.save(languageExchange);
	}
	// Negative TEST CASE: An polyglot cannot create
	// a languageExchange null
	@Transactional
	@Test(expected = IllegalArgumentException.class)
	public void testCreateLanguageExchangeNull() {
		authentificate("polyglot2");
		languageExchangeService.save(null);
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
