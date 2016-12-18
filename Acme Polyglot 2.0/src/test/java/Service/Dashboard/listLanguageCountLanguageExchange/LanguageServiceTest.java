package Service.Dashboard.listLanguageCountLanguageExchange;

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

import security.LoginService;
import services.LanguageService;
import utilities.PopulateDatabase;
import domain.Language;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:spring/datasource.xml",
		"classpath:spring/config/packages.xml" })
public class LanguageServiceTest {

	@Autowired
	private LoginService loginService;
	@Autowired
	private LanguageService languageService;
	
	@Before
	public void setUp() {
		PopulateDatabase.main(null);
	}

	@Test
	public void testListLanguageCountLanguageExchange() {
		authentificate("admin");
		List<Language> languageCountLanguageExchange = (List<Language>) languageService.findAll();
		
		//son dashboard de size por lo que no interesa hacer tests

		
		Assert.isTrue(languageCountLanguageExchange.get(0).getExpectedTalks().size() == 4);
		Assert.isTrue(languageCountLanguageExchange.get(1).getExpectedTalks().size() == 4);
		Assert.isTrue(languageCountLanguageExchange.get(2).getExpectedTalks().size() == 2);
		Assert.isTrue(languageCountLanguageExchange.get(3).getExpectedTalks().size() == 1);
		Assert.isTrue(languageCountLanguageExchange.get(4).getExpectedTalks().size() == 0);
	
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
