package Service.CreateSearch;




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
import services.SearchService;


import utilities.PopulateDatabase;




@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:spring/datasource.xml",
		"classpath:spring/config/packages.xml" })
public class SearchServiceTest {
	@Autowired
	private SearchService searchService;


	@Autowired
	private LoginService loginService;

	@Before
	public void setUp() {
		PopulateDatabase.main(null);
	}
	// A user who is authenticated 
		// must be able search y save information in the system

		// POSITIVE TEST CASE: An polyglot can "create"
		// a search

	@Transactional
	@Test
	public void testCreateSearch() {
		authentificate("polyglot1");
		String cadena ="test";
		
		searchService.createWithEntrante(cadena);
		
		

	}
	
	
	// NEGATIVE TEST CASE: An polyglot cannot "create"
	// a search null
	@Transactional
	@Test(expected = IllegalArgumentException.class)
	public void testCreateSearchNull() {
		authentificate("polyglot2");
		searchService.save(null);
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
