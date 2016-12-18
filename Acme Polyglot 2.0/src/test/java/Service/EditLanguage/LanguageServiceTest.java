package Service.EditLanguage;


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
import services.LanguageService;


import services.LanguageService;
import utilities.PopulateDatabase;

import domain.Language;


import forms.LanguageEditForm;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:spring/datasource.xml",
		"classpath:spring/config/packages.xml" })
public class LanguageServiceTest {
	@Autowired
	private LanguageService languageService;

	

	@Autowired
	private LoginService loginService;

	@Before
	public void setUp() {
		PopulateDatabase.main(null);
	}
	// A user who is authenticated as an administrator
		// must be able to manage the Language.
		// Managing implies creating, modifying, or deleting them.

		// POSITIVE TEST CASE: An administrator can edit 
		// a language
	@Transactional
	@Test
	public void testEditLanguage() {
		
		authentificate("admin");

	
		Language lang=null;
		Collection <Language> languages = languageService.findAll();
		for(Language langAux: languages){
			lang=langAux;
			break;
		}
		
		
		
		
		LanguageEditForm languageEditForm = new LanguageEditForm();
		languageEditForm.setCode("ky");
		
		
		Language language= languageService.reconstructEdit(languageEditForm, lang.getId());
		
		languageService.saveModified(language);

	}
	// Negative TEST CASE: An administrator cannot edit
		// a language with code null.
	@Test(expected = IllegalArgumentException.class)
	public void testCreateLanguageExchangeWithoutCode() {
		authentificate("admin");

		Language lang=null;
		Collection <Language> languages = languageService.findAll();
		for(Language langAux: languages){
			lang=langAux;
			break;
		}
		
		
		
		
		LanguageEditForm languageEditForm = new LanguageEditForm();
	
		
		
		Language language= languageService.reconstructEdit(languageEditForm, lang.getId());
		
		languageService.saveModified(language);
	}
	// Negative TEST CASE: An anonymous cannot edit
	// a language 
	@Transactional
	@Test(expected = IllegalArgumentException.class)
	public void testCreateLanguageExchangeNotAutehntificated() {
		desauthentificate();
	
		Language lang=null;
		Collection <Language> languages = languageService.findAll();
		for(Language langAux: languages){
			lang=langAux;
			break;
		}
		
		
		
		
		LanguageEditForm languageEditForm = new LanguageEditForm();
		languageEditForm.setCode("ky");
		
		
		Language language= languageService.reconstructEdit(languageEditForm, lang.getId());
		
		languageService.saveModified(language);
	}

	
	// Negative TEST CASE: An administrator cannot edit
	// a language null
	@Transactional
	@Test(expected = IllegalArgumentException.class)
	public void testCreateLanguageExchangeNull() {
		authentificate("admin2");
		languageService.saveModified(null);
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
