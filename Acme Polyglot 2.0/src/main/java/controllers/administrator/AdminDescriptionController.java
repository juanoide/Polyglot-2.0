package controllers.administrator;

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


import domain.Description;
import domain.Language;



import security.LoginService;

import services.DescriptionService;

import services.LanguageService;


@Controller
@RequestMapping("/description")
public class AdminDescriptionController extends AbstractController{

	public AdminDescriptionController() {
		super();
		// TODO Auto-generated constructor stub
	}

	@Autowired
	private DescriptionService descriptionService;
	
	@Autowired
	private LanguageService languageService;

	

	
	@RequestMapping("/language/admin")
	public ModelAndView listOfLanguage(@RequestParam int languageId,@RequestParam int languageDescriptionId) {
		ModelAndView result;
		Collection<Description> descriptions =new ArrayList<Description>();
		Collection<Description> descriptionsOfLanguage =new ArrayList<Description>();
		String en= "en";
		Language english =languageService.searchLanguageForCode(en);
		Language language = languageService.findOne(languageId);
		Language languageDescription = languageService.findOne(languageDescriptionId);
		descriptions = descriptionService.descriptionsOFLanguage(languageId);
	
	//	String message;

			
			Assert.isTrue(LoginService.getPrincipal().containsAuthority("ADMIN"));
		
		//aqui comprueba si la descripcion elegida existe sino te carga una en ingles y si la de ingles no existe te carga la primera que haya
		for(Description des: descriptions){
			if(des.getLanguage()==languageDescription){
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
		
		
		
		result = new ModelAndView("description/language/admin");
		result.addObject("language",language);
		result.addObject("languageId",languageId);
		result.addObject("languageDescription",languageDescription);
		result.addObject("languageDescriptionId",languageDescriptionId);
		result.addObject("descriptions", descriptionsOfLanguage);
		result.addObject("requestURI", "description/language/admin.do");

		
		
		
		return result;
	}
	
	
	
	//edit description de lenguaje:
	
	

	@RequestMapping(value = "/language/admin/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam int descriptionId, @RequestParam int languageId) {
		ModelAndView result;
		Collection<Language> languages = languageService.findAll();
		
		Assert.notNull(languages);
		Assert.isTrue(LoginService.getPrincipal().containsAuthority("ADMIN"));
		Description description;
	
		description =descriptionService.findOne(descriptionId);
		
		result = new ModelAndView("description/language/admin/edit");		
		result.addObject("languages",languages);
		result.addObject("languageId",languageId);
		result.addObject("description",description);
		result.addObject("requestURI", "description/language/admin/edit.do");
			 
		return result;
	}
	
	
	
	@RequestMapping(value = "/language/admin/edit", method = RequestMethod.POST, params="save")
	public ModelAndView registerAnotherAgent(@RequestParam int languageId, @Valid Description description, BindingResult binding) {
		ModelAndView result;
		Collection<Language> languages = languageService.findAll();
		
		
		
		
		if (binding.hasErrors()) {
			result = new ModelAndView();
			result.addObject("languages",languages);
			result.addObject("languageId",languageId);
			result.addObject("description", description);
			BindingResult bR = binding;
			System.out.println(bR);
		} else {
			try {
				Assert.notNull(languages);
				Assert.isTrue(LoginService.getPrincipal().containsAuthority("ADMIN"));
				descriptionService.saveModified(description);
				Language langExch = languageService.findOne(languageId);
				for(Description des: langExch.getDescriptionsOwners()){
					System.out.println("esta es la primera descripcion:  " +description.getLanguage().getCode()+ "  esta es la segunda:  " + des.getLanguage().getCode());
					if(description.getLanguage()==des.getLanguage()){
						langExch.getDescriptionsOwners().remove(des);
						break;
					}
				}
				langExch.getDescriptionsOwners().add(description);
				languageService.saveModified(langExch);
				
				result = new ModelAndView("redirect:/language/admin/list.do");
				
			} catch (Throwable oops) {
				result = new ModelAndView();
				result.addObject("languages",languages);
				result.addObject("languageId",languageId);
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
	
		String requestURI="description/language/admin/edit.do";
		
		result = new ModelAndView("description/language/admin/edit");
		
		Collection<Language> languages = languageService.findAll();
		
		Assert.notNull(languages);
		
		result.addObject("languages",languages);
		
		result.addObject("description",des);
		
		result.addObject("message",message);

		result.addObject("requestURI",requestURI);
		
		return result;
	}
	
	@RequestMapping("/language/admin/delete")
	public ModelAndView delete(@RequestParam int descriptionId, @RequestParam int languageId, @RequestParam int languageDescriptionId) {
		ModelAndView result;
		String message;
		Language lanExc2 =languageService.findOne(languageId);
		Description des =descriptionService.findOne(descriptionId);
		
		boolean noLastOne=true;
		
		noLastOne=languageService.NolastOneDescription(lanExc2);
		
		
		try {
			Assert.isTrue(LoginService.getPrincipal().containsAuthority("ADMIN"));
			Assert.isTrue(noLastOne, "Is the last one");
			
			descriptionService.deleteWithLanguage(des, languageId);
			result = new ModelAndView("redirect:/language/admin/list.do");
		} catch (Throwable oops) {
			if(!(LoginService.getPrincipal().containsAuthority("ADMIN"))){
			message= "error.Description.Language.delete";
			}else if(noLastOne==false){
				message="error.theLastOneDescription";
			}else{
				message="error";
			}
			result = createListModelAndView(languageId, languageDescriptionId, message);
			
			
			}	
		return result;
	}
	
	protected ModelAndView createListModelAndView(@RequestParam int languageId,@RequestParam int languageDescriptionId, String message){
		ModelAndView result;
		Collection<Description> descriptions =new ArrayList<Description>();
		Collection<Description> descriptionsOfLanguage =new ArrayList<Description>();
		String en= "en";
		Language english =languageService.searchLanguageForCode(en);
		Language language = languageService.findOne(languageId);
		Language languageDescription = languageService.findOne(languageDescriptionId);
		descriptions = descriptionService.descriptionsOFLanguage(languageId);
	
		
		
		for(Description des: descriptions){
			if(des.getLanguage()==language){
				descriptionsOfLanguage.add(des);
			}
		}
		
		if(descriptionsOfLanguage.isEmpty()){
				for(Description des2:language.getDescriptions()){
					if(des2.getLanguage()==english){
						descriptionsOfLanguage.add(des2);
					}
				}
					
			}
		
		if(descriptionsOfLanguage.isEmpty()){
			for(Description des2:language.getDescriptions()){
					descriptionsOfLanguage.add(des2);
					break;
				}
			}
		
		result = new ModelAndView("description/language/admin");

		result.addObject("message",message);
		result.addObject("language",language);
		result.addObject("languageId",languageId);
		result.addObject("languageDescription",languageDescription);
		result.addObject("languageDescriptionId",languageDescriptionId);
		result.addObject("descriptions", descriptions);
		result.addObject("requestURI", "description/language/admin.do");

		return result;
		}


	
}
