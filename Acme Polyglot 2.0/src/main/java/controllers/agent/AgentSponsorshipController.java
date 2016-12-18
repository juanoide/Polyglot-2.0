package controllers.agent;


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



import domain.Description;


import domain.Banner;
import domain.Language;
import domain.LanguageExchange;
import domain.Sponsorship;
import domain.Agent;


import forms.SponsorshipEditForm;
import forms.SponsorshipForm;




import security.LoginService;
import services.BannerService;
import services.DescriptionService;
import services.LanguageExchangeService;
import services.SponsorshipService;
import services.LanguageService;
import services.AgentService;


@Controller
@RequestMapping("/sponsorship")
public class AgentSponsorshipController extends AbstractController{
	
	// Managed service ----------------------------------------------
	

	
	@Autowired
	private SponsorshipService sponsorshipService;

	@Autowired
	private LanguageService languageService;
	
	@Autowired
	private LanguageExchangeService languageExchangeService;
	
	@Autowired
	private DescriptionService descriptionService;
	
	
	@Autowired
	private BannerService bannerService;

	@Autowired
	private AgentService agentService;

	
	// Constructors -------------------------------------------------
	//Register -------------------------------------------------------
	@RequestMapping(value="/agent/register", method= RequestMethod.GET)
	public ModelAndView create(){
		ModelAndView result;
		
		SponsorshipForm lform = new SponsorshipForm();
		
		result=createEditModelAndView(lform);
		

		return result;
	}
	
	@RequestMapping(value="/agent/register", method= RequestMethod.POST,params="save")
	public ModelAndView save(@Valid SponsorshipForm lform,  BindingResult binding){
		ModelAndView result;
		Sponsorship lexc;
		if (binding.hasErrors()) {
			result=createEditModelAndView(lform);
		} else {
			try {
		
				Assert.isTrue(LoginService.getPrincipal().containsAuthority("AGENT"));
			
					lexc= sponsorshipService.reconstruct(lform);
					
					
			
				sponsorshipService.save(lexc);
					

			
				result = new ModelAndView("redirect:/sponsorship/agent/list.do");
				
			} catch (Throwable oops) {
				result=createEditModelAndView(lform,"lform.commit.error");

			}
		}
		return result;
		
	}


	//editar sponsorship



	@RequestMapping(value = "/agent/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam int sponsorshipId) {
		ModelAndView result;
		Sponsorship lanExc;
		SponsorshipEditForm lform;
		String message;
		
		lanExc =sponsorshipService.findOne(sponsorshipId);
		try{
			
		
		Assert.isTrue(lanExc.getAgent()==agentService.findByPrincipal(), "You don´t have access to this place");

		
		Assert.isTrue(LoginService.getPrincipal().containsAuthority("AGENT"));
		
		
		lanExc =sponsorshipService.findOne(sponsorshipId);
		
		
		lform = sponsorshipService.constructForm(lanExc);
		
		result=createEditModelAndView(lform);
		} catch(Throwable oops){
			
			if(lanExc.getAgent()!=agentService.findByPrincipal()){
			
				message= "error.usuario.creation";
			}else{
				message="error";
			}
			result = createListModelAndView(message);
			
		}
		
		return result;
	}



	@RequestMapping(value = "/agent/edit", method = RequestMethod.POST, params="save")
	public ModelAndView edit(@RequestParam int sponsorshipId, @Valid SponsorshipEditForm sponsorshipEditForm, BindingResult binding) {
		ModelAndView result;

		Sponsorship lanExc2 =sponsorshipService.findOne(sponsorshipId);
		

		Collection<LanguageExchange> languageExchanges = languageExchangeService.findAll();
		
		Assert.notNull(languageExchanges);
		


		
		
		if (binding.hasErrors()) {
			result = new ModelAndView();
			result.addObject("languageExchanges",languageExchanges);
			result.addObject("sponsorshipEditForm", sponsorshipEditForm);
			
			BindingResult bR = binding;
			System.out.println(bR);
		} else {
			try {
				
				Assert.isTrue(LoginService.getPrincipal().containsAuthority("AGENT"));
				Assert.isTrue(lanExc2.getAgent()==agentService.findByPrincipal(), "You don´t have permission");
				Sponsorship lanExc = sponsorshipService.reconstructEdit(sponsorshipEditForm, sponsorshipId);
				
				sponsorshipService.saveModified(lanExc);
				
				result = new ModelAndView("redirect:/sponsorship/agent/list.do");
				
			} catch (Throwable oops) {
				result=createEditModelAndView(sponsorshipEditForm);
				
					if(!(lanExc2.getAgent()==agentService.findByPrincipal())){
						
						result.addObject("message", "error.usuario.creation");
					}else{
						result.addObject("message", "error");
					}	
				

			}
		}
		return result;
		
	}


	

