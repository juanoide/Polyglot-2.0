package Service.EditBannerSponsorship;


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

import services.SponsorshipService;
import services.AgentService;

import services.LanguageService;
import utilities.PopulateDatabase;

import domain.Banner;
import domain.Language;
import domain.Sponsorship;
import domain.Agent;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:spring/datasource.xml",
		"classpath:spring/config/packages.xml" })
public class BannerServiceTest {

	@Autowired
	private LanguageService languageService;
	
	@Autowired
	private SponsorshipService sponsorshipService;
		

	
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

// POSITIVE TEST CASE: An agent can edit banner
// a sponsorships owner.
	@Transactional
	@Test
	public void testEditBannerLanguage() {
		authentificate("agent1");
	
		Collection<Language> languages = languageService.findAll();
		Language bannerLanguage = null;
		for(Language l: languages){
			bannerLanguage= l;
			break;
		}
		
		
		Sponsorship lang=null;
		Agent pol=agentService.findByUserAccountUsername("agent1");
		Collection <Sponsorship> languages2 = sponsorshipService.findAllAgentLogin(pol.getId());
		for(Sponsorship langAux: languages2){
			lang=langAux;
			break;
		}
		Banner banner=null;
		for(Banner des: lang.getBanners()){
			banner = des;
			lang.getBanners().remove(des);
			break;
		}
		
		
		
		


		banner.setLanguage(bannerLanguage);
		banner.setImg("https://projetsiasasafa/issues");
	

		lang.getBanners().add(banner);
		sponsorshipService.saveModified(lang);
		

	}
	// Negative TEST CASE: An anonymous cannot edit banner
	// a sponsorships 

	@Transactional
	@Test(expected = IllegalArgumentException.class)
	public void testEditBannerLanguageNotAutehntificated() {
		desauthentificate();
	
		
		Collection<Language> languages = languageService.findAll();
		Language bannerLanguage = null;
		for(Language l: languages){
			bannerLanguage= l;
			break;
		}
		
		
		Sponsorship lang=null;		
		Agent pol=agentService.findByUserAccountUsername("agent1");
		Collection <Sponsorship> languages2 = sponsorshipService.findAllAgentLogin(pol.getId());
		for(Sponsorship langAux: languages2){
			lang=langAux;
			break;
		}
		Banner banner=null;
		for(Banner des: lang.getBanners()){
			banner = des;
			lang.getBanners().remove(banner);
			break;
		}
		
		
		
		


		banner.setLanguage(bannerLanguage);
		banner.setImg("https://projetsiasasafa/issues");
	


		lang.getBanners().add(banner);
		sponsorshipService.saveModified(lang);

	}
	// Negative TEST CASE: An agent cannot edit banner null
	// a sponsorships
	@Transactional
	@Test(expected = IllegalArgumentException.class)
	public void testEditBannerLanguageWithBannerNull() {
		authentificate("agent2");

		
		

	
		
		

		Sponsorship lang=null;
		Agent pol=agentService.findByUserAccountUsername("agent1");
		Collection <Sponsorship> languages2 = sponsorshipService.findAllAgentLogin(pol.getId());
		for(Sponsorship langAux: languages2){
			lang=langAux;
			break;
		}
		Banner banner=null;
	
		
		
		
		


	


		lang.getBanners().add(banner);
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