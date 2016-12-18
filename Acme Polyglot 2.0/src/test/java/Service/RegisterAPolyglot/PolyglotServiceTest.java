package Service.RegisterAPolyglot;


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


import domain.Polyglot;
import forms.PolyglotForm;

import security.LoginService;
import services.PolyglotService;
import utilities.PopulateDatabase;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:spring/datasource.xml",
		"classpath:spring/config/packages.xml" })
public class PolyglotServiceTest {

	@Autowired
	private PolyglotService PolyglotService;
	@Autowired
	private LoginService loginService;

	@Before
	public void setUp() {
		PopulateDatabase.main(null);
	}
	// A user who is not authenticated 
				// must be able register how polyglot

				// POSITIVE TEST CASE: register polyglot
	@Test
	public void RegisterAPolyglot() {

		PolyglotForm PolyglotForm = new PolyglotForm();
		Polyglot Polyglot = PolyglotService.create();

		PolyglotForm.setName("Prueba1");
		PolyglotForm.setSurname("Prueba1");
		PolyglotForm.setPhoneNumber("6590876512");
		PolyglotForm.setEmail("Prueba1@gmail.es");

	
		PolyglotForm.setUsername("Prueba1");
		PolyglotForm.setPassword("Prueba1");
		PolyglotForm.setRepeatPassword("Prueba1");
		PolyglotForm.setValid(true);

		Polyglot = PolyglotService.reconstruct(PolyglotForm);

		PolyglotService.save(Polyglot);

		authentificate("Prueba1");

		Polyglot Polyglot2 = PolyglotService.findByPrincipal();

		Assert.notNull(Polyglot2);
	}
	// NEGATIVE TEST CASE: register polyglot WITH WRONG PASSWORD
	@Test(expected = IllegalArgumentException.class)
	public void RegisterAPolyglotWrongPassword() {

		PolyglotForm PolyglotForm = new PolyglotForm();
		Polyglot Polyglot = PolyglotService.create();

		PolyglotForm.setName("Prueba1");
		PolyglotForm.setSurname("Prueba1");
		PolyglotForm.setEmail("Prueba1@gmail.es");

	

		PolyglotForm.setUsername("Prueba1");
		PolyglotForm.setPassword("Contraseña Incorrecta");
		PolyglotForm.setRepeatPassword("Prueba1");
		PolyglotForm.setValid(true);

		Polyglot = PolyglotService.reconstruct(PolyglotForm);

		PolyglotService.save(Polyglot);
	}
	// NEGATIVE TEST CASE: register polyglot WITH WRONG DATA
	@Test(expected = IllegalArgumentException.class)
	public void RegisterAPolyglotNotValid() {

		PolyglotForm PolyglotForm = new PolyglotForm();
		Polyglot Polyglot = PolyglotService.create();

		PolyglotForm.setName("Prueba1");
		PolyglotForm.setSurname("Prueba1");
		PolyglotForm.setEmail("Prueba1@gmail.es");

	
		
		PolyglotForm.setUsername("Prueba1");
		PolyglotForm.setPassword("Contraseña Incorrecta");
		PolyglotForm.setRepeatPassword("Prueba1");
		PolyglotForm.setValid(false);

		Polyglot = PolyglotService.reconstruct(PolyglotForm);

		PolyglotService.save(Polyglot);
	}
	// NEGATIVE TEST CASE: register polyglot WITH POLYGLOT NULL
	@Test(expected = IllegalArgumentException.class)
	public void RegisterAPolyglotNotPolyglotInSave() {

		PolyglotForm PolyglotForm = new PolyglotForm();
		Polyglot Polyglot = PolyglotService.create();

		PolyglotForm.setName("Prueba1");
		PolyglotForm.setSurname("Prueba1");
		PolyglotForm.setEmail("Prueba1@gmail.es");

	

	

		PolyglotForm.setUsername("Prueba1");
		PolyglotForm.setPassword("Contraseña Incorrecta");
		PolyglotForm.setRepeatPassword("Prueba1");
		PolyglotForm.setValid(true);

		Polyglot = PolyglotService.reconstruct(PolyglotForm);

		PolyglotService.save(null);
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