	@RequestMapping(value= "/agent/addDescription", method= RequestMethod.GET)
	public ModelAndView createDescription(@RequestParam int sponsorshipId) {
		ModelAndView result;
		Sponsorship lanExc;
		Collection<Language> languages = languageService.findAll();
		Description description = new Description();
		String message;
		lanExc =sponsorshipService.findOne(sponsorshipId);
		
		try{
		Assert.notNull(languages);
		Assert.isTrue(lanExc.getAgent()==agentService.findByPrincipal(), "You don´t have access to this place");
		Assert.isTrue(LoginService.getPrincipal().containsAuthority("AGENT"));
	
		
		result = new ModelAndView("sponsorship/agent/addDescription");
		result.addObject("languages",languages);
		result.addObject("sponsorshipId",sponsorshipId);
		result.addObject("description",description);
		result.addObject("requestURI", "sponsorship/agent/addDescription.do");
		} catch(Throwable oops){
			
			if(lanExc.getAgent()!=agentService.findByPrincipal()){
			
				message= "error.AddDescription";
			}else{
				message="error";
			}
			result = createListModelAndView(message);
			
		}
		
		
		
		return result;
	}

	@RequestMapping(value="/agent/addDescription", method= RequestMethod.POST,params="save")
	public ModelAndView descriptionSave(@RequestParam int sponsorshipId, @Valid Description description,  BindingResult binding){
		ModelAndView result;
		Collection<Language> languages = languageService.findAll();
		Sponsorship lanExc2 =sponsorshipService.findOne(sponsorshipId);
		//Collection<Language> languagesOwn =new ArrayList<Language>();
		boolean languageNoExist =true;
		for(Description desAux: lanExc2.getDescriptions()){
			if(description.getLanguage()==desAux.getLanguage()){
				languageNoExist =false;
			}
		}
		
		if (binding.hasErrors()) {
			result = new ModelAndView();
			result.addObject("languages",languages);
			result.addObject("sponsorshipId",sponsorshipId);
			result.addObject("description",description);
			BindingResult bR = binding;
			System.out.println(bR);
		} else {
			try {
				
				Assert.isTrue(LoginService.getPrincipal().containsAuthority("AGENT"));
				Assert.notNull(languages);
				Assert.isTrue(languageNoExist);
				Assert.isTrue(lanExc2.getAgent()==agentService.findByPrincipal(), "You don´t have permission");
				Sponsorship langExch = sponsorshipService.findOne(sponsorshipId);
				langExch.getDescriptions().add(description);
				sponsorshipService.saveModified(langExch);

			
				result = new ModelAndView("redirect:/sponsorship/agent/list.do");
				
			} catch (Throwable oops) {
					result = new ModelAndView();
					result.addObject("languages",languages);
					result.addObject("sponsorshipId",sponsorshipId);
					result.addObject("description",description);
					
				if(lanExc2.getAgent()!=agentService.findByPrincipal()){
						
					result.addObject("message", "error.AddDescription.Sponsorship");
				}else if(languageNoExist==false){
					result.addObject("message", "error.LanguageExist");
				}	else{
					result.addObject("message", "error");
				}	
			}
		}
		return result;
		
	}
	//inicio de añadir banner
	

	@RequestMapping(value= "/agent/addBanner", method= RequestMethod.GET)
	public ModelAndView createBanner(@RequestParam int sponsorshipId) {
		ModelAndView result;
		Sponsorship lanExc;
		Collection<Language> languages = languageService.findAll();
		Banner banner = new Banner();
		String message;
		lanExc =sponsorshipService.findOne(sponsorshipId);
		
		try{
		Assert.notNull(languages);
		Assert.isTrue(lanExc.getAgent()==agentService.findByPrincipal(), "You don´t have access to this place");
		Assert.isTrue(LoginService.getPrincipal().containsAuthority("AGENT"));
	
		
		result = new ModelAndView("sponsorship/agent/addBanner");
		result.addObject("languages",languages);
		result.addObject("sponsorshipId",sponsorshipId);
		result.addObject("banner",banner);
		result.addObject("requestURI", "sponsorship/agent/addBanner.do");
		} catch(Throwable oops){
			
			if(lanExc.getAgent()!=agentService.findByPrincipal()){
			
				message= "error.AddBanner";
			}else{
				message="error";
			}
			result = createListModelAndView(message);
			
		}
		
		
		
		return result;
	}

