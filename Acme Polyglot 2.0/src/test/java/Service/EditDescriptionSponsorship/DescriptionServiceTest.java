package Service.EditDescriptionSponsorship;


import java.util.ArrayList;
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
import org.springframework.util.Assert;


import security.LoginService;
import services.DescriptionService;
import services.SponsorshipService;
import services.AgentService;

import services.LanguageService;
import utilities.PopulateDatabase;

import domain.Description;
import domain.Language;
import domain.Sponsorship;
import domain.Agent;
import forms.LanguageEditForm;
import forms.SponsorshipForm;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:spring/datasource.xml",
		"classpath:spring/config/packages.xml" })
public class DescriptionServiceTest {

	@Autowired
	private LanguageService languageService;
	
	@Autowired
	private SponsorshipService sponsorshipService;
		
	@Autowired
	private DescriptionService descriptionService;
	
	@Autowired
	private AgentService agentService;

	@Autowired
	private LoginService loginService;
	


	@Before
	public void setUp() {
		PopulateDatabase.main(null);
	}
	// A user who is authenticated as an agent
			// must be able to manage the sponsorships.
			// Managing implies creating, modifying, or deleting them.

		// POSITIVE TEST CASE: An agent can edit description
		// a sponsorships owner.
	@Transactional
	@Test
	public void testEditDescriptionLanguage() {
		authentificate("agent1");
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
		
		
		Sponsorship lang=null;
		Agent pol=agentService.findByUserAccountUsername("agent1");
		Collection <Sponsorship> languages2 = sponsorshipService.findAllAgentLogin(pol.getId());
		for(Sponsorship langAux: languages2){
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
		sponsorshipService.saveModified(lang);
		

	}

	// Negative TEST CASE: An anonymous cannot edit description
	// a sponsorships.
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
		
		
		Sponsorship lang=null;		
		Agent pol=agentService.findByUserAccountUsername("agent1");
		Collection <Sponsorship> languages2 = sponsorshipService.findAllAgentLogin(pol.getId());
		for(Sponsorship langAux: languages2){
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
		sponsorshipService.saveModified(lang);

	}
	// Negative TEST CASE: An anonymous cannot edit description null
		// a sponsorships
	@Transactional
	@Test(expected = NullPointerException.class)
	public void testEditDescriptionLanguageWithDescriptionNull() {
		authentificate("agent2");

		
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
		
		

		Sponsorship lang=null;
		Agent pol=agentService.findByUserAccountUsername("agent1");
		Collection <Sponsorship> languages2 = sponsorshipService.findAllAgentLogin(pol.getId());
		for(Sponsorship langAux: languages2){
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
		sponsorshipService.saveModified(lang);

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
