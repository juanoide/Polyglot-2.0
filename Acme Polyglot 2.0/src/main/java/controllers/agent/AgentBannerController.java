package controllers.agent;

import java.util.ArrayList;
import java.util.Collection;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import controllers.AbstractController;


import domain.Banner;
import domain.Language;
import domain.Sponsorship;
import domain.Agent;


import security.LoginService;

import services.BannerService;
import services.SponsorshipService;
import services.LanguageService;
import services.AgentService;

@Controller
@RequestMapping("/banner")
public class AgentBannerController extends AbstractController{

	public AgentBannerController() {
		super();
		
	}

	@Autowired
	private BannerService bannerService;
	
	@Autowired
	private LanguageService languageService;
	
	@Autowired
	private SponsorshipService sponsorshipService;
	
	@Autowired
	private AgentService agentService;
	

	
	@RequestMapping("/sponsorship/agent")
	public ModelAndView listOfSponsorship(@RequestParam int sponsorshipId,@RequestParam int languageId) {
		ModelAndView result;
		Collection<Banner> banners =new ArrayList<Banner>();
		Collection<Banner> bannersOfLanguage =new ArrayList<Banner>();
		String en= "en";
		Language english =languageService.searchLanguageForCode(en);
		Sponsorship sponsorship = sponsorshipService.findOne(sponsorshipId);
		Language language = languageService.findOne(languageId);
		banners = bannerService.bannersOFSponsorship(sponsorshipId);
		boolean isMine = false;
		
		
		Agent c = agentService.findByPrincipal();
		
		for(Banner des: banners){
			if(des.getLanguage()==language){
				bannersOfLanguage.add(des);
			}
		}
		
		if(bannersOfLanguage.isEmpty()){
				for(Banner des2:banners){
					if(des2.getLanguage()==english){
						bannersOfLanguage.add(des2);
					}
				}
					
			}
		
		if(bannersOfLanguage.isEmpty()){
			for(Banner des2:banners){
					bannersOfLanguage.add(des2);
					break;
				}
			}
		
		result = new ModelAndView("banner/sponsorship/agent");
		result.addObject("isMine",isMine);
		result.addObject("agentprincipal",c);
		result.addObject("sponsorship",sponsorship);
		result.addObject("languageId",languageId);
		result.addObject("language",language);
		result.addObject("sponsorshipId",sponsorshipId);
		result.addObject("banners", bannersOfLanguage);
		result.addObject("requestURI", "banner/sponsorship/agent.do");

		return result;
	}
	
	
	
	//edit banner de intercambios de lenguaje:
	
	

	@RequestMapping(value = "/sponsorship/agent/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam int bannerId, @RequestParam int sponsorshipId) {
		ModelAndView result;
		Collection<Language> languages = languageService.findAll();
		
		Assert.notNull(languages);
		Assert.isTrue(LoginService.getPrincipal().containsAuthority("AGENT"));
		Banner banner;
	
		banner =bannerService.findOne(bannerId);
		
		result = new ModelAndView("banner/sponsorship/agent/edit");		
		result.addObject("languages",languages);
		result.addObject("sponsorshipId",sponsorshipId);
		result.addObject("banner",banner);
		result.addObject("requestURI", "banner/sponsorship/agent/edit.do");
			 
		return result;
	}
	
	
	
	@RequestMapping(value = "/sponsorship/agent/edit", method = RequestMethod.POST, params="save")
	public ModelAndView registerAnotherAgent(@RequestParam int sponsorshipId, @Valid Banner banner, BindingResult binding) {
		ModelAndView result;
		Collection<Language> languages = languageService.findAll();
		
		Assert.notNull(languages);
		Assert.isTrue(LoginService.getPrincipal().containsAuthority("AGENT"));
		
		
		if (binding.hasErrors()) {
			result = new ModelAndView();
			result.addObject("languages",languages);
			result.addObject("sponsorshipId",sponsorshipId);
			result.addObject("banner", banner);
			BindingResult bR = binding;
			System.out.println(bR);
		} else {
			try {
			
				Sponsorship langExch = sponsorshipService.findOne(sponsorshipId);
				for(Banner des: langExch.getBanners()){
					if(banner.getLanguage()==des.getLanguage()){
						langExch.getBanners().remove(des);
						break;
					}
				}
				bannerService.saveModified(banner);
				langExch.getBanners().add(banner);
				sponsorshipService.saveModified(langExch);
				
				result = new ModelAndView("redirect:/sponsorship/agent/list.do");
				
			} catch (Throwable oops) {
				result = new ModelAndView();
				result.addObject("languages",languages);
				result.addObject("sponsorshipId",sponsorshipId);
				result.addObject("banner",banner);
				result.addObject("message", "lform.commit.error");
				}
					
		}
		return result;
		
	}
	
