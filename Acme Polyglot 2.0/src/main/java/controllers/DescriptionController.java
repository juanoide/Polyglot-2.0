package controllers;

import java.util.ArrayList;
import java.util.Collection;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;


import domain.Banner;
import domain.Description;
import domain.Language;
import domain.LanguageExchange;
import domain.Sponsorship;





import services.BannerService;
import services.DescriptionService;
import services.LanguageExchangeService;
import services.LanguageService;


@Controller
@RequestMapping("/description")
public class DescriptionController extends AbstractController{

	public DescriptionController() {
		super();
		// TODO Auto-generated constructor stub
	}

	@Autowired
	private DescriptionService descriptionService;
	
	@Autowired
	private LanguageService languageService;
	
	@Autowired
	private BannerService bannerService;
	
	@Autowired
	private LanguageExchangeService languageExchangeService;
	
	@RequestMapping("/languageExchange")
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
				if(bann.getLanguage().getCode().equals(language)){
					banners.add(bann);
				}
			}
		}
		Banner banner=null;
		if(!(banners.isEmpty())){
		banner=bannerService.bannerRandom(banners);
		}
	
		
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
		
		result = new ModelAndView("description/languageExchange");
		if(banner!=null){
			result.addObject("banner",banner.getImg());
		}
		result.addObject("isMine",isMine);
		result.addObject("languageExchange",languageExchange);
		result.addObject("languageId",languageId);
		result.addObject("language",language);
		result.addObject("languageExchangeId",languageExchangeId);
		result.addObject("descriptions", descriptionsOfLanguage);
		result.addObject("requestURI", "description/languageExchange.do");

		return result;
	}
	
	
	
	
}
