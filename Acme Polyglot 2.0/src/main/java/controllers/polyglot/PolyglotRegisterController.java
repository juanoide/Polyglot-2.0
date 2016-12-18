

package controllers.polyglot;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import controllers.AbstractController;

import domain.Actor;
import domain.Polyglot;

import forms.PolyglotForm;


import services.ActorService;
import services.PolyglotService;

@Controller
@RequestMapping("/polyglot")
public class PolyglotRegisterController extends AbstractController {

	// Managed service --------------------------------------------------------
	@Autowired
	private PolyglotService polyglotService;

	
	@Autowired
	private ActorService actorService;
	// Constructors -----------------------------------------------------------
	// Register ---------------------------------------------------------------		

	@RequestMapping(value="/register", method=RequestMethod.GET)
	public ModelAndView create() {
		ModelAndView result;
		PolyglotForm polyglotForm = new PolyglotForm();
		
		result= createEditModelAndView(polyglotForm);

		return result;
	}
	
	@RequestMapping(value="/register", method=RequestMethod.POST,params="save")
	public ModelAndView save(@Valid PolyglotForm polyglotForm, BindingResult binding) {
		
		ModelAndView result;
		Polyglot polyglot;
		Actor principal = actorService.findByPrincipalNullable();
		if(binding.hasErrors()){
			result=createEditModelAndView(polyglotForm);
		}else{
			try{
				Assert.isTrue(principal == null, "You can´t"); 
				polyglot=polyglotService.reconstruct(polyglotForm);
				polyglotService.save(polyglot);
				result=new ModelAndView("redirect:../");
			}catch (Throwable oops) {
				if(actorService.findByUsername(polyglotForm.getUsername())!=null)
					result = createEditModelAndView(polyglotForm,"polyglot.duplicated");
				else if(!polyglotForm.getValid())
					result = createEditModelAndView(polyglotForm,"polyglot.conditions");
				else if(polyglotForm.getPassword()!=polyglotForm.getRepeatPassword())
					result = createEditModelAndView(polyglotForm,"polyglot.passwordError");
				else if(principal == null)
					result = createEditModelAndView(polyglotForm,"polyglot.noAnonimous");
				else
					result = createEditModelAndView(polyglotForm,"polyglot.badPhoneNumber");
			}
		}
		return result;
	}
	// Ancillary methods
	protected ModelAndView createEditModelAndView(PolyglotForm polyglotForm){
		ModelAndView result;
		result=createEditModelAndView(polyglotForm,null);
		return result;
		
	}
	protected ModelAndView createEditModelAndView(PolyglotForm polyglotForm, String message){
		ModelAndView result;
		
		String requestURI="polyglot/register.do";
		
		result=new ModelAndView("polyglot/register");
		result.addObject("polyglotForm",polyglotForm);
		result.addObject("message",message);
		result.addObject("requestURI",requestURI);
		return result;
	}
}