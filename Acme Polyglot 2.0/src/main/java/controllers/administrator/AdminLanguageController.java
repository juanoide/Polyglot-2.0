package controllers.administrator;


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





import forms.LanguageEditForm;
import forms.LanguageForm;







import security.LoginService;

import services.DescriptionService;
import services.LanguageService;



@Controller
@RequestMapping("/language")
public class AdminLanguageController extends AbstractController{
	
	// Managed service ----------------------------------------------
	

	
	@Autowired
	private LanguageService languageService;

	@Autowired
	private DescriptionService descriptionService;



	
	// Constructors -------------------------------------------------
	//Register -------------------------------------------------------
	
	@RequestMapping(value="/admin/electionLanguage", method= RequestMethod.GET)
	public ModelAndView SelectLanguage(@RequestParam int languageId){
		ModelAndView result;
		
		Language lExc = languageService.findOne(languageId);
		
		result=createEditModelAndViewNoForm(lExc);
		

		return result;
	}
	
	protected ModelAndView createEditModelAndViewNoForm(Language lexc){
		ModelAndView result;
	
		result = createEditModelAndViewNoForm(lexc,null);
		
		
		
		return result;
	}
	
	protected ModelAndView createEditModelAndViewNoForm(Language lexc, String message){
		ModelAndView result;
	
		String requestURI="language/admin/electionLanguage.do";
		
		result = new ModelAndView("language/admin/electionLanguage");
		
		Collection<Language> languages = languageService.findAll();
		
		Description des = descriptionService.create();
		
		Assert.notNull(languages);
		
		result.addObject("description",des);
		
		result.addObject("languages",languages);
		
		result.addObject("language",lexc);
		
		result.addObject("message",message);

		result.addObject("requestURI",requestURI);
		
		return result;
	}
	
	
	
	@RequestMapping(value="/admin/register", method= RequestMethod.GET)
	public ModelAndView create(){
		ModelAndView result;
		
		LanguageForm lform = new LanguageForm();
		
		result=createEditModelAndView(lform);
		

		return result;
	}
	
	@RequestMapping(value="/admin/register", method= RequestMethod.POST,params="save")
	public ModelAndView save(@Valid LanguageForm lform,  BindingResult binding){
		ModelAndView result;
		Language lexc;
		if (binding.hasErrors()) {
			result=createEditModelAndView(lform);
		} else {
			try {
				Assert.isTrue(languageService.NoExist(lform));
				Assert.isTrue(LoginService.getPrincipal().containsAuthority("ADMIN"));
					lexc= languageService.reconstruct(lform);
					
					languageService.save(lexc);

					

			
				result = new ModelAndView("redirect:/language/admin/list.do");
				
			} catch (Throwable oops) {
				
				if(languageService.NoExist(lform)==false){
					
					result=createEditModelAndView(lform,"error.LanguageExist");
				}else{
					result=createEditModelAndView(lform,"lform.commit.error");
				}
				
				

			}
		}
		return result;
		
	}
	
	protected ModelAndView createEditModelAndView(LanguageForm lform){
		ModelAndView result;
	
		result = createEditModelAndView(lform,null);
		
		
		
		return result;
	}
	protected ModelAndView createEditModelAndView(LanguageForm lform, String message){
		ModelAndView result;
	
		String requestURI="language/admin/register.do";
		
		result = new ModelAndView("language/admin/register");
		
		Collection<Language> languages = languageService.findAll();
		
		Assert.notNull(languages);
		
		result.addObject("languages",languages);
		
		result.addObject("languageForm",lform);
		
		result.addObject("message",message);

		result.addObject("requestURI",requestURI);
		
		return result;
	}
	
	//List -------------------------------------------------------

	@RequestMapping("/admin/list")
	public ModelAndView listAllLanguage() {
		ModelAndView result;
		
		
		
		
		Collection<Language> languages;
		languages = languageService.findAll();
		
		String requestURI="language/admin/list.do";
		
		boolean variable = false;
	
	
		
		result = new ModelAndView("language/admin/list");


		result.addObject("languages",languages);
		result.addObject("variable", variable);
		result.addObject("requestURI",requestURI);
		
		
		
		return result;
	}
	
	//editar language
	

	@RequestMapping(value = "/admin/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam int languageId) {
		ModelAndView result;
		Language lanExc;
		LanguageEditForm lform;
		String message;
		
		lanExc =languageService.findOne(languageId);
		try{
			
		
		
		Assert.isTrue(LoginService.getPrincipal().containsAuthority("ADMIN"));
		
		
		lanExc =languageService.findOne(languageId);
		
		
		lform = languageService.constructForm(lanExc);
		
		result=createEditModelAndView(lform);
		} catch(Throwable oops){
			
			if(LoginService.getPrincipal().containsAuthority("ADMIN")){
			
				message= "error.LanguageNoEdit";
			}else{
				message="error";
			}
			result = createListModelAndView(message);
			
		}
		
		return result;
	}
	
	
	
