/* WelcomeController.java
 *
 * Copyright (C) 2014 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the 
 * TDG Licence, a copy of which you may download from 
 * http://www.tdg-seville.info/License.html
 * 
 */

package controllers;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import security.UserAccount;
import services.AdministratorService;
import services.AgentService;
import services.PolyglotService;
import domain.Actor;

@Controller
@RequestMapping("/welcome")
public class WelcomeController extends AbstractController {

	// Services ----------------------------------------------------------------
	@Autowired
	private AdministratorService administratorService;
	@Autowired
	private PolyglotService polyglotService;
	@Autowired
	private AgentService agentService;
	
	// Constructors -----------------------------------------------------------
	
	public WelcomeController() {
		super();
	}
	
	
		
	// Index ------------------------------------------------------------------		

	@RequestMapping(value = "/index")
	public ModelAndView index(
			@RequestParam(required = false, defaultValue = "") String name) {
		ModelAndView result;
		SimpleDateFormat formatter;
		String moment;

		Actor actor = null;
		SecurityContext context = SecurityContextHolder.getContext();
		Authentication authentication = context == null ? null : context
				.getAuthentication();
		Object principal = authentication == null ? null : authentication
				.getPrincipal();
		UserAccount ua = principal instanceof UserAccount ? (UserAccount) principal
				: null;

		if (ua != null) {
			actor = administratorService.findByUserAccount(ua);
			if (actor == null) {
				actor = polyglotService.findByUserAccount(ua);
				if (actor == null) {
					actor = agentService.findByUserAccount(ua);
				}
			}
		}
		name = actor == null ? name : actor.getName() + " "
				+ actor.getSurname();
		name = name.equals("") ? name : ", " + name;

		formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm");
		moment = formatter.format(new Date());

		result = new ModelAndView("welcome/index");
		result.addObject("name", name);
		result.addObject("moment", moment);

		return result;
	}
}