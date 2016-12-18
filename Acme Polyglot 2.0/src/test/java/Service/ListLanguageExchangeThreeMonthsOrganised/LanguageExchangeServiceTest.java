package Service.ListLanguageExchangeThreeMonthsOrganised;

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
import utilities.PopulateDatabase;
import domain.LanguageExchange;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:spring/datasource.xml",
		"classpath:spring/config/packages.xml" })
public class LanguageExchangeServiceTest {
	@Autowired
	LanguageExchangeService languageExchangeService;
	@Autowired
	private LoginService loginService;

	@Before
	public void setUp() {
		PopulateDatabase.main(null);
	}
	// A user must be able list all language Exchange 3 months future.

	// POSITIVE TEST CASE: An user can list all language Exchange 3 months future.
	@Test
	public void testFindAllLanguageExchangeThreeMontsAgo() {
		Collection<LanguageExchange> LanguageExchanges = languageExchangeService.listExchangeFutureOrganised3Months();
		Assert.notNull(LanguageExchanges);
	Date now= new Date();
	boolean res=true;
	
	java.util.Date fecha = new Date();
	Calendar fecha2 = Calendar.getInstance();
	fecha2.add(Calendar.MONTH, 3);
	//fecha2.setTimeInMillis(fecha.getTime());
	Date date2=fecha2.getTime();
		for (LanguageExchange LanguageExchange : LanguageExchanges) {
	
			res=res&&(LanguageExchange.getDate().after(fecha))&&(LanguageExchange.getDate().before(date2));
		
	}
		Assert.isTrue(res);
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
