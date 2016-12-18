package controllers.polyglot;


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


import domain.Language;
import domain.LanguageExchange;
import domain.Polyglot;


import forms.LanguageExchangeEditForm;
import forms.LanguageExchangeForm;




import security.LoginService;
import services.DescriptionService;
import services.LanguageExchangeService;
import services.LanguageService;
import services.PolyglotService;
import services.SearchService;


@Controller
@RequestMapping("/languageExchange")
public class LanguageExchangePolyglotController extends AbstractController{
	
	// Managed service ----------------------------------------------
	

	
	@Autowired
	private LanguageExchangeService languageExchangeService;

	@Autowired
	private LanguageService languageService;
	
	@Autowired
	private DescriptionService descriptionService;

	@Autowired
	private PolyglotService polyglotService;
	
	@Autowired
	private SearchService searchService;

	
	// Constructors -------------------------------------------------
	//Register -------------------------------------------------------
	@RequestMapping(value="/polyglot/register", method= RequestMethod.GET)
	public ModelAndView create(){
		ModelAndView result;
		
		LanguageExchangeForm lform = new LanguageExchangeForm();
		
		result=createEditModelAndView(lform);
		

		return result;
	}
	
	@RequestMapping(value="/polyglot/register", method= RequestMethod.POST,params="save")
	public ModelAndView save(@Valid LanguageExchangeForm lform,  BindingResult binding){
		ModelAndView result;
		LanguageExchange lexc;
		if (binding.hasErrors()) {
			result=createEditModelAndView(lform);
		} else {
			try {
				
				Assert.isTrue(LoginService.getPrincipal().containsAuthority("POLYGLOT"));
					lexc= languageExchangeService.reconstruct(lform);
					
					languageExchangeService.save(lexc);

					

			
				result = new ModelAndView("redirect:/languageExchange/polyglot/list.do");
				
			} catch (Throwable oops) {
				System.out.println(oops);
				result=createEditModelAndView(lform,"lform.commit.error");

			}
		}
		return result;
		
	}
	
	@RequestMapping(value = "/polyglot/search", method = RequestMethod.GET)
	public ModelAndView search(@RequestParam String entrante,@RequestParam int languageId) {
		ModelAndView result;
		
		Collection<LanguageExchange> languageExchanges;
		Collection<LanguageExchange> languageExchangesAll = languageExchangeService.findAll();
		Collection<Language> languages = languageService.findAll();
		Collection<Description> descriptions;
	
		descriptions =languageExchangeService.languageDescriptionExchangeForKeyword(languageId, entrante);
		String requestURI="languageExchange/polyglot/list.do";
		Polyglot c = polyglotService.findByPrincipal();
		if(!(entrante.isEmpty())){
			searchService.createWithEntrante(entrante);
		}
		
		boolean variable = false;
		boolean isMine = false;
		
		languageExchanges = languageExchangeService.languageExchangeForKeyword(entrante);
		
		for(Description desAux: descriptions){
			for(LanguageExchange langExchAux: languageExchangesAll){
				for(Description desLanguageExchange: langExchAux.getDescriptions()){
					if(desAux.getId()==desLanguageExchange.getId()){
						languageExchanges.add(langExchAux);
					}
					
				}
				
				
			}
			
		}
		Description description = descriptionService.create();
		LanguageExchange languageExchange = languageExchangeService.create();
		
		result = new ModelAndView("languageExchange/polyglot/list");
		result.addObject("description", description);
		result.addObject("languageExchange", languageExchange);
		result.addObject("languageExchanges", languageExchanges);
		result.addObject("languages", languages);
		result.addObject("requestURI", "languageExchange/polyglot/list.do");
		result.addObject("languageExchanges",languageExchanges);
		result.addObject("polyglotprincipal",c);
		result.addObject("variable", variable);
		result.addObject("isMine", isMine);
		result.addObject("requestURI",requestURI);
		
		
		
		
		
		return result;
	}
	
	//editar languageExchange
	
