package Service.CreateSponsorship;


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

import services.LanguageService;
import utilities.PopulateDatabase;

import domain.Language;
import domain.Sponsorship;
import forms.SponsorshipForm;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:spring/datasource.xml",
		"classpath:spring/config/packages.xml" })
public class SponsorshipServiceTest {
	@Autowired
	private SponsorshipService sponsorshipService;
	@Autowired
	private LanguageService languageService;

	@Autowired
	private LoginService loginService;

	@Before
	public void setUp() {
		PopulateDatabase.main(null);
	}
	// A user who is authenticated as an agent
	// must be able to manage the LanguageExchange.
	// Managing implies creating, modifying, or deleting them.

	// POSITIVE TEST CASE: An agent can create
	// a sponsorship
	@Transactional
	@Test
	public void testCreateSponsorship() {
		
		authentificate("agent1");
		SponsorshipForm sponsorshipForm = new SponsorshipForm();
		sponsorshipForm.setImg("https://projetsii.informatica.us.es/projsues");
		
		
		Collection<Language> languages = languageService.findAll();		
		
		String links="https://projetsii.informatica.us.es/projects/623q7vprzje3n44wfxr/issues, https://projetsii.informatica.us.es/projec, https://projetsii.informatica.us.es/issues/71708";
		String tag="cosas, intercambios, sanas";
		Language descriptionLanguage = null;
		Language bannerLanguage = null;
		sponsorshipForm.setLinks(links);
		sponsorshipForm.setTag(tag);
		for(Language l: languages){
			descriptionLanguage= l;
			bannerLanguage=l;
			break;
		}
		sponsorshipForm.setBannerLanguage(bannerLanguage);
		String text="Esto es asi porque si y tiene mucha informacion y tal";
		String title="esto es un titulo con informacion importante";
		sponsorshipForm.setDescriptionLanguage(descriptionLanguage);
		sponsorshipForm.setText(text);
		sponsorshipForm.setTitle(title);
		
		Sponsorship sponsorship= sponsorshipService.reconstruct(sponsorshipForm);
		
		sponsorshipService.save(sponsorship);

	}
	// Negative TEST CASE: An polyglot cannot create
	// a languageExchange with description null
	@Test(expected = IllegalArgumentException.class)
	public void testCreateSponsorshipWithoutLanguageDescription() {
		authentificate("agent1");
		SponsorshipForm sponsorshipForm = new SponsorshipForm();
		sponsorshipForm.setImg("https://projetsii.informatica.us.es/projsues");
		
		
		Collection<Language> languages = languageService.findAll();		
		
		String links="https://projetsii.informatica.us.es/projects/623q7vprzje3n44wfxr/issues, https://projetsii.informatica.us.es/projec, https://projetsii.informatica.us.es/issues/71708";
		String tag="cosas, intercambios, sanas";
		Language descriptionLanguage = null;
		Language bannerLanguage = null;
		sponsorshipForm.setLinks(links);
		sponsorshipForm.setTag(tag);
		for(Language l: languages){
		
			bannerLanguage=l;
			break;
		}
		sponsorshipForm.setBannerLanguage(bannerLanguage);
		String text="Esto es asi porque si y tiene mucha informacion y tal";
		String title="esto es un titulo con informacion importante";
		sponsorshipForm.setDescriptionLanguage(descriptionLanguage);
		sponsorshipForm.setText(text);
		sponsorshipForm.setTitle(title);
		
		Sponsorship sponsorship= sponsorshipService.reconstruct(sponsorshipForm);
		
		sponsorshipService.save(sponsorship);
	}
	// Negative TEST CASE: An anonymous cannot create
	// a sponsorship
	@Transactional
	@Test(expected = IllegalArgumentException.class)
	public void testCreateSponsorshipNotAutehntificated() {
		desauthentificate();
	
		SponsorshipForm sponsorshipForm = new SponsorshipForm();
		sponsorshipForm.setImg("https://projetsii.informatica.us.es/projsues");
		
		
		Collection<Language> languages = languageService.findAll();		
		
		String links="https://projetsii.informatica.us.es/projects/623q7vprzje3n44wfxr/issues, https://projetsii.informatica.us.es/projec, https://projetsii.informatica.us.es/issues/71708";
		String tag="cosas, intercambios, sanas";
		Language descriptionLanguage = null;
		Language bannerLanguage = null;
		sponsorshipForm.setLinks(links);
		sponsorshipForm.setTag(tag);
		for(Language l: languages){
			descriptionLanguage= l;
			bannerLanguage=l;
			break;
		}
		sponsorshipForm.setBannerLanguage(bannerLanguage);
		String text="Esto es asi porque si y tiene mucha informacion y tal";
		String title="esto es un titulo con informacion importante";
		sponsorshipForm.setDescriptionLanguage(descriptionLanguage);
		sponsorshipForm.setText(text);
		sponsorshipForm.setTitle(title);
		
		Sponsorship sponsorship= sponsorshipService.reconstruct(sponsorshipForm);
		
		sponsorshipService.save(sponsorship);
	}
	// Negative TEST CASE: An agent cannot create
	// a sponsorship with language banner null
	@Transactional
	@Test(expected = IllegalArgumentException.class)
	public void testCreateSponsorshipWithBannerLanguagesNull() {
		authentificate("agent2");
		SponsorshipForm sponsorshipForm = new SponsorshipForm();
		sponsorshipForm.setImg("https://projetsii.informatica.us.es/projsues");
		
		
		Collection<Language> languages = languageService.findAll();		
		
		String links="https://projetsii.informatica.us.es/projects/623q7vprzje3n44wfxr/issues, https://projetsii.informatica.us.es/projec, https://projetsii.informatica.us.es/issues/71708";
		String tag="cosas, intercambios, sanas";
		Language descriptionLanguage = null;
		Language bannerLanguage = null;
		sponsorshipForm.setLinks(links);
		sponsorshipForm.setTag(tag);
		for(Language l: languages){
			descriptionLanguage= l;
			
			break;
		}
		sponsorshipForm.setBannerLanguage(bannerLanguage);
		String text="Esto es asi porque si y tiene mucha informacion y tal";
		String title="esto es un titulo con informacion importante";
		sponsorshipForm.setDescriptionLanguage(descriptionLanguage);
		sponsorshipForm.setText(text);
		sponsorshipForm.setTitle(title);
		
		Sponsorship sponsorship= sponsorshipService.reconstruct(sponsorshipForm);
		
		sponsorshipService.save(sponsorship);
	}
	// Negative TEST CASE: An agent cannot create
	// a sponsorship null
	@Transactional
	@Test(expected = IllegalArgumentException.class)
	public void testCreateSponsorshipNull() {
		authentificate("agent2");
		sponsorshipService.save(null);
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
