package Service.ListLanguageExchangeJoined;

import java.util.Calendar;
import java.util.Collection;
import java.util.Date;

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
import services.LanguageExchangeService;
import services.PolyglotService;
import utilities.PopulateDatabase;
import domain.LanguageExchange;
import domain.JoinExchange;
import domain.Polyglot;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:spring/datasource.xml",
		"classpath:spring/config/packages.xml" })
public class LanguageExchangeServiceTest {
	@Autowired
	LanguageExchangeService languageExchangeService;
	
	@Autowired
	PolyglotService polyglotService;
	
	@Autowired
	private LoginService loginService;

	@Before
	public void setUp() {
		PopulateDatabase.main(null);
	}
	// A user who is authenticated as an polyglot
	// must be able list all language Exchange joined

// POSITIVE TEST CASE: An polyglot can list all language Exchange joined
	@Test
	public void testFindAllLanguageExchangeJoined() {
		authentificate("polyglot1");
		Polyglot pol=polyglotService.findByUserAccountUsername("polyglot1");
		Collection<LanguageExchange> languageExchanges = languageExchangeService.languageExchangeOfPolyglotJoined(pol.getId());
		Assert.notNull(languageExchanges);
		boolean res=true;
	
	
	
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
