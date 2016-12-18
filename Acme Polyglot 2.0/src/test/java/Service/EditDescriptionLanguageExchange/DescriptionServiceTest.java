package Service.EditDescriptionLanguageExchange;


import java.util.ArrayList;
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

import services.LanguageService;
import utilities.PopulateDatabase;

import domain.Description;
import domain.Language;
import domain.LanguageExchange;
import domain.Polyglot;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:spring/datasource.xml",
		"classpath:spring/config/packages.xml" })
public class DescriptionServiceTest {

	@Autowired
	private LanguageService languageService;
	
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

	// POSITIVE TEST CASE: An polyglot can edit description
	// a languageExchange
	@Transactional
	@Test
	public void testEditDescriptionLanguage() {
		authentificate("polyglot1");
		Collection<String> links= new ArrayList<String>();
		links.add("https://projetsii3q7vprzje3n44wfxr/issues");
		links.add("https://projetsiasasafa/issues");
		Collection<String> tag= new ArrayList<String>();
		tag.add("cosas");
		tag.add("intercambios");
		Collection<Language> languages = languageService.findAll();
		Language descriptionLanguage = null;
		for(Language l: languages){
			descriptionLanguage= l;
			break;
		}
		
		
		LanguageExchange lang=null;
		Polyglot pol=polyglotService.findByUserAccountUsername("polyglot1");
		Collection <LanguageExchange> languages2 = languageExchangeService.findAllPolyglotLogin(pol.getId());
		for(LanguageExchange langAux: languages2){
			lang=langAux;
			break;
		}
		Description description=null;
		for(Description des: lang.getDescriptions()){
			description = des;
			lang.getDescriptions().remove(des);
			break;
		}
		
		
		
		


		description.setLanguage(descriptionLanguage);
		description.setLinks(links);
		description.setTag(tag);
		description.setText("informacion interesante");
		description.setTitle("titulo interesante");

		lang.getDescriptions().add(description);
		languageExchangeService.saveModified(lang);
		

	}

	// Negative TEST CASE: An anonymous cannot edit description
	// a languageExchange.
	@Transactional
	@Test(expected = IllegalArgumentException.class)
	public void testEditDescriptionLanguageNotAutehntificated() {
		desauthentificate();
	
		Collection<String> links= new ArrayList<String>();
		links.add("https://projetsii3q7vprzje3n44wfxr/issues");
		links.add("https://projetsiasasafa/issues");
		Collection<String> tag= new ArrayList<String>();
		tag.add("cosas");
		tag.add("intercambios");
		Collection<Language> languages = languageService.findAll();
		Language descriptionLanguage = null;
		for(Language l: languages){
			descriptionLanguage= l;
			break;
		}
		
		
		LanguageExchange lang=null;		
		Polyglot pol=polyglotService.findByUserAccountUsername("polyglot1");
		Collection <LanguageExchange> languages2 = languageExchangeService.findAllPolyglotLogin(pol.getId());
		for(LanguageExchange langAux: languages2){
			lang=langAux;
			break;
		}
		Description description=null;
		for(Description des: lang.getDescriptions()){
			description = des;
			lang.getDescriptions().remove(description);
			break;
		}
		
		
		
		


		description.setLanguage(descriptionLanguage);
		description.setLinks(links);
		description.setTag(tag);
		description.setText("informacion interesante");
		description.setTitle("titulo interesante");

		lang.getDescriptions().add(description);
		languageExchangeService.saveModified(lang);

	}
	// Negative TEST CASE: An polyglot cannot edit description null
	// a languageExchange.
	@Transactional
	@Test(expected = NullPointerException.class)
	public void testEditDescriptionLanguageWithDescriptionNull() {
		authentificate("polyglot2");

		
		Collection<String> links= new ArrayList<String>();
		links.add("https://projetsii3q7vprzje3n44wfxr/issues");
		links.add("https://projetsiasasafa/issues");
		Collection<String> tag= new ArrayList<String>();
		tag.add("cosas");
		tag.add("intercambios");
		Collection<Language> languages = languageService.findAll();
		Language descriptionLanguage = null;
		for(Language l: languages){
			descriptionLanguage= l;
			break;
		}
		
		

		LanguageExchange lang=null;
		Polyglot pol=polyglotService.findByUserAccountUsername("polyglot1");
		Collection <LanguageExchange> languages2 = languageExchangeService.findAllPolyglotLogin(pol.getId());
		for(LanguageExchange langAux: languages2){
			lang=langAux;
			break;
		}
		Description description=null;
	
		
		
		
		


		description.setLanguage(descriptionLanguage);
		description.setLinks(links);
		description.setTag(tag);
		description.setText("informacion interesante");
		description.setTitle("titulo interesante");

		lang.getDescriptions().add(description);
		languageExchangeService.saveModified(lang);

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