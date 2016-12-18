package Service.Banned;




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

import services.ActorService;



import utilities.PopulateDatabase;




@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:spring/datasource.xml",
		"classpath:spring/config/packages.xml" })
public class ActorServiceTest {

	private ActorService actorService;

	@Autowired
	private LoginService loginService;

	@Before
	public void setUp() {
		PopulateDatabase.main(null);
	}

	// A user who is authenticated as an administrator
		// must be banned and disbanned a actor
	

		
	// NEGATIVE TEST CASE: An anonymous cannot banned and disbanned a actor

	@Transactional
	@Test(expected = IllegalArgumentException.class)
	public void testBannedNotAutehntificated() {
		desauthentificate();
	
		int actorId=0;
		authentificate("polyglot1");
		actorId =actorService.findByPrincipal().getId();	
		desauthentificate();
		
		
		
	
		
		
		
		actorService.disable(actorId);
		actorService.enable(actorId);
	
	}

	// NEGATIVE TEST CASE: An administrator cannot banned and disbanned a actor null
	@Transactional
	@Test(expected = NullPointerException.class)
	public void testBannedNull() {
		authentificate("admin");
		
		actorService.disable(0);
		actorService.enable(0);
		desauthentificate();
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
