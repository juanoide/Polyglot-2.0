package controllers.administrator;


import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import services.LanguageExchangeService;
import services.LanguageService;
import services.PolyglotService;


import controllers.AbstractController;
import domain.Language;
import domain.LanguageExchange;
import domain.Polyglot;


@Controller
@RequestMapping("/administrator")
public class DashboardAdministratorController extends AbstractController {

	// Supporting
	// Services-------------------------------------------------------
	@Autowired
	private LanguageService languageService;
	
	@Autowired
	private LanguageExchangeService languageExchangeService;
	
	@Autowired
	private PolyglotService polyglotService;
	// Constructor---------------------------------------------------------------
	public DashboardAdministratorController() {
		super();
		// TODO Auto-generated constructor stub
	}

	// Listing-------------------------------------------------------------------
	@RequestMapping("/dashboard")
	public ModelAndView dashboard() {
		ModelAndView result;

		result = createDashboard();

		return result;
	}

	public ModelAndView createDashboard() {
		ModelAndView result;
		
		Collection<Language> allLanguages = languageService.findAll();
		
		Collection<LanguageExchange> allLanguageExchanges = languageExchangeService.findAll();
		
		Collection<Polyglot> allPolyglot = polyglotService.findAll();
		Double avgDouble=languageExchangeService.languageExchangeAvgSponsorizedPerPolyglot();
		Integer maxInteger=languageExchangeService.languageExchangeMaxSponsorizedPerPolyglot();
		Integer minInteger=languageExchangeService.languageExchangeMinSponsorizedPerPolyglot();
	
		Integer numerica=0;
		
		result = new ModelAndView("administrator/dashboard");
		result.addObject("allLanguageExchanges", allLanguageExchanges);
		result.addObject("allLanguages",allLanguages);
		result.addObject("allPolyglot",allPolyglot);
		result.addObject("numerica",numerica);
		result.addObject("avgDouble",avgDouble);
		result.addObject("maxInteger",maxInteger);
		result.addObject("minInteger",minInteger);
		
		
		
		
		
		
		
		

		return result;
	}

}
