package services;

import java.util.ArrayList;
import java.util.Collection;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;



import domain.Description;
import domain.Language;
import domain.LanguageExchange;
import domain.Sponsorship;

import repositories.DescriptionRepository;
import security.LoginService;




@Service
@Transactional
public class DescriptionService {

	// Managed repository ------------------------------------------------
	@Autowired
	private DescriptionRepository descriptionRepository;
	
	@Autowired
	private LanguageExchangeService languageExchangeService;
	
	@Autowired
	private LanguageService languageService;

	@Autowired
	private PolyglotService polyglotService;
	
	@Autowired
	private AgentService agentService;
	
	@Autowired
	private SponsorshipService sponsorshipService;
	
	
	
	// Simple CRUD methods ------------------------------------
	public Collection<Description> findAll(){
		Collection<Description> result;
		result = descriptionRepository.findAll();
		return result;
	}
	
	public Description findOne(int id){
		Description result;
		result = descriptionRepository.findOne(id);
		return result;
	}
	
	public Description findOneNoId(Description des){
		Description result;
		result = descriptionRepository.findByDescription(des);
		return result;
	}
	
	
	public Description create(){
		Description result = new Description();
	
		 Collection<String> links = new ArrayList<String>();
		 Collection<String> tag = new ArrayList<String>();
		
		 result.setLinks(links);
		 result.setTag(tag);
		 
		return result;
	}
	
	
	// Other bussiness methods ----------------------------
	

	public void remove(int descriptionId){
		Description description = descriptionRepository.findOne(descriptionId);
		descriptionRepository.delete(description);
	}

	public void deleteAllDescription(Collection<Description> descriptions) {
		descriptionRepository.deleteInBatch(descriptions);
		
	}
	
	
	public void save(Description description){
		Assert.notNull(description);
		 descriptionRepository.saveAndFlush(description);
	}
	
	public void saveModified(Description description){
		Assert.notNull(description);
		 descriptionRepository.saveAndFlush(description);
	}
	

	public void delete(Description descriptionId){
		descriptionRepository.delete(descriptionId);
	}
	

	public void deleteWithLanguageExchange(Description descriptionId, int languageExchangeId){
		LanguageExchange exc = languageExchangeService.findOne(languageExchangeId);
		Assert.isTrue(LoginService.getPrincipal().containsAuthority("POLYGLOT"));
		Assert.isTrue(exc.getPolyglotOrganise()==polyglotService.findByPrincipal(), "You don´t have permission");
		Assert.notNull(descriptionId);
		Assert.notNull(exc);
		exc.getDescriptions().remove(descriptionId);
		
		descriptionRepository.delete(descriptionId);
		
		languageExchangeService.saveModified(exc);
		
		
	}
	
	public void deleteWithLanguage(Description descriptionId, int languageId){
		Language exc = languageService.findOne(languageId);
		Assert.isTrue(LoginService.getPrincipal().containsAuthority("ADMIN"));
		Assert.notNull(descriptionId);
		Assert.notNull(exc);
		exc.getDescriptionsOwners().remove(descriptionId);
		
		descriptionRepository.delete(descriptionId);
		
		languageService.saveModified(exc);
		
		
	}
	
	public void deleteWithSponsorship(Description descriptionId, int sponsorshipId){
		Sponsorship exc = sponsorshipService.findOne(sponsorshipId);
		Assert.isTrue(LoginService.getPrincipal().containsAuthority("AGENT"));
		Assert.isTrue(exc.getAgent()==agentService.findByPrincipal());
		Assert.notNull(descriptionId);
		Assert.notNull(exc);
		exc.getDescriptions().remove(descriptionId);
		
		descriptionRepository.delete(descriptionId);
		
		sponsorshipService.saveModified(exc);
		
		
	}
	
	
	public Collection<Description> descriptionsOFLanguageExchange(int id){
		
		LanguageExchange exc = languageExchangeService.findOne(id);
		
		Collection<Description> descriptions=exc.getDescriptions();
		
		return descriptions;
		
		
		
	}
	
	public Collection<Description> descriptionsOFLanguage(int id){
		
		Language exc = languageService.findOne(id);
		
		Collection<Description> descriptions=exc.getDescriptionsOwners();
		
		return descriptions;
		
		
		
	}
	

	public Collection<Description> descriptionsOFSponsorship(int id){
		
		Sponsorship exc = sponsorshipService.findOne(id);
		
		Collection<Description> descriptions=exc.getDescriptions();
		
		return descriptions;
		
		
		
	}
	

	
	
}