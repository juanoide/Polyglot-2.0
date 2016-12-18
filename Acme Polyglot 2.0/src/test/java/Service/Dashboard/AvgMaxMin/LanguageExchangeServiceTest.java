package Service.Dashboard.AvgMaxMin;

import java.util.List;

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

import domain.LanguageExchange;

import security.LoginService;
import services.LanguageExchangeService;
import utilities.PopulateDatabase;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:spring/datasource.xml",
		"classpath:spring/config/packages.xml" })
public class LanguageExchangeServiceTest {

	@Autowired
	private LoginService loginService;
	@Autowired
	private LanguageExchangeService languageExchangeService;
	
	@Before
	public void setUp() {
		PopulateDatabase.main(null);
	}

	@Test
	public void testLanguageExchangeSponsorizadosPerPolyglot() {
		authentificate("admin");
		Double result= languageExchangeService.languageExchangeAvgSponsorizedPerPolyglot();
		Integer result2= languageExchangeService.languageExchangeMaxSponsorizedPerPolyglot();
		Integer result3= languageExchangeService.languageExchangeMinSponsorizedPerPolyglot();
		Assert.isTrue(result== 0.75);
		Assert.isTrue(result2== 2);
		Assert.isTrue(result3== 1);
		
	
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