	@RequestMapping(value = "/polyglot/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam int languageExchangeId) {
		ModelAndView result;
		LanguageExchange lanExc;
		LanguageExchangeEditForm lform;
		String message;
		
		lanExc =languageExchangeService.findOne(languageExchangeId);
		try{
			
		
		Assert.isTrue(lanExc.getPolyglotOrganise()==polyglotService.findByPrincipal(), "You don´t have access to this place");
	
		
		Assert.isTrue(LoginService.getPrincipal().containsAuthority("POLYGLOT"));
		
		
		lanExc =languageExchangeService.findOne(languageExchangeId);
		
		
		lform = languageExchangeService.constructForm(lanExc);
		
		result=createEditModelAndView(lform);
		} catch(Throwable oops){
			
			if(lanExc.getPolyglotOrganise()!=polyglotService.findByPrincipal()){
			
				message= "error.usuario.creation";
			}else{
				message="error";
			}
			result = createListModelAndView(message);
			
		}
		
		return result;
	}
	
	
	
	@RequestMapping(value = "/polyglot/edit", method = RequestMethod.POST, params="save")
	public ModelAndView edit(@RequestParam int languageExchangeId, @Valid LanguageExchangeEditForm languageExchangeEditForm, BindingResult binding) {
		ModelAndView result;
	
		LanguageExchange lanExc2 =languageExchangeService.findOne(languageExchangeId);
		
		Collection<Language> languages = languageService.findAll();
		
		Assert.notNull(languages);


	
		
		
		if (binding.hasErrors()) {
			result = new ModelAndView();
			result.addObject("languages",languages);
			result.addObject("languageExchangeEditForm", languageExchangeEditForm);
			BindingResult bR = binding;
			System.out.println(bR);
		} else {
			try {
				
				Assert.isTrue(LoginService.getPrincipal().containsAuthority("POLYGLOT"));
				Assert.isTrue(lanExc2.getPolyglotOrganise()==polyglotService.findByPrincipal(), "You don´t have permission");
				LanguageExchange lanExc = languageExchangeService.reconstructEdit(languageExchangeEditForm, languageExchangeId);
				
				languageExchangeService.saveModified(lanExc);
				
				result = new ModelAndView("redirect:/languageExchange/polyglot/list.do");
				
			} catch (Throwable oops) {
				result=createEditModelAndView(languageExchangeEditForm);
				
					if(!(lanExc2.getPolyglotOrganise()==polyglotService.findByPrincipal())){
						
						result.addObject("message", "error.usuario.creation");
					}else{
						result.addObject("message", "error");
					}	
				

			}
		}
		return result;
		
	}
	

	@RequestMapping(value= "/polyglot/addDescription", method= RequestMethod.GET)
	public ModelAndView createDescription(@RequestParam int languageExchangeId) {
		ModelAndView result;
		LanguageExchange lanExc;
		Collection<Language> languages = languageService.findAll();
		Description description = new Description();
		String message;
		lanExc =languageExchangeService.findOne(languageExchangeId);
		
		try{
		Assert.notNull(languages);
		Assert.isTrue(lanExc.getPolyglotOrganise()==polyglotService.findByPrincipal(), "You don´t have access to this place");
		Assert.isTrue(LoginService.getPrincipal().containsAuthority("POLYGLOT"));
	
		
		result = new ModelAndView("languageExchange/polyglot/addDescription");
		result.addObject("languages",languages);
		result.addObject("languageExchangeId",languageExchangeId);
		result.addObject("description",description);
		result.addObject("requestURI", "languageExchange/polyglot/addDescription.do");
		} catch(Throwable oops){
			
			if(lanExc.getPolyglotOrganise()!=polyglotService.findByPrincipal()){
			
				message= "error.AddDescription";
			}else{
				message="error";
			}
			result = createListModelAndView(message);
			
		}
		
		
		
		return result;
	}