	@RequestMapping(value = "/admin/edit", method = RequestMethod.POST, params="save")
	public ModelAndView edit(@RequestParam int languageId, @Valid LanguageEditForm languageEditForm, BindingResult binding) {
		ModelAndView result;
		
	

	
		
		
		if (binding.hasErrors()) {
			result = new ModelAndView();
			result.addObject("languageEditForm", languageEditForm);
			BindingResult bR = binding;
			System.out.println(bR);
		} else {
			try {
				
				Assert.isTrue(LoginService.getPrincipal().containsAuthority("ADMIN"));
				Language lanExc = languageService.reconstructEdit(languageEditForm, languageId);
				languageService.saveModified(lanExc);
				
				result = new ModelAndView("redirect:/language/admin/list.do");
				
			} catch (Throwable oops) {
				result=createEditModelAndView(languageEditForm);
				
					if(!(LoginService.getPrincipal().containsAuthority("ADMIN"))){
						
						result.addObject("message", "error.LanguageNoEdit");
						
						
					}else if(languageService.NoExist(languageEditForm)==false){
						
						result.addObject("message","error.LanguageExist");
			
					
						
					}else{
						result.addObject("message", "error");
					}	
				

			}
		}
		return result;
		
	}
	

	

	@RequestMapping("/admin/delete")
	public ModelAndView delete(@RequestParam int languageId) {
		ModelAndView result;
		String message;
		Language lanExc2 =languageService.findOne(languageId);
		
		try {
			Assert.isTrue(LoginService.getPrincipal().containsAuthority("ADMIN"));
			Assert.isTrue(lanExc2.getExpectedTalks().size()==0);
			Assert.isTrue(lanExc2.getDescriptions().size()==0);
			Language language;
			language = languageService.findOne(languageId);
			languageService.delete(language);
			result = new ModelAndView("redirect:list.do?");
		} catch (Throwable oops) {
			if(!(lanExc2.getExpectedTalks().size()==0)||(!(lanExc2.getDescriptions().size()==0))||(!(lanExc2.getMessages().size()==0))){
			message= "error.LanguageUse";
			}else{
				message="error";
			}
			result = createListModelAndView(message);
			
			
			}	
		return result;
	}
	
	protected ModelAndView createListModelAndView(String message){
		ModelAndView result;
	
		
		Collection<Language> languages;
		languages = languageService.findAll();
		
		String requestURI="language/admin/list.do";
		
		boolean variable = false;
		
		result = new ModelAndView("language/admin/list");
		result.addObject("message",message);
		result.addObject("languages",languages);
		result.addObject("variable", variable);
		result.addObject("requestURI",requestURI);
			
			return result;
		}

	protected ModelAndView createEditModelAndView(LanguageEditForm lform){
		ModelAndView result;
	
		result = createEditModelAndView(lform,null);
		
		
		
		return result;
	}
	
	protected ModelAndView createEditModelAndView(LanguageEditForm lform, String message){
		ModelAndView result;
	
		String requestURI="language/admin/edit.do";
		
		result = new ModelAndView("language/admin/edit");
		
		
		
	
		
		result.addObject("languageEditForm",lform);
		
		result.addObject("message",message);

		result.addObject("requestURI",requestURI);
		
		return result;
	}
	
	//add description
	
	

	@RequestMapping(value= "/admin/addDescription", method= RequestMethod.GET)
	public ModelAndView createDescription(@RequestParam int languageId) {
		ModelAndView result;
		
		Collection<Language> languages = languageService.findAll();
		Description description = new Description();
		String message;
		
		
		try{
		Assert.notNull(languages);
		Assert.isTrue(LoginService.getPrincipal().containsAuthority("ADMIN"));
		
		
		result = new ModelAndView("language/admin/addDescription");
		result.addObject("languages",languages);
		result.addObject("languageId",languageId);
		result.addObject("description",description);
		result.addObject("requestURI", "language/admin/addDescription.do");
		} catch(Throwable oops){
			
			if(LoginService.getPrincipal().containsAuthority("ADMIN")){
			
				message= "error.AddDescription.language";
			}else{
				message="error";
			}
			result = createListModelAndView(message);
			
		}
		
		
		
		return result;
	}

	@RequestMapping(value="/admin/addDescription", method= RequestMethod.POST,params="save")
	public ModelAndView descriptionSave(@RequestParam int languageId, @Valid Description description,  BindingResult binding){
		ModelAndView result;
		Collection<Language> languages = languageService.findAll();
		Language lanExc2 =languageService.findOne(languageId);
		//Collection<Language> languagesOwn =new ArrayList<Language>();
		boolean languageNoExist =true;
		for(Description desAux: lanExc2.getDescriptionsOwners()){
			if(description.getLanguage()==desAux.getLanguage()){
				languageNoExist =false;
			}
		}
		
		if (binding.hasErrors()) {
			result = new ModelAndView();
			result.addObject("languages",languages);
			result.addObject("languageId",languageId);
			result.addObject("description",description);
			BindingResult bR = binding;
			System.out.println(bR);
		} else {
			try {
				
				Assert.isTrue(LoginService.getPrincipal().containsAuthority("ADMIN"));
				Assert.notNull(languages);
				Assert.isTrue(languageNoExist);
				Language langExch = languageService.findOne(languageId);
				langExch.getDescriptionsOwners().add(description);
				languageService.saveModified(langExch);

			
				result = new ModelAndView("redirect:/language/admin/list.do");
				
			} catch (Throwable oops) {
					result = new ModelAndView();
					result.addObject("languages",languages);
					result.addObject("languageId",languageId);
					result.addObject("description",description);
					
				if(!(LoginService.getPrincipal().containsAuthority("ADMIN"))){
						
					result.addObject("message", "error.AddDescription.language");
				}else if(languageNoExist==false){
					result.addObject("message", "error.LanguageExist");
				}	else{
					result.addObject("message", "error");
				}	
			}
		}
		return result;
		
	}
	
	
}

