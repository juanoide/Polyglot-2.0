package controllers.polyglot;

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
import domain.Description;
import domain.Language;
import domain.LanguageExchange;
import domain.Polyglot;
import domain.Sponsorship;


import security.LoginService;

import services.BannerService;
import services.DescriptionService;
import services.LanguageExchangeService;
import services.LanguageService;
import services.PolyglotService;

@Controller
@RequestMapping("/description")
public class PolyglotDescriptionController extends AbstractController{

	public PolyglotDescriptionController() {
		super();
		// TODO Auto-generated constructor stub
	}

	@Autowired
	private DescriptionService descriptionService;
	
	@Autowired
	private LanguageService languageService;
	
	@Autowired
	private LanguageExchangeService languageExchangeService;
	
	@Autowired
	private PolyglotService polyglotService;
	
	@Autowired
	private BannerService bannerService;
	

	
	@RequestMapping("/languageExchange/polyglot")
	public ModelAndView listOfLanguageExchange(@RequestParam int languageExchangeId,@RequestParam int languageId) {
		ModelAndView result;
		Collection<Description> descriptions =new ArrayList<Description>();
		Collection<Description> descriptionsOfLanguage =new ArrayList<Description>();
		String en= "en";
		Language english =languageService.searchLanguageForCode(en);
		LanguageExchange languageExchange = languageExchangeService.findOne(languageExchangeId);
		Language language = languageService.findOne(languageId);
		descriptions = descriptionService.descriptionsOFLanguageExchange(languageExchangeId);
		boolean isMine = false;
		Collection<Banner> banners = new ArrayList<Banner>();
		for(Sponsorship sponsorship: languageExchange.getSponsorships()){
			for(Banner bann: sponsorship.getBanners()){
				if(bann.getLanguage().getCode().equals(language.getCode())){
					banners.add(bann);
					
				}
			}
		}
		Banner banner=null;
		if(!(banners.isEmpty())){
		banner=bannerService.bannerRandom(banners);
		}
		Polyglot c = polyglotService.findByPrincipal();
		
		for(Description des: descriptions){
			if(des.getLanguage()==language){
				descriptionsOfLanguage.add(des);
			}
		}
		
		if(descriptionsOfLanguage.isEmpty()){
				for(Description des2:descriptions){
					if(des2.getLanguage()==english){
						descriptionsOfLanguage.add(des2);
					}
				}
					
			}
		
		if(descriptionsOfLanguage.isEmpty()){
			for(Description des2:descriptions){
					descriptionsOfLanguage.add(des2);
					break;
				}
			}
		
		result = new ModelAndView("description/languageExchange/polyglot");
		if(banner!=null){
			result.addObject("banner",banner.getImg());
		}
	
		result.addObject("isMine",isMine);
		result.addObject("polyglotprincipal",c);
		result.addObject("languageExchange",languageExchange);
		result.addObject("languageId",languageId);
		result.addObject("language",language);
		result.addObject("languageExchangeId",languageExchangeId);
		result.addObject("descriptions", descriptionsOfLanguage);
		result.addObject("requestURI", "description/languageExchange/polyglot.do");

		return result;
	}
	
	
	
	//edit description de intercambios de lenguaje:
	
	

	@RequestMapping(value = "/languageExchange/polyglot/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam int descriptionId, @RequestParam int languageExchangeId) {
		ModelAndView result;
		Collection<Language> languages = languageService.findAll();
		
		Assert.notNull(languages);
		Assert.isTrue(LoginService.getPrincipal().containsAuthority("POLYGLOT"));
		Description description;
	
		description =descriptionService.findOne(descriptionId);
		
		result = new ModelAndView("description/languageExchange/polyglot/edit");		
		result.addObject("languages",languages);
		result.addObject("languageExchangeId",languageExchangeId);
		result.addObject("description",description);
		result.addObject("requestURI", "description/languageExchange/polyglot/edit.do");
			 
		return result;
	}
	
	
	