	@RequestMapping(value="/polyglot/addDescription", method= RequestMethod.POST,params="save")
	public ModelAndView descriptionSave(@RequestParam int languageExchangeId, @Valid Description description,  BindingResult binding){
		ModelAndView result;
		Collection<Language> languages = languageService.findAll();
		LanguageExchange lanExc2 =languageExchangeService.findOne(languageExchangeId);
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
			result.addObject("languageExchangeId",languageExchangeId);
			result.addObject("description",description);
			BindingResult bR = binding;
			System.out.println(bR);
		} else {
			try {
				
				Assert.isTrue(LoginService.getPrincipal().containsAuthority("POLYGLOT"));
				Assert.notNull(languages);
				Assert.isTrue(languageNoExist);
				Assert.isTrue(lanExc2.getPolyglotOrganise()==polyglotService.findByPrincipal(), "You don´t have permission");
				LanguageExchange langExch = languageExchangeService.findOne(languageExchangeId);
				langExch.getDescriptions().add(description);
				languageExchangeService.saveModified(langExch);

			
				result = new ModelAndView("redirect:/languageExchange/polyglot/list.do");
				
			} catch (Throwable oops) {
					result = new ModelAndView();
					result.addObject("languages",languages);
					result.addObject("languageExchangeId",languageExchangeId);
					result.addObject("description",description);
					
				if(lanExc2.getPolyglotOrganise()!=polyglotService.findByPrincipal()){
						
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
	
	
	
	@RequestMapping(value="/polyglot/electionLanguage", method= RequestMethod.GET)
	public ModelAndView SelectLanguage(@RequestParam int languageExchangeId){
		ModelAndView result;
		
		LanguageExchange lExc = languageExchangeService.findOne(languageExchangeId);
		
		result=createEditModelAndViewNoForm(lExc);
		

		return result;
	}
	
	
	protected ModelAndView createEditModelAndView(LanguageExchangeForm lform){
		ModelAndView result;
	
		result = createEditModelAndView(lform,null);
		
		
		
		return result;
	}
	
	protected ModelAndView createEditModelAndView(LanguageExchangeEditForm lform){
		ModelAndView result;
	
		result = createEditModelAndView(lform,null);
		
		
		
		return result;
	}
	
	
	protected ModelAndView createEditModelAndView(Description lform){
		ModelAndView result;
	
		result = createEditModelAndView(lform,null);
		
		
		
		return result;
	}

	protected ModelAndView createEditModelAndViewNoForm(LanguageExchange lexc){
		ModelAndView result;
	
		result = createEditModelAndViewNoForm(lexc,null);
		
		
		
		return result;
	}
	
	
	protected ModelAndView createEditModelAndView(LanguageExchangeForm lform, String message){
		ModelAndView result;
	
		String requestURI="languageExchange/polyglot/register.do";
		
		result = new ModelAndView("languageExchange/polyglot/register");
		
		Collection<Language> languages = languageService.findAll();
		
		Assert.notNull(languages);
		
		result.addObject("languages",languages);
		
		result.addObject("languageExchangeForm",lform);
		
		result.addObject("message",message);

		result.addObject("requestURI",requestURI);
		
		return result;
	}
	
	
	
	protected ModelAndView createEditModelAndView(LanguageExchangeEditForm lform, String message){
		ModelAndView result;
	
		String requestURI="languageExchange/polyglot/edit.do";
		
		result = new ModelAndView("languageExchange/polyglot/edit");
		
		Collection<Language> languages = languageService.findAll();
		
		Assert.notNull(languages);
		
		result.addObject("languages",languages);
		
		result.addObject("languageExchangeEditForm",lform);
		
		result.addObject("message",message);

		result.addObject("requestURI",requestURI);
		
		return result;
	}
	
	protected ModelAndView createEditModelAndView(Description des, String message){
		ModelAndView result;
	
		String requestURI="languageExchange/polyglot/addDescription.do";
		
		result = new ModelAndView("languageExchange/polyglot/addDescription");
		
		Collection<Language> languages = languageService.findAll();
		
		Assert.notNull(languages);
		
		result.addObject("languages",languages);
		
		result.addObject("description",des);
		
		result.addObject("message",message);

		result.addObject("requestURI",requestURI);
		
		return result;
	}
	
	protected ModelAndView createEditModelAndViewNoForm(LanguageExchange lexc, String message){
		ModelAndView result;
	
		String requestURI="languageExchange/electionLanguage.do";
		
		result = new ModelAndView("languageExchange/electionLanguage");
		
		Collection<Language> languages = languageService.findAll();
		
		Description des = descriptionService.create();
		
		Assert.notNull(languages);
		
		result.addObject("description",des);
		
		result.addObject("languages",languages);
		
		result.addObject("languageExchange",lexc);
		
		result.addObject("message",message);

		result.addObject("requestURI",requestURI);
		
		return result;
	}
	
	//listar por querys
	
	@RequestMapping("/polyglot/future3months/list")
	public ModelAndView future3months() {
		ModelAndView result;
		
		Polyglot c = polyglotService.findByPrincipal();
		
		Collection<LanguageExchange> languageExchanges;
		languageExchanges = languageExchangeService.listExchangeFutureOrganised3Months();
		
		String requestURI="languageExchange/future3months/list.do";
		
		boolean variable = false;
		boolean isMine = false;
		

		Description des = descriptionService.create();
				
		Collection<Language> languages;
		languages = languageService.findAll();
				
		LanguageExchange languageExchange=languageExchangeService.create();
		
		result = new ModelAndView("languageExchange/polyglot/future3months/list");		
		result.addObject("languageExchanges",languageExchanges);
		result.addObject("polyglotprincipal",c);
		result.addObject("variable", variable);
		result.addObject("isMine", isMine);
		result.addObject("requestURI",requestURI);
		result.addObject("description",des);
		result.addObject("languageExchange", languageExchange);
		result.addObject("languages",languages);
		return result;
	}
	
	@RequestMapping("/polyglot/past3months/list")
	public ModelAndView past3months() {
		ModelAndView result;
		
		Polyglot c = polyglotService.findByPrincipal();
		
		Collection<LanguageExchange> languageExchanges;
		languageExchanges = languageExchangeService.listExchangePastOrganised3Months();
		
			
		String requestURI="languageExchange/polyglot/past3months/list.do";
		
		boolean variable = false;
		boolean isMine = false;
		
		Description des = descriptionService.create();
		
		
		Collection<Language> languages;
		languages = languageService.findAll();
		
		
		LanguageExchange languageExchange=languageExchangeService.create();
		
		result = new ModelAndView("languageExchange/polyglot/past3months/list");
		result.addObject("languageExchanges",languageExchanges);
		result.addObject("polyglotprincipal",c);
		result.addObject("variable", variable);
		result.addObject("isMine", isMine);
		result.addObject("requestURI",requestURI);
		result.addObject("description",des);
		result.addObject("languageExchange", languageExchange);
		result.addObject("languages",languages);
		
		return result;
	}
	
	@RequestMapping("/polyglot/list")
	public ModelAndView listAllLanguageExchange() {
		ModelAndView result;
		Polyglot c = polyglotService.findByPrincipal();
		
		Description des = descriptionService.create();
		
		Collection<LanguageExchange> languageExchanges;
		languageExchanges = languageExchangeService.findAllNoCancel();
		
		Collection<Language> languages;
		languages = languageService.findAll();
		
		String requestURI="languageExchange/polyglot/list.do";
		
		boolean variable = false;
		boolean isMine = false;
		LanguageExchange languageExchange=languageExchangeService.create();
		
		result = new ModelAndView("languageExchange/polyglot/list");
		result.addObject("description",des);
		result.addObject("isMine",isMine);
		result.addObject("polyglotprincipal",c);
		result.addObject("languageExchanges",languageExchanges);
		result.addObject("languageExchange", languageExchange);
		result.addObject("languages",languages);
		result.addObject("variable", variable);
		result.addObject("requestURI",requestURI);
		
		
		
		return result;
	}
	
	
protected ModelAndView createListModelAndView(String message){
	ModelAndView result;
	Polyglot c = polyglotService.findByPrincipal();
	
	Description des = descriptionService.create();
	
	Collection<LanguageExchange> languageExchanges;
	languageExchanges = languageExchangeService.findAllNoCancel();
	
	Collection<Language> languages;
	languages = languageService.findAll();
	
	String requestURI="languageExchange/polyglot/list.do";
	
	boolean variable = false;
	boolean isMine = false;
	LanguageExchange languageExchange=languageExchangeService.create();
	
	result = new ModelAndView("languageExchange/polyglot/list");
	result.addObject("message",message);
	result.addObject("description",des);
	result.addObject("isMine",isMine);
	result.addObject("polyglotprincipal",c);
	result.addObject("languageExchanges",languageExchanges);
	result.addObject("languageExchange", languageExchange);
	result.addObject("languages",languages);
	result.addObject("variable", variable);
	result.addObject("requestURI",requestURI);
	
	
		
		return result;
	}

	
	

	
	
	
	
	@RequestMapping(value= "/polyglot/join", method = RequestMethod.GET)
	public ModelAndView joinToLanguageExchange(@RequestParam int languageExchangeId) {
		ModelAndView result;
		LanguageExchange languageExc = languageExchangeService.findOne(languageExchangeId);
		languageExchangeService.JoinToLanguageExchange(languageExc);
		result = new ModelAndView("redirect:list.do");		

		return result;
	}
	
	@RequestMapping(value= "/polyglot/unjoin", method = RequestMethod.GET)
	public ModelAndView unjoinToLanguageExchange(@RequestParam int languageExchangeId) {
		ModelAndView result;
		
		LanguageExchange languageExc = languageExchangeService.findOne(languageExchangeId);
		languageExchangeService.UnJoinToLanguageExchange(languageExc);
		result = new ModelAndView("redirect:list.do");
	
		

		return result;
	}
	
	@RequestMapping("/polyglot/joined/list")
	public ModelAndView joinedList() {
		ModelAndView result;
		
		Polyglot c = polyglotService.findByPrincipal();

		Description des = descriptionService.create();
		
		
		Collection<Language> languages;
		languages = languageService.findAll();
		
		
		Collection<LanguageExchange> languageExchanges;
		languageExchanges = languageExchangeService.languageExchangeOfPolyglotLogin();
		LanguageExchange languageExchange=languageExchangeService.create();
			
		String requestURI="languageExchange/polyglot/joined/list.do";
		
		boolean variable = false;
		boolean isMine = false;
		
		result = new ModelAndView("languageExchange/polyglot/joined/list");
		result.addObject("languageExchanges",languageExchanges);
		result.addObject("polyglotprincipal",c);
		result.addObject("variable", variable);
		result.addObject("isMine", isMine);
		result.addObject("requestURI",requestURI);


		
		result.addObject("description",des);
		result.addObject("languageExchange", languageExchange);
		result.addObject("languages",languages);

		
		
		return result;
	}
	
	//eliminar languageExchange
	
	@RequestMapping("/polyglot/cancel")
	public ModelAndView delete(@RequestParam int languageExchangeId) {
		ModelAndView result;
		String message;
		LanguageExchange lanExc2 =languageExchangeService.findOne(languageExchangeId);
		
		try {
			Assert.isTrue(lanExc2.getPolyglotOrganise()==polyglotService.findByPrincipal(), "You don´t have permission");
			LanguageExchange languageExchange;
			languageExchange = languageExchangeService.findOne(languageExchangeId);
			languageExchangeService.delete(languageExchange);
			result = new ModelAndView("redirect:list.do?");
		} catch (Throwable oops) {
			if(lanExc2.getPolyglotOrganise()!=polyglotService.findByPrincipal()){
			message= "error.LanguageExchange.delete";
			}else{
				message="error";
			}
			result = createListModelAndView(message);
			
			
			}	
		return result;
	}
	
}