	@RequestMapping(value="/agent/addBanner", method= RequestMethod.POST,params="save")
	public ModelAndView descriptionBannerSave(@RequestParam int sponsorshipId, @Valid Banner banner,  BindingResult binding){
		ModelAndView result;
		Collection<Language> languages = languageService.findAll();
		Sponsorship lanExc2 =sponsorshipService.findOne(sponsorshipId);
		//Collection<Language> languagesOwn =new ArrayList<Language>();
		boolean languageNoExist =true;
		for(Banner desAux: lanExc2.getBanners()){
			if(banner.getLanguage()==desAux.getLanguage()){
				languageNoExist =false;
			}
		}
		
		if (binding.hasErrors()) {
			result = new ModelAndView();
			result.addObject("languages",languages);
			result.addObject("sponsorshipId",sponsorshipId);
			result.addObject("banner",banner);
			BindingResult bR = binding;
			System.out.println(bR);
		} else {
			try {
				
				Assert.isTrue(LoginService.getPrincipal().containsAuthority("AGENT"));
				Assert.notNull(languages);
				Assert.isTrue(languageNoExist);
				Assert.isTrue(lanExc2.getAgent()==agentService.findByPrincipal(), "You don´t have permission");
				Sponsorship langExch = sponsorshipService.findOne(sponsorshipId);
				langExch.getBanners().add(banner);
				sponsorshipService.saveModified(langExch);

			
				result = new ModelAndView("redirect:/sponsorship/agent/list.do");
				
			} catch (Throwable oops) {
					result = new ModelAndView();
					result.addObject("languages",languages);
					result.addObject("sponsorshipId",sponsorshipId);
					result.addObject("banner",banner);
					
				if(lanExc2.getAgent()!=agentService.findByPrincipal()){
						
					result.addObject("message", "error.AddDescription");
				}else if(languageNoExist==false){
					result.addObject("message", "error.LanguageExist");
				}	else{
					result.addObject("message", "error");
				}	
			}
		}
		return result;
		
	}
	
	
	//fin de añadir banner
	@RequestMapping(value="/banner/agent/electionLanguage", method= RequestMethod.GET)
	public ModelAndView SelectBannerLanguage(@RequestParam int sponsorshipId){
		ModelAndView result;
		
		Sponsorship lExc = sponsorshipService.findOne(sponsorshipId);
		
		result=createModelAndViewBanner(lExc);
		

		return result;
	}
	
	protected ModelAndView createModelAndViewBanner(Sponsorship lexc){
		ModelAndView result;
	
		result = createModelAndViewBanner(lexc,null);
		
		
		
		return result;
	}
	
	protected ModelAndView createModelAndViewBanner(Sponsorship lexc, String message){
		ModelAndView result;
	
		String requestURI="sponsorship/banner/agent/electionLanguage.do";
		
		result = new ModelAndView("sponsorship/banner/agent/electionLanguage");
		
		Collection<Language> languages = languageService.findAll();
		
		Banner des = bannerService.create();
		
		Assert.notNull(languages);
		
		result.addObject("banner",des);
		
		result.addObject("languages",languages);
		
		result.addObject("sponsorship",lexc);
		
		result.addObject("message",message);

		result.addObject("requestURI",requestURI);
		
		return result;
	}
	
	@RequestMapping(value="/description/agent/electionLanguage", method= RequestMethod.GET)
	public ModelAndView SelectLanguage(@RequestParam int sponsorshipId){
		ModelAndView result;
		
		Sponsorship lExc = sponsorshipService.findOne(sponsorshipId);
		
		result=createEditModelAndViewNoForm(lExc);
		

		return result;
	}
	
	
	protected ModelAndView createEditModelAndView(SponsorshipForm lform){
		ModelAndView result;
	
		result = createEditModelAndView(lform,null);
		
		
		
		return result;
	}
	
	protected ModelAndView createEditModelAndView(SponsorshipEditForm lform){
		ModelAndView result;
	
		result = createEditModelAndView(lform,null);
		
		
		
		return result;
	}
	
	
	
	
	protected ModelAndView createEditModelAndView(Description lform){
		ModelAndView result;
	
		result = createEditModelAndView(lform,null);
		
		
		
		return result;
	}