	protected ModelAndView createEditModelAndView(Banner des){
		ModelAndView result;
	
		result = createEditModelAndView(des,null);
		
		
		
		return result;
	}
	
	
	protected ModelAndView createEditModelAndView(Banner des, String message){
		ModelAndView result;
	
		String requestURI="banner/sponsorship/agent/edit.do";
		
		result = new ModelAndView("banner/sponsorship/agent/edit");
		
		Collection<Language> languages = languageService.findAll();
		
		Assert.notNull(languages);
		
		result.addObject("languages",languages);
		
		result.addObject("banner",des);
		
		result.addObject("message",message);

		result.addObject("requestURI",requestURI);
		
		return result;
	}
	
	@RequestMapping("/sponsorship/agent/delete")
	public ModelAndView delete(@RequestParam int bannerId, @RequestParam int sponsorshipId, @RequestParam int languageId) {
		ModelAndView result;
		String message;
		Sponsorship lanExc2 =sponsorshipService.findOne(sponsorshipId);
		Banner des =bannerService.findOne(bannerId);
		
		boolean noLastOne=true;
		
		noLastOne=sponsorshipService.NolastOneBanner(lanExc2);
		
		
		try {
			Assert.isTrue(lanExc2.getAgent()==agentService.findByPrincipal(), "You don´t have permission");
			Assert.isTrue(noLastOne, "Is the last one");
			
			bannerService.deleteWithSponsorship(des, sponsorshipId);
			result = new ModelAndView("redirect:/sponsorship/agent/list.do");
		} catch (Throwable oops) {
			if(lanExc2.getAgent()!=agentService.findByPrincipal()){
			message= "error.Banner.delete";
			}else if(noLastOne==false){
				message="error.theLastOneBanner";
			}else{
				message="error";
			}
			result = createListModelAndView(sponsorshipId, languageId, message);
			
			
			}	
		return result;
	}
	
	protected ModelAndView createListModelAndView(@RequestParam int sponsorshipId,@RequestParam int languageId, String message){
		ModelAndView result;
		Collection<Banner> banners =new ArrayList<Banner>();
		Collection<Banner> bannersOfLanguage =new ArrayList<Banner>();
		String en= "en";
		Language english =languageService.searchLanguageForCode(en);
		Sponsorship sponsorship = sponsorshipService.findOne(sponsorshipId);
		Language language = languageService.findOne(languageId);
		banners = bannerService.bannersOFSponsorship(sponsorshipId);
		boolean isMine = false;
		Agent c = agentService.findByPrincipal();
		
		for(Banner des: banners){
			if(des.getLanguage()==language){
				bannersOfLanguage.add(des);
			}
		}
		
		if(bannersOfLanguage.isEmpty()){
				for(Banner des2:sponsorship.getBanners()){
					if(des2.getLanguage()==english){
						bannersOfLanguage.add(des2);
					}
				}
					
			}
		
		if(bannersOfLanguage.isEmpty()){
			for(Banner des2:sponsorship.getBanners()){
					bannersOfLanguage.add(des2);
					break;
				}
			}
		
		result = new ModelAndView("banner/sponsorship/agent");
		result.addObject("isMine",isMine);
		result.addObject("agentprincipal",c);
		result.addObject("message",message);
		result.addObject("sponsorship",sponsorship);
		result.addObject("sponsorshipId",sponsorshipId);
		result.addObject("language",language);
		result.addObject("languageId",languageId);
		result.addObject("banners", bannersOfLanguage);
		result.addObject("requestURI", "banner/sponsorship/agent.do");

		return result;
		}


	
}
