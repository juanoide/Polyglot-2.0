

package controllers;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.ActorService;

import domain.Actor;





@Controller
@RequestMapping("/administrator")
public class AdministratorController extends AbstractController {

	// Managed service ----------------------------------------------

	@Autowired
	private ActorService actorService;
	// Constructors -----------------------------------------------------------
	
	public AdministratorController() {
		super();
	}
		


	@RequestMapping("/ban/disable")
	public ModelAndView disabled(@RequestParam int actorId) {
		ModelAndView result;
	
		actorService.disable(actorId);
		
		result = new ModelAndView("redirect:list.do");	;
		
		return result;
	}
	
	@RequestMapping("/ban/enable")
	public ModelAndView enabled(@RequestParam int actorId) {
		ModelAndView result;
	
		actorService.enable(actorId);
		
		result = new ModelAndView("redirect:list.do");	;
		
		return result;
	}
	

	
	@RequestMapping("/ban/list")
	public ModelAndView Banelection() {
		ModelAndView result;
		Collection<Actor> actores = actorService.findAll();
		String requestURI="administrator/ban/list.do";
		
		boolean variable = false;
		boolean isBanned = false;
	
		
		result = new ModelAndView("administrator/ban/list");
		result.addObject("isBanned",isBanned);
		result.addObject("actores",actores);
		result.addObject("variable", variable);
		result.addObject("requestURI",requestURI);
		
		return result;
	}
	
}