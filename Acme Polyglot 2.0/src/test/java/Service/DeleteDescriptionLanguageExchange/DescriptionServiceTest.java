package Service.DeleteDescriptionLanguageExchange;


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
import services.DescriptionService;
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
	private LanguageExchangeService languageExchangeService;
	@Autowired
	private LanguageService languageService;
	
	@Autowired
	private DescriptionService descriptionService;

	@Autowired
	private LoginService loginService;
	
	@Autowired
	private PolyglotService polyglotService;

	@Before
	public void setUp() {
		PopulateDatabase.main(null);
	}
	// A user who is authenticated as an polyglot
	// must be able to manage the LanguageExchange.
	// Managing implies creating, modifying, or deleting them.

	// POSITIVE TEST CASE: An polyglot can delete description
	// a languageExchange
	@Transactional
	@Test
	public void testDeleteDescriptionLanguageExchange() {
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
		

		Description description = descriptionService.create();
		description.setLanguage(descriptionLanguage);
		description.setLinks(links);
		description.setTag(tag);
		description.setText("informacion interesante");
		description.setTitle("titulo interesante");

		Polyglot pol=polyglotService.findByUserAccountUsername("polyglot1");
		LanguageExchange langExch=null;
		Collection <LanguageExchange> languageExchanges = languageExchangeService.findAllPolyglotLogin(pol.getId());
		for(LanguageExchange langExchAux: languageExchanges){
			langExch=langExchAux;
			break;
		}
		
		langExch.getDescriptions().add(description);
		languageExchangeService.saveModified(langExch);
		descriptionService.deleteWithLanguageExchange(description, langExch.getId());

	}
	// Negative TEST CASE: An polyglot cannot delete description
	// a languageExchange  that is not him.
	@Test(expected = NullPointerException.class)
	public void testDeleteDescriptionLanguageExchangeNoMy() {
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
		

		Description description = descriptionService.create();
		description.setLanguage(descriptionLanguage);
		description.setLinks(links);
		description.setTag(tag);
		description.setText("informacion interesante");
		description.setTitle("titulo interesante");

		Polyglot pol=polyglotService.findByUserAccountUsername("polyglot2");
		LanguageExchange langExch=null;
		Collection <LanguageExchange> languageExchanges = languageExchangeService.findAllPolyglotLogin(pol.getId());
		
		for(LanguageExchange langExchAux: languageExchanges){
			langExch=langExchAux;
		
			break;
		}
		langExch.getDescriptions().add(description);
		languageExchangeService.saveModified(langExch);
		descriptionService.deleteWithLanguageExchange(description, langExch.getId());
	}
	// Negative TEST CASE: An anonymous cannot delete description
	// a languageExchange.
	@Transactional
	@Test(expected = IllegalArgumentException.class)
	public void testDeleteDescriptionLanguageExchangeNotAutehntificated() {
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
		

		Description description = descriptionService.create();
		description.setLanguage(descriptionLanguage);
		description.setLinks(links);
		description.setTag(tag);
		description.setText("informacion interesante");
		description.setTitle("titulo interesante");

		Polyglot pol=polyglotService.findByUserAccountUsername("polyglot1");
		LanguageExchange langExch=null;
		Collection <LanguageExchange> languageExchanges = languageExchangeService.findAllPolyglotLogin(pol.getId());
		for(LanguageExchange langExchAux: languageExchanges){
			langExch=langExchAux;
			break;
		}
		langExch.getDescriptions().add(description);
		languageExchangeService.saveModified(langExch);
		descriptionService.deleteWithLanguageExchange(description, langExch.getId());
	}
	// Negative TEST CASE: An polyglot cannot delete description null
	// a languageExchange.
	@Transactional
	@Test(expected = NullPointerException.class)
	public void testDeleteDescriptionLanguageExchangeWithDescriptionNull() {
		authentificate("polyglot2");

		Description description = null;
	

		Polyglot pol=polyglotService.findByUserAccountUsername("polyglot2");
		LanguageExchange langExch=null;
		Collection <LanguageExchange> languageExchanges = languageExchangeService.findAllPolyglotLogin(pol.getId());
		for(LanguageExchange langExchAux: languageExchanges){
			langExch=langExchAux;
			break;
		}
	
		descriptionService.deleteWithLanguageExchange(description, langExch.getId());
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
