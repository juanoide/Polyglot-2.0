package controllers;

import java.util.Collection;
import java.util.HashSet;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.ObjectOptimisticLockingFailureException;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import services.ActorService;

import services.LanguageExchangeService;
import services.LanguageService;
import services.MessageService;
import services.PolyglotService;

import domain.Actor;

import domain.JoinExchange;
import domain.Language;
import domain.LanguageExchange;
import domain.Message;
import domain.Polyglot;


import forms.MessageLanguageExchangeForm;

@Controller
@RequestMapping("/message")
public class MessageLanguageExchangeController extends AbstractController {
	
	//Services --------------------------------------
	@Autowired
	private MessageService messageService;

	@Autowired
	private ActorService actorService;
	
	@Autowired
	private PolyglotService polyglotService;
	
	@Autowired
	private LanguageService languageService;	
	
	@Autowired
	private LanguageExchangeService languageExchangeService;	
	
	//Constructor -----------------------------------
	public MessageLanguageExchangeController() {
		super();
	}
	

	
	@RequestMapping(value = "/send/languageExchange", method = RequestMethod.GET)
	public ModelAndView sendingLanguageExchange() {
		MessageLanguageExchangeForm messageLanguageExchangeForm = new MessageLanguageExchangeForm();
		return createEditModelAndView(messageLanguageExchangeForm, null);
	}

	//Edit -----------------------------------------
	@RequestMapping(value = "/edit/languageExchange", method = RequestMethod.POST, params = "save")
	public ModelAndView saveLanguageExchange(@Valid MessageLanguageExchangeForm messageLanguageExchangeForm, BindingResult binding) {
		ModelAndView result;
		LanguageExchange langExchAuxNoEmpty= languageExchangeService.findOne(messageLanguageExchangeForm.getLanguageExchange());
		Boolean onlyYou=false;
		Collection<Polyglot> polyglots = new HashSet<Polyglot>();
		Polyglot c = polyglotService.findByPrincipal();
		for(JoinExchange joinExchange:langExchAuxNoEmpty.getJoinExchanges()){
			polyglots.add(joinExchange.getPolyglot());
			
		}
		
		if(polyglots.size()<2){
			for(Polyglot polyglot: polyglots){
				if(polyglot==c){
					onlyYou=true;
				}
			}
		}
		
		
		if (messageLanguageExchangeForm.getLanguageExchange()==0){
			result = createEditModelAndView(messageLanguageExchangeForm, "message.select.LanguageExchange");
		}else if (langExchAuxNoEmpty.getJoinExchanges().isEmpty()) {
			result = createEditModelAndView(messageLanguageExchangeForm, "message.select.LanguageExchange.empty");
		}else if (onlyYou) {
			result = createEditModelAndView(messageLanguageExchangeForm, "message.select.LanguageExchange.onlyYou");
		} else if (binding.hasErrors()) {
			result = createEditModelAndView(messageLanguageExchangeForm, null);
		} else {
			try {				
				Collection<Message> message = messageService.reconstructLanguageExchange(messageLanguageExchangeForm);
				messageService.saveLanguageExchange(message, messageLanguageExchangeForm.getLanguageExchange());
				result = new ModelAndView("redirect:/folder/list.do");
			} catch(ObjectOptimisticLockingFailureException exc) {
				result = createEditModelAndView(messageLanguageExchangeForm, "message.concurrencyError");
			} catch (Throwable oops) {	
				result = createEditModelAndView(messageLanguageExchangeForm, "message.commit.error");				
			}
		}

		return result;
	}
	
	
	
	protected ModelAndView createEditModelAndView(MessageLanguageExchangeForm messageLanguageExchangeForm, String message) {
		ModelAndView result;
		Collection<Actor> actors = actorService.findAll();
		Collection<Language> languages = languageService.findAll();	
		Polyglot c = polyglotService.findByPrincipal();
		Collection<LanguageExchange> languageExchanges =  new HashSet<LanguageExchange>();
				languageExchanges.addAll(c.getOrganiseExchanges());
			
				
		for(JoinExchange joinExch: c.getJoinExchanges()){
		/*	for(LanguageExchange langExc: languageExchanges){
				if(langExc!=joinExch.getLanguageExchange()){*/
				languageExchanges.add(joinExch.getLanguageExchange());
				
			
			
		}
		
		
		result = new ModelAndView("message/send/languageExchange");
		result.addObject("messageLanguageExchangeForm", messageLanguageExchangeForm);
		result.addObject("actors", actors);
		result.addObject("message", message);
		result.addObject("languages", languages);
		result.addObject("languageExchanges", languageExchanges);
		return result;

	}

}

