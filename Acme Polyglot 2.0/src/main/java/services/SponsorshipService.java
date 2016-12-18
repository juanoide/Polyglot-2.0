package services;

import java.util.ArrayList;
import java.util.Collection;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;






import domain.Banner;
import domain.Description;

import domain.Sponsorship;


import domain.Agent;


import forms.SponsorshipEditForm;
import forms.SponsorshipForm;

import repositories.SponsorshipRepository;
import security.LoginService;
import security.UserAccount;




@Service
@Transactional
public class SponsorshipService {

	// Managed repository ------------------------------------------------
	@Autowired
	private SponsorshipRepository sponsorshipRepository;
	
	
	// Supporting Service -------------------------------------------------

	@Autowired
	private AgentService agentService;
	
	@Autowired
	private DescriptionService descriptionService;
	
	@Autowired
	private BannerService bannerService;

/*	@Autowired
	private LanguageExchangeService languageExchangeService;	

	
*/
	
	// Simple CRUD methods ------------------------------------
	public Collection<Sponsorship> findAll(){
		Collection<Sponsorship> result;
		result = sponsorshipRepository.findAll();
		return result;
	}
	
	
	
	public Sponsorship findOne(int id){
		Sponsorship result;
		result = sponsorshipRepository.findOne(id);
		return result;
	}
	
	public Sponsorship create(){
		Sponsorship result = new Sponsorship();
		UserAccount loginNow = LoginService.getPrincipal();
		agentService.isAgent();
			
		
		 Collection<Banner> banners = new ArrayList<Banner>();	
		
		 Collection<Description> descriptions= new ArrayList<Description>();
		
		 Agent p = agentService.findByUserAccount(loginNow);
		 
		 result.setAgent(p);
		 result.setBanners(banners);
	
		 result.setDescriptions(descriptions);

		
		return result;
	}
	
	public void save(Sponsorship sponsorship){
		Assert.notNull(sponsorship);
		Assert.isTrue(LoginService.getPrincipal().containsAuthority("AGENT"));
		Assert.isTrue(sponsorship.getAgent()==agentService.findByPrincipal());
	sponsorshipRepository.saveAndFlush(sponsorship);
	
	}
	

	
	
	
	public void saveModified(Sponsorship sponsorship) {
		Assert.notNull(sponsorship);
		Assert.isTrue(LoginService.getPrincipal().containsAuthority("AGENT"));
		Assert.isTrue(sponsorship.getAgent()==agentService.findByPrincipal());
		
		for(Description des: sponsorship.getDescriptions()){
			Assert.notNull(des);
		}
		for(Banner bann: sponsorship.getBanners()){
			Assert.notNull(bann);
		}
		sponsorshipRepository.saveAndFlush(sponsorship);
	}
	
	// Other bussiness methods ----------------------------
	
	public Collection<Sponsorship> findAllAgentLogin(int agentId){

		Collection<Sponsorship> result;
		result = sponsorshipRepository.findAllAgentLogin(agentId);		
		return result;
	}
	

	public Sponsorship reconstruct(SponsorshipForm sponsorshipForm){
		Assert.isTrue(sponsorshipForm.getDescriptionLanguage()!=null);
		Assert.isTrue(sponsorshipForm.getBannerLanguage()!=null);
		Assert.isTrue(LoginService.getPrincipal().containsAuthority("AGENT"));
		
		Sponsorship result= create();
		
	
		
		//description class--->
		
		
		Description des = descriptionService.create();
		
		des.setTitle(sponsorshipForm.getTitle());
		des.setText(sponsorshipForm.getText());
		
		//links sin parsear, vamos a parsear para separarlo por comas.
		String linksWithoutParsing = sponsorshipForm.getLinks();
		
		String[] tokens= linksWithoutParsing.split(",");
		Collection<String> links = new ArrayList<String>();
		for(String l:tokens){
			links.add(l);
	
		}
				
		des.setLinks(links);
		//tag sin parsear, vamos a parsear para separarlo por comas.
		String tagWithoutParsing = sponsorshipForm.getTag();
		
		String[] tokens2= tagWithoutParsing.split(",");
		Collection<String> tag = new ArrayList<String>();
		for(String l:tokens2){
			tag.add(l);
	
		}
		
		des.setTag(tag);
		
		des.setLanguage(sponsorshipForm.getDescriptionLanguage());
		
	
		
		
		
		result.getDescriptions().add(des);
		//Fin de description<----
		

		
		//banner class--->
		
		
		Banner banner = bannerService.create();
		
		banner.setImg(sponsorshipForm.getImg());
		
		
		banner.setLanguage(sponsorshipForm.getBannerLanguage());
		
	
		
		
		result.getBanners().add(banner);
	
		//Fin de banner<----
		
		
		result.setLanguageExchange(sponsorshipForm.getLanguageExchange());
	
		
		return result;
		
		
	}
	

	
	public SponsorshipEditForm constructForm(Sponsorship sponsorship) {
		Assert.notNull(sponsorship);
		SponsorshipEditForm lform = new SponsorshipEditForm();


		lform.setLanguageExchange(sponsorship.getLanguageExchange());
		
		return lform;
	}
	
	

	public Sponsorship reconstructEdit(SponsorshipEditForm sponsorshipForm, int sponsorshipid){
		Assert.isTrue(LoginService.getPrincipal().containsAuthority("AGENT"));
		
		Sponsorship result= sponsorshipRepository.findOne(sponsorshipid);

		Assert.isTrue(result!=null);
	
		
		
		result.setLanguageExchange(sponsorshipForm.getLanguageExchange());
	
		
		return result;
		
		
	}
	
	
	

	
	public void remove(int sponsorshipId){
		Sponsorship sponsorship = sponsorshipRepository.findOne(sponsorshipId);
		sponsorshipRepository.delete(sponsorship);
	}


	
	
	public void delete(Sponsorship sponsorship){
		Assert.isTrue(sponsorship.getAgent()==agentService.findByPrincipal());
		Assert.isTrue(LoginService.getPrincipal().containsAuthority("AGENT"));
		Assert.notNull(sponsorship);
		sponsorshipRepository.delete(sponsorship);
		
	}
	
	
	
	public Collection<Sponsorship> sponsorshipOfAgentLogin(){
		Agent pol;
		UserAccount loginNow = LoginService.getPrincipal();
		agentService.isAgent();
		pol=agentService.findByUserAccount(loginNow);
		Collection<Sponsorship> result;
		result = sponsorshipRepository.sponsorshipOfAgentID(pol.getId());
		return result;
	}
	
	
	public boolean NolastOneDescription (Sponsorship sponsorship){
	
		boolean result=false;
		
		if(sponsorship.getDescriptions().size()>1){
			result=true;
		}
		
		return result;
	}
	
	public boolean NolastOneBanner (Sponsorship sponsorship){
		
		boolean result=false;
		
		if(sponsorship.getBanners().size()>1){
			result=true;
		}
		
		return result;
	}


	
	
}