	protected ModelAndView createEditModelAndViewNoForm(Sponsorship lexc){
		ModelAndView result;
	
		result = createEditModelAndViewNoForm(lexc,null);
		
		
		
		return result;
	}
	
	
	protected ModelAndView createEditModelAndView(SponsorshipForm lform, String message){
		ModelAndView result;
	
		String requestURI="sponsorship/agent/register.do";
		
		result = new ModelAndView("sponsorship/agent/register");
		
		Collection<Language> languages = languageService.findAll();
		
		Collection<LanguageExchange> languageExchanges = languageExchangeService.findAll();
		
		Assert.notNull(languages);
		
		result.addObject("languageExchanges",languageExchanges);
		
		result.addObject("languages",languages);
		
		result.addObject("sponsorshipForm",lform);
		
		result.addObject("message",message);

		result.addObject("requestURI",requestURI);
		
		return result;
	}
	
	protected ModelAndView createEditModelAndView(SponsorshipEditForm lform, String message){
		ModelAndView result;
	
		String requestURI="sponsorship/agent/edit.do";
		
		result = new ModelAndView("sponsorship/agent/edit");
		
		Collection<Language> languages = languageService.findAll();
		
		Collection<LanguageExchange> languageExchanges = languageExchangeService.findAll();
		
		Assert.notNull(languages);
		
		result.addObject("languageExchanges",languageExchanges);
		
		result.addObject("languages",languages);
		
		result.addObject("sponsorshipEditForm",lform);
		
		result.addObject("message",message);

		result.addObject("requestURI",requestURI);
		
		return result;
	}
	
	
	
	protected ModelAndView createEditModelAndView(Description des, String message){
		ModelAndView result;
	
		String requestURI="sponsorship/agent/addDescription.do";
		
		result = new ModelAndView("sponsorship/agent/addDescription");
		
		Collection<Language> languages = languageService.findAll();
		
		Assert.notNull(languages);
		
		result.addObject("languages",languages);
		
		result.addObject("description",des);
		
		result.addObject("message",message);

		result.addObject("requestURI",requestURI);
		
		return result;
	}
	
	protected ModelAndView createEditModelAndViewNoForm(Sponsorship lexc, String message){
		ModelAndView result;
	
		String requestURI="sponsorship/description/agent/electionLanguage.do";
		
		result = new ModelAndView("sponsorship/description/agent/electionLanguage");
		
		Collection<Language> languages = languageService.findAll();
		
		Description des = descriptionService.create();
		
		Assert.notNull(languages);
		
		result.addObject("description",des);
		
		result.addObject("languages",languages);
		
		result.addObject("sponsorship",lexc);
		
		result.addObject("message",message);

		result.addObject("requestURI",requestURI);
		
		return result;
	}
	
	
	@RequestMapping("/agent/list")
	public ModelAndView listAllSponsorship() {
		ModelAndView result;
		Agent c = agentService.findByPrincipal();
		
		
		
		Collection<Sponsorship> sponsorships;
		sponsorships = sponsorshipService.sponsorshipOfAgentLogin();
		
		String requestURI="sponsorship/agent/list.do";
		
		boolean variable = false;
		boolean isMine = false;
	
		
		result = new ModelAndView("sponsorship/agent/list");
		result.addObject("isMine",isMine);
		result.addObject("agentprincipal",c);
		result.addObject("sponsorships",sponsorships);
		result.addObject("variable", variable);
		result.addObject("requestURI",requestURI);
		
		
		
		return result;
	}
	
	
protected ModelAndView createListModelAndView(String message){
	ModelAndView result;
	Agent c = agentService.findByPrincipal();
	
	Collection<Sponsorship> sponsorships;
	sponsorships = sponsorshipService.sponsorshipOfAgentLogin();
	
	String requestURI="sponsorship/agent/list.do";
	
	boolean variable = false;
	
	result = new ModelAndView("sponsorship/agent/list");
	result.addObject("message",message);
	result.addObject("sponsorships",sponsorships);
	result.addObject("agentprincipal",c);
	result.addObject("variable", variable);
	result.addObject("requestURI",requestURI);
		
		return result;
	}

	
	

	
	//eliminar sponsorship
	
	@RequestMapping("/agent/delete")
	public ModelAndView delete(@RequestParam int sponsorshipId) {
		ModelAndView result;
		String message;
		Sponsorship lanExc2 =sponsorshipService.findOne(sponsorshipId);
		
		try {
			Assert.isTrue(lanExc2.getAgent()==agentService.findByPrincipal(), "You don´t have permission");
			Sponsorship sponsorship;
			sponsorship = sponsorshipService.findOne(sponsorshipId);
			sponsorshipService.delete(sponsorship);
			result = new ModelAndView("redirect:list.do?");
		} catch (Throwable oops) {
			if(lanExc2.getAgent()!=agentService.findByPrincipal()){
			message= "error.Sponsorship.delete";
			}else{
				message="error";
			}
			result = createListModelAndView(message);
			
			
			}	
		return result;
	}
	
}

