package controllers.administrator;


import java.util.ArrayList;
import java.util.Collection;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import services.ActorService;
import services.AdministratorService;
import services.LanguageService;
import services.SearchService;



import controllers.AbstractController;
import domain.Actor;
import domain.Language;
import domain.Search;



@Controller
@RequestMapping("/administrator")
public class Dashboard2AdministratorController extends AbstractController {

	// Supporting
	// Services-------------------------------------------------------
	@Autowired
	private LanguageService languageService;
	
	@Autowired
	private AdministratorService administratorService;
	
	@Autowired
	private ActorService actorService;
	
	@Autowired
	private SearchService searchService;
	

	// Constructor---------------------------------------------------------------
	public Dashboard2AdministratorController() {
		super();
		// TODO Auto-generated constructor stub
	}

	// Listing-------------------------------------------------------------------
	@RequestMapping("/dashboard2")
	public ModelAndView dashboard() {
		ModelAndView result;

		result = createDashboard();

		return result;
	}

	public ModelAndView createDashboard() {
		ModelAndView result;
		
		Collection<Language> allLanguages = languageService.findAll();
		Collection<Actor> actores = actorService.findAll();
		Collection<Search> searchs = searchService.findAll();
		Collection<Search> searchs2 = new ArrayList<Search>();
		Collection<Search> searchs3= searchService.keywordsSums();
		
		
		Double avgDouble=administratorService.avgMessageForFolder();
		Integer maxInteger=administratorService.maxMessageForFolder();
		Integer minInteger=administratorService.minMessageForFolder();
	
		Integer numerica=0;
		
		result = new ModelAndView("administrator/dashboard2");
		result.addObject("actores",actores);
		result.addObject("searchs",searchs);
		result.addObject("searchs2",searchs2);
		result.addObject("searchs3",searchs3);
		result.addObject("allLanguages",allLanguages);
		result.addObject("numerica",numerica);
		result.addObject("avgDouble",avgDouble);
		result.addObject("maxInteger",maxInteger);
		result.addObject("minInteger",minInteger);
			

		return result;
	}

}
