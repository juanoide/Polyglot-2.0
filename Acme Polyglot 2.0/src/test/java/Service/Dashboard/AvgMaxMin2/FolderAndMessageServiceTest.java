package Service.Dashboard.AvgMaxMin2;



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
import services.AdministratorService;

import utilities.PopulateDatabase;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:spring/datasource.xml",
		"classpath:spring/config/packages.xml" })
public class FolderAndMessageServiceTest {

	@Autowired
	private LoginService loginService;
	@Autowired
	private AdministratorService administratorService;
	
	@Before
	public void setUp() {
		PopulateDatabase.main(null);
	}

	@Test
	public void testLanguageExchangeSponsorizadosPerPolyglot() {
		authentificate("admin");
		Double avgDouble=administratorService.avgMessageForFolder();
		Integer maxInteger=administratorService.maxMessageForFolder();
		Integer minInteger=administratorService.minMessageForFolder();
		Assert.isTrue(avgDouble== 0.3);
		Assert.isTrue(maxInteger== 3);
		Assert.isTrue(minInteger== 0);
		
	
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
