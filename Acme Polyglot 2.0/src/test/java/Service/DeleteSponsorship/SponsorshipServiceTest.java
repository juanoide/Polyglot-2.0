package Service.DeleteSponsorship;


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


import utilities.PopulateDatabase;

import domain.Agent;

import domain.Sponsorship;



@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:spring/datasource.xml",
		"classpath:spring/config/packages.xml" })
public class SponsorshipServiceTest {
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

	// POSITIVE TEST CASE: An agent can delete 
	// a sponsorships owner.
	@Transactional
	@Test
	public void testDeleteSponsorship() {
		
		authentificate("agent1");
		Agent agent=agentService.findByUserAccountUsername("agent1");
		Sponsorship sponsorship=null;
		Collection <Sponsorship> sponsorships = sponsorshipService.findAllAgentLogin(agent.getId());
		for(Sponsorship sponsorshipAux: sponsorships){
			sponsorship=sponsorshipAux;
			break;
		}
		
		
		
		
		
		
		sponsorshipService.delete(sponsorship);

	}
	// Negative TEST CASE: An anonymous cannot delete
	// a sponsorships 
	@Transactional
	@Test(expected = IllegalArgumentException.class)
	public void testDeleteSponsorshipNotAutehntificated() {
		desauthentificate();
	
		Agent agent=agentService.findByUserAccountUsername("agent1");
		Sponsorship sponsorship=null;
		Collection <Sponsorship> sponsorships = sponsorshipService.findAllAgentLogin(agent.getId());
		for(Sponsorship sponsorshipAux: sponsorships){
			sponsorship=sponsorshipAux;
			break;
		}
		
		
		
		
		
		
		sponsorshipService.delete(sponsorship);

	}
	
	// Negative TEST CASE: An agent cannot delete
	// a sponsorships null

	@Transactional
	@Test(expected = IllegalArgumentException.class)
	public void testDeleteSponsorshipNull() {
		authentificate("admin2");
		sponsorshipService.delete(null);
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