	@RequestMapping(value = "/languageExchange/polyglot/edit", method = RequestMethod.POST, params="save")
	public ModelAndView registerAnotherAgent(@RequestParam int languageExchangeId, @Valid Description description, BindingResult binding) {
		ModelAndView result;
		Collection<Language> languages = languageService.findAll();
		
		Assert.notNull(languages);
		Assert.isTrue(LoginService.getPrincipal().containsAuthority("POLYGLOT"));
		
		
		if (binding.hasErrors()) {
			result = new ModelAndView();
			result.addObject("languages",languages);
			result.addObject("languageExchangeId",languageExchangeId);
			result.addObject("description", description);
			BindingResult bR = binding;
			System.out.println(bR);
		} else {
			try {
				descriptionService.saveModified(description);
				LanguageExchange langExch = languageExchangeService.findOne(languageExchangeId);
				for(Description des: langExch.getDescriptions()){
					if(description.getLanguage()==des.getLanguage()){
						langExch.getDescriptions().remove(des);
						break;
					}
				}
				langExch.getDescriptions().add(description);
				languageExchangeService.saveModified(langExch);
				
				result = new ModelAndView("redirect:/languageExchange/polyglot/list.do");
				
			} catch (Throwable oops) {
				result = new ModelAndView();
				result.addObject("languages",languages);
				result.addObject("languageExchangeId",languageExchangeId);
				result.addObject("description",description);
				result.addObject("message", "lform.commit.error");
				}
					
		}
		return result;
		
	}
	
	protected ModelAndView createEditModelAndView(Description des){
		ModelAndView result;
	
		result = createEditModelAndView(des,null);
		
		
		
		return result;
	}
	
	
	protected ModelAndView createEditModelAndView(Description des, String message){
		ModelAndView result;
	
		String requestURI="description/languageExchange/polyglot/edit.do";
		
		result = new ModelAndView("description/languageExchange/polyglot/edit");
		
		Collection<Language> languages = languageService.findAll();
		
		Assert.notNull(languages);
		
		result.addObject("languages",languages);
		
		result.addObject("description",des);
		
		result.addObject("message",message);

		result.addObject("requestURI",requestURI);
		
		return result;
	}
	
	@RequestMapping("/languageExchange/polyglot/delete")
	public ModelAndView delete(@RequestParam int descriptionId, @RequestParam int languageExchangeId, @RequestParam int languageId) {
		ModelAndView result;
		String message;
		LanguageExchange lanExc2 =languageExchangeService.findOne(languageExchangeId);
		Description des =descriptionService.findOne(descriptionId);
		
		boolean noLastOne=true;
		
		noLastOne=languageExchangeService.NolastOneDescription(lanExc2);
		
		
		try {
			Assert.isTrue(lanExc2.getPolyglotOrganise()==polyglotService.findByPrincipal(), "You don´t have permission");
			Assert.isTrue(noLastOne, "Is the last one");
			
			descriptionService.deleteWithLanguageExchange(des, languageExchangeId);
			result = new ModelAndView("redirect:/languageExchange/polyglot/list.do");
		} catch (Throwable oops) {
			if(lanExc2.getPolyglotOrganise()!=polyglotService.findByPrincipal()){
			message= "error.Description.delete";
			}else if(noLastOne==false){
				message="error.theLastOneDescription";
			}else{
				message="error";
			}
			result = createListModelAndView(languageExchangeId, languageId, message);
			
			
			}	
		return result;
	}
	
	protected ModelAndView createListModelAndView(@RequestParam int languageExchangeId,@RequestParam int languageId, String message){
		ModelAndView result;
		Collection<Description> descriptions =new ArrayList<Description>();
		Collection<Description> descriptionsOfLanguage =new ArrayList<Description>();
		String en= "en";
		Language english =languageService.searchLanguageForCode(en);
		LanguageExchange languageExchange = languageExchangeService.findOne(languageExchangeId);
		Language language = languageService.findOne(languageId);
		descriptions = descriptionService.descriptionsOFLanguageExchange(languageExchangeId);
		boolean isMine = false;
		Polyglot c = polyglotService.findByPrincipal();
		
		for(Description des: descriptions){
			if(des.getLanguage()==language){
				descriptionsOfLanguage.add(des);
			}
		}
		
		if(descriptionsOfLanguage.isEmpty()){
				for(Description des2:languageExchange.getDescriptions()){
					if(des2.getLanguage()==english){
						descriptionsOfLanguage.add(des2);
					}
				}
					
			}
		
		if(descriptionsOfLanguage.isEmpty()){
			for(Description des2:languageExchange.getDescriptions()){
					descriptionsOfLanguage.add(des2);
					break;
				}
			}
		
		result = new ModelAndView("description/languageExchange/polyglot");
		result.addObject("isMine",isMine);
		result.addObject("polyglotprincipal",c);
		result.addObject("message",message);
		result.addObject("languageExchange",languageExchange);
		result.addObject("languageExchangeId",languageExchangeId);
		result.addObject("language",language);
		result.addObject("languageId",languageId);
		result.addObject("descriptions", descriptionsOfLanguage);
		result.addObject("requestURI", "description/languageExchange/polyglot.do");

		return result;
		}


	
}
