package services;


import java.util.Collection;
import java.util.Random;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;



import domain.Banner;
import domain.Language;

import domain.Sponsorship;

import repositories.BannerRepository;
import security.LoginService;





@Service
@Transactional
public class BannerService {

	// Managed repository ------------------------------------------------
	@Autowired
	private BannerRepository bannerRepository;
	
	@Autowired
	private SponsorshipService sponsorshipService;
	
	@Autowired
	private LanguageService languageService;

	@Autowired
	private AgentService agentService;
	

	
	
	
	// Simple CRUD methods ------------------------------------
	public Collection<Banner> findAll(){
		Collection<Banner> result;
		result = bannerRepository.findAll();
		return result;
	}
	
	public Banner findOne(int id){
		Banner result;
		result = bannerRepository.findOne(id);
		return result;
	}
	
	public Banner findOneNoId(Banner des){
		Banner result;
		result = bannerRepository.findByBanner(des);
		return result;
	}
	
	
	public Banner create(){
		Banner result = new Banner();
	
		 
		 
		return result;
	}
	
	
	// Other bussiness methods ----------------------------
	

	public void remove(int bannerId){
		Banner banner = bannerRepository.findOne(bannerId);
		bannerRepository.delete(banner);
	}

	public void deleteAllBanner(Collection<Banner> banners) {
		bannerRepository.deleteInBatch(banners);
		
	}
	
	
	public void save(Banner banner){
		Assert.notNull(banner);
		 bannerRepository.saveAndFlush(banner);
	}
	
	public void saveModified(Banner banner){
		Assert.notNull(banner);
		 bannerRepository.saveAndFlush(banner);
	}
	

	public void delete(Banner bannerId){
		bannerRepository.delete(bannerId);
	}
	

	public void deleteWithSponsorship(Banner bannerId, int sponsorshipId){
		Sponsorship exc = sponsorshipService.findOne(sponsorshipId);
		Assert.isTrue(exc.getAgent()==agentService.findByPrincipal(), "You don´t have permission");
		Assert.isTrue(LoginService.getPrincipal().containsAuthority("AGENT"));
		Assert.notNull(bannerId);
		
		exc.getBanners().remove(bannerId);
		
		bannerRepository.delete(bannerId);
		
		sponsorshipService.saveModified(exc);
		
		
	}
	
	
	public Collection<Banner> bannersOFSponsorship(int id){
		
		Sponsorship exc = sponsorshipService.findOne(id);
		
		Collection<Banner> banners=exc.getBanners();
		
		return banners;
		
		
		
	}
	
	public Collection<Banner> bannersOFLanguage(int id){
		
		Language exc = languageService.findOne(id);
		
		Collection<Banner> banners=exc.getBanners();
		
		return banners;
		
		
		
	}
	
	public Banner bannerRandom(Collection<Banner> banners){
		Banner banner = null;
		Random  rnd = new Random();
		int variable= (int)(rnd.nextDouble() * banners.size());
		int i=0;
		for(Banner bann: banners){
			if(variable==i){
				banner=bann;
				break;
			}
			i++;
		}
	
		
		
		
		return banner;
		
		
		
	}
	

	
	
}