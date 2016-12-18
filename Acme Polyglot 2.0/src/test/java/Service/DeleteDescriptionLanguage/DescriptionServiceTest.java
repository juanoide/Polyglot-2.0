package Service.DeleteDescriptionLanguage;


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


import services.LanguageService;
import utilities.PopulateDatabase;

import domain.Description;
import domain.Language;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:spring/datasource.xml",
		"classpath:spring/config/packages.xml" })
public class DescriptionServiceTest {

	@Autowired
	private LanguageService languageService;
	
	@Autowired
	private DescriptionService descriptionService;

	@Autowired
	private LoginService loginService;
	


	@Before
	public void setUp() {
		PopulateDatabase.main(null);
	}
	// A user who is authenticated as an administrator
	// must be able to manage the Language.
	// Managing implies creating, modifying, or deleting them.

	// POSITIVE TEST CASE: An administrator can delete description
	// a language
	@Transactional
	@Test
	public void testDeleteDescriptionLanguage() {
		authentificate("admin");
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

		
		Language langExch=null;
		Collection <Language> languages1 = languageService.findAll();
		for(Language langExchAux: languages1){
			langExch=langExchAux;
			break;
		}
		langExch.getDescriptions().add(description);
		languageService.saveModified(langExch);
		descriptionService.deleteWithLanguage(description, langExch.getId());
	

	}

	// Negative TEST CASE: An anonymous cannot delete description
	// a language.
	@Transactional
	@Test(expected = IllegalArgumentException.class)
	public void testDeleteDescriptionLanguageNotAutehntificated() {
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

		
		Language langExch=null;
		Collection <Language> languages1 = languageService.findAll();
		for(Language langExchAux: languages1){
			langExch=langExchAux;
			break;
		}
		langExch.getDescriptions().add(description);
		languageService.saveModified(langExch);
		descriptionService.deleteWithLanguage(description, langExch.getId());

	}
	// Negative TEST CASE: An administrator cannot delete description null
	// a language.
	@Transactional
	@Test(expected = IllegalArgumentException.class)
	public void testDeleteDescriptionLanguageWithDescriptionNull() {
		authentificate("admin2");

		

		Description description = null;
		

		
		Language langExch=null;
		Collection <Language> languages1 = languageService.findAll();
		for(Language langExchAux: languages1){
			langExch=langExchAux;
			break;
		}

		descriptionService.deleteWithLanguage(description, langExch.getId());

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
