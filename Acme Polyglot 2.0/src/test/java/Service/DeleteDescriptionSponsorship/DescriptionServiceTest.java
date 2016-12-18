package Service.DeleteDescriptionSponsorship;


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
import services.SponsorshipService;
import services.AgentService;

import services.LanguageService;
import utilities.PopulateDatabase;

import domain.Agent;
import domain.Description;
import domain.Language;
import domain.Sponsorship;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:spring/datasource.xml",
		"classpath:spring/config/packages.xml" })
public class DescriptionServiceTest {
	@Autowired
	private SponsorshipService sponsorshipService;
	@Autowired
	private LanguageService languageService;
	
	@Autowired
	private DescriptionService descriptionService;

	@Autowired
	private LoginService loginService;
	
	@Autowired
	private AgentService agentService;

	@Before
	public void setUp() {
		PopulateDatabase.main(null);
	}
	// A user who is authenticated as an agent
		// must be able to manage the sponsorships.
		// Managing implies creating, modifying, or deleting them.

	// POSITIVE TEST CASE: An agent can delete description
	// a sponsorships owner.
	@Transactional
	@Test
	public void testDeleteDescriptionSponsorship() {
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
		

		Description description = descriptionService.create();
		description.setLanguage(descriptionLanguage);
		description.setLinks(links);
		description.setTag(tag);
		description.setText("informacion interesante");
		description.setTitle("titulo interesante");

		Agent agent=agentService.findByUserAccountUsername("agent1");
		Sponsorship sponsorship=null;
		Collection <Sponsorship> sponsorships = sponsorshipService.findAllAgentLogin(agent.getId());
		for(Sponsorship sponsorshipsAux: sponsorships){
			sponsorship=sponsorshipsAux;
			break;
		}
		sponsorship.getDescriptions().add(description);
		sponsorshipService.saveModified(sponsorship);
		descriptionService.deleteWithSponsorship(description, sponsorship.getId());
	

	}
	// Negative TEST CASE: An agent cannot delete description
	// a sponsorships  that is not him.
	@Test(expected = NullPointerException.class)
	public void testDeleteDescriptionSponsorshipNoMy() {
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
		

		Description description = descriptionService.create();
		description.setLanguage(descriptionLanguage);
		description.setLinks(links);
		description.setTag(tag);
		description.setText("informacion interesante");
		description.setTitle("titulo interesante");

		Agent agent=agentService.findByUserAccountUsername("agent3");
		Sponsorship sponsorship=null;
		Collection <Sponsorship> sponsorships = sponsorshipService.findAllAgentLogin(agent.getId());
		for(Sponsorship sponsorshipsAux: sponsorships){
			sponsorship=sponsorshipsAux;
			break;
		}
		sponsorship.getDescriptions().add(description);
		sponsorshipService.saveModified(sponsorship);
		descriptionService.deleteWithSponsorship(description, sponsorship.getId());

	}
	// Negative TEST CASE: An anonymous cannot delete description
	// a sponsorships 
	@Transactional
	@Test(expected = IllegalArgumentException.class)
	public void testDeleteDescriptionSponsorshipNotAutehntificated() {
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

		Agent agent=agentService.findByUserAccountUsername("agent1");
		Sponsorship sponsorship=null;
		Collection <Sponsorship> sponsorships = sponsorshipService.findAllAgentLogin(agent.getId());
		for(Sponsorship sponsorshipsAux: sponsorships){
			sponsorship=sponsorshipsAux;
			break;
		}
		sponsorship.getDescriptions().add(description);
		sponsorshipService.saveModified(sponsorship);
		descriptionService.deleteWithSponsorship(description, sponsorship.getId());

	}
	// Negative TEST CASE: An anonymous cannot delete description null
	// a sponsorships
	@Transactional
	@Test(expected = IllegalArgumentException.class)
	public void testDeleteDescriptionSponsorshipWithDescriptionNull() {
		authentificate("agent2");

		Description description = null;

		Agent agent=agentService.findByUserAccountUsername("agent1");
		Sponsorship sponsorship=null;
		Collection <Sponsorship> sponsorships = sponsorshipService.findAllAgentLogin(agent.getId());
		for(Sponsorship sponsorshipsAux: sponsorships){
			sponsorship=sponsorshipsAux;
			break;
		}
	
		descriptionService.deleteWithSponsorship(description, sponsorship.getId());

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
