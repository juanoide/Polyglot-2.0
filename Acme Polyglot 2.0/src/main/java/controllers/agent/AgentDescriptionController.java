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


import domain.Description;
import domain.Language;
import domain.Sponsorship;
import domain.Agent;


import security.LoginService;

import services.DescriptionService;
import services.SponsorshipService;
import services.LanguageService;
import services.AgentService;

@Controller
@RequestMapping("/description")
public class AgentDescriptionController extends AbstractController{

	public AgentDescriptionController() {
		super();
		
	}

	@Autowired
	private DescriptionService descriptionService;
	
	@Autowired
	private LanguageService languageService;
	
	@Autowired
	private SponsorshipService sponsorshipService;
	
	@Autowired
	private AgentService agentService;
	

	
	@RequestMapping("/sponsorship/agent")
	public ModelAndView listOfSponsorship(@RequestParam int sponsorshipId,@RequestParam int languageId) {
		ModelAndView result;
		Collection<Description> descriptions =new ArrayList<Description>();
		Collection<Description> descriptionsOfLanguage =new ArrayList<Description>();
		String en= "en";
		Language english =languageService.searchLanguageForCode(en);
		Sponsorship sponsorship = sponsorshipService.findOne(sponsorshipId);
		Language language = languageService.findOne(languageId);
		descriptions = descriptionService.descriptionsOFSponsorship(sponsorshipId);
		boolean isMine = false;
		
		
		Agent c = agentService.findByPrincipal();
		
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
		
		result = new ModelAndView("description/sponsorship/agent");
		result.addObject("isMine",isMine);
		result.addObject("agentprincipal",c);
		result.addObject("sponsorship",sponsorship);
		result.addObject("languageId",languageId);
		result.addObject("language",language);
		result.addObject("sponsorshipId",sponsorshipId);
		result.addObject("descriptions", descriptionsOfLanguage);
		result.addObject("requestURI", "description/sponsorship/agent.do");

		return result;
	}
	
	
	
	//edit description de intercambios de lenguaje:
	
	

	@RequestMapping(value = "/sponsorship/agent/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam int descriptionId, @RequestParam int sponsorshipId) {
		ModelAndView result;
		Collection<Language> languages = languageService.findAll();
		
		Assert.notNull(languages);
		Assert.isTrue(LoginService.getPrincipal().containsAuthority("AGENT"));
		Description description;
	
		description =descriptionService.findOne(descriptionId);
		
		result = new ModelAndView("description/sponsorship/agent/edit");		
		result.addObject("languages",languages);
		result.addObject("sponsorshipId",sponsorshipId);
		result.addObject("description",description);
		result.addObject("requestURI", "description/sponsorship/agent/edit.do");
			 
		return result;
	}
	
	
	
	@RequestMapping(value = "/sponsorship/agent/edit", method = RequestMethod.POST, params="save")
	public ModelAndView registerAnotherAgent(@RequestParam int sponsorshipId, @Valid Description description, BindingResult binding) {
		ModelAndView result;
		Collection<Language> languages = languageService.findAll();
		
		Assert.notNull(languages);
		Assert.isTrue(LoginService.getPrincipal().containsAuthority("AGENT"));
		
		
		if (binding.hasErrors()) {
			result = new ModelAndView();
			result.addObject("languages",languages);
			result.addObject("sponsorshipId",sponsorshipId);
			result.addObject("description", description);
			BindingResult bR = binding;
			System.out.println(bR);
		} else {
			try {
				descriptionService.saveModified(description);
				Sponsorship langExch = sponsorshipService.findOne(sponsorshipId);
				for(Description des: langExch.getDescriptions()){
					if(description.getLanguage()==des.getLanguage()){
						langExch.getDescriptions().remove(des);
						break;
					}
				}
				langExch.getDescriptions().add(description);
				sponsorshipService.saveModified(langExch);
				
				result = new ModelAndView("redirect:/sponsorship/agent/list.do");
				
			} catch (Throwable oops) {
				result = new ModelAndView();
				result.addObject("languages",languages);
				result.addObject("sponsorshipId",sponsorshipId);
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
	
		String requestURI="description/sponsorship/agent/edit.do";
		
		result = new ModelAndView("description/sponsorship/agent/edit");
		
		Collection<Language> languages = languageService.findAll();
		
		Assert.notNull(languages);
		
		result.addObject("languages",languages);
		
		result.addObject("description",des);
		
		result.addObject("message",message);

		result.addObject("requestURI",requestURI);
		
		return result;
	}
	
	@RequestMapping("/sponsorship/agent/delete")
	public ModelAndView delete(@RequestParam int descriptionId, @RequestParam int sponsorshipId, @RequestParam int languageId) {
		ModelAndView result;
		String message;
		Sponsorship lanExc2 =sponsorshipService.findOne(sponsorshipId);
		Description des =descriptionService.findOne(descriptionId);
		
		boolean noLastOne=true;
		
		noLastOne=sponsorshipService.NolastOneDescription(lanExc2);
		
		
		try {
			Assert.isTrue(lanExc2.getAgent()==agentService.findByPrincipal(), "You don´t have permission");
			Assert.isTrue(noLastOne, "Is the last one");
			
			descriptionService.deleteWithSponsorship(des, sponsorshipId);
			result = new ModelAndView("redirect:/sponsorship/agent/list.do");
		} catch (Throwable oops) {
			if(lanExc2.getAgent()!=agentService.findByPrincipal()){
			message= "error.Description.Sponsorship.delete";
			}else if(noLastOne==false){
				message="error.theLastOneDescriptionSponsorship";
			}else{
				message="error";
			}
			result = createListModelAndView(sponsorshipId, languageId, message);
			
			
			}	
		return result;
	}
	
	protected ModelAndView createListModelAndView(@RequestParam int sponsorshipId,@RequestParam int languageId, String message){
		ModelAndView result;
		Collection<Description> descriptions =new ArrayList<Description>();
		Collection<Description> descriptionsOfLanguage =new ArrayList<Description>();
		String en= "en";
		Language english =languageService.searchLanguageForCode(en);
		Sponsorship sponsorship = sponsorshipService.findOne(sponsorshipId);
		Language language = languageService.findOne(languageId);
		descriptions = descriptionService.descriptionsOFSponsorship(sponsorshipId);
		boolean isMine = false;
		Agent c = agentService.findByPrincipal();
		
		for(Description des: descriptions){
			if(des.getLanguage()==language){
				descriptionsOfLanguage.add(des);
			}
		}
		
		if(descriptionsOfLanguage.isEmpty()){
				for(Description des2:sponsorship.getDescriptions()){
					if(des2.getLanguage()==english){
						descriptionsOfLanguage.add(des2);
					}
				}
					
			}
		
		if(descriptionsOfLanguage.isEmpty()){
			for(Description des2:sponsorship.getDescriptions()){
					descriptionsOfLanguage.add(des2);
					break;
				}
			}
		
		result = new ModelAndView("description/sponsorship/agent");
		result.addObject("isMine",isMine);
		result.addObject("agentprincipal",c);
		result.addObject("message",message);
		result.addObject("sponsorship",sponsorship);
		result.addObject("sponsorshipId",sponsorshipId);
		result.addObject("language",language);
		result.addObject("languageId",languageId);
		result.addObject("descriptions", descriptionsOfLanguage);
		result.addObject("requestURI", "description/sponsorship/agent.do");

		return result;
		}


	
}
