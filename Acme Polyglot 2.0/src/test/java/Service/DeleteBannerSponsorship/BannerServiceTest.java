package Service.DeleteBannerSponsorship;


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
import services.BannerService;
import services.SponsorshipService;
import services.AgentService;

import services.LanguageService;
import utilities.PopulateDatabase;

import domain.Agent;
import domain.Banner;
import domain.Language;
import domain.Sponsorship;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:spring/datasource.xml",
		"classpath:spring/config/packages.xml" })
public class BannerServiceTest {
	@Autowired
	private SponsorshipService sponsorshipService;
	@Autowired
	private LanguageService languageService;
	
	@Autowired
	private BannerService bannerService;

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

// POSITIVE TEST CASE: An agent can delete banner
// a sponsorships owner.
	@Transactional
	@Test
	public void testRemoveBannerSponsorship() {
		authentificate("agent1");
	
		Collection<Language> languages = languageService.findAll();
		Language bannerLanguage = null;
		for(Language l: languages){
			bannerLanguage= l;
			break;
		}
		

		Banner banner = bannerService.create();
		banner.setLanguage(bannerLanguage);
		banner.setImg("https://projetsiasasafa/issues");
		

		Agent agent=agentService.findByUserAccountUsername("agent1");
		Sponsorship sponsorship=null;
		Collection <Sponsorship> sponsorships = sponsorshipService.findAllAgentLogin(agent.getId());
		for(Sponsorship sponsorshipsAux: sponsorships){
			sponsorship=sponsorshipsAux;
			break;
		}
		
		sponsorship.getBanners().add(banner);
		sponsorshipService.saveModified(sponsorship);
		bannerService.deleteWithSponsorship(banner, sponsorship.getId());
		

	}
	// Negative TEST CASE: An agent cannot delete banner
			// a sponsorships  that is not him.
	@Test(expected = NullPointerException.class)
	public void testRemoveBannerSponsorshipNoMy() {
		authentificate("agent1");

		Collection<Language> languages = languageService.findAll();
		Language bannerLanguage = null;
		for(Language l: languages){
			bannerLanguage= l;
			break;
		}
		

		Banner banner = bannerService.create();
		banner.setLanguage(bannerLanguage);
		banner.setImg("https://projetsiasasafa/issues");

		Agent agent=agentService.findByUserAccountUsername("agent3");
		Sponsorship sponsorship=null;
		Collection <Sponsorship> sponsorships = sponsorshipService.findAllAgentLogin(agent.getId());
		for(Sponsorship sponsorshipsAux: sponsorships){
			sponsorship=sponsorshipsAux;
			break;
		}
		sponsorship.getBanners().add(banner);
		sponsorshipService.saveModified(sponsorship);
		bannerService.deleteWithSponsorship(banner, sponsorship.getId());
	}
	// Negative TEST CASE: An anonymous cannot delete banner
			// a sponsorships 
	@Transactional
	@Test(expected = IllegalArgumentException.class)
	public void testRemoveBannerSponsorshipNotAutehntificated() {
		desauthentificate();
	

		Collection<Language> languages = languageService.findAll();
		Language bannerLanguage = null;
		for(Language l: languages){
			bannerLanguage= l;
			break;
		}
		

		Banner banner = bannerService.create();
		banner.setLanguage(bannerLanguage);
		banner.setImg("https://projetsiasasafa/issues");

		Agent agent=agentService.findByUserAccountUsername("agent1");
		Sponsorship sponsorship=null;
		Collection <Sponsorship> sponsorships = sponsorshipService.findAllAgentLogin(agent.getId());
		for(Sponsorship sponsorshipsAux: sponsorships){
			sponsorship=sponsorshipsAux;
			break;
		}
		sponsorship.getBanners().add(banner);
		sponsorshipService.saveModified(sponsorship);
		bannerService.deleteWithSponsorship(banner, sponsorship.getId());
	}
	// Negative TEST CASE: An agent cannot delete banner null
		// a sponsorships
	@Transactional
	@Test(expected = IllegalArgumentException.class)
	public void testRemoveBannerSponsorshipWithBannerNull() {
		authentificate("agent2");

		Banner banner = null;

		Agent agent=agentService.findByUserAccountUsername("agent1");
		Sponsorship sponsorship=null;
		Collection <Sponsorship> sponsorships = sponsorshipService.findAllAgentLogin(agent.getId());
		for(Sponsorship sponsorshipsAux: sponsorships){
			sponsorship=sponsorshipsAux;
			break;
		}
	
		bannerService.deleteWithSponsorship(banner, sponsorship.getId());
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
