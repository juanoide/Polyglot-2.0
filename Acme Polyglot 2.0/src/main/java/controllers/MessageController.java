package controllers;

import java.util.Collection;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.ObjectOptimisticLockingFailureException;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import services.ActorService;
import services.FolderService;
import services.LanguageService;
import services.MessageService;


import domain.Actor;
import domain.Folder;
import domain.Language;

import domain.Message;


import forms.MessageForm;


@Controller
@RequestMapping("/message")
public class MessageController extends AbstractController {
	
	//Services --------------------------------------
	@Autowired
	private MessageService messageService;
	@Autowired
	private FolderService folderService;
	@Autowired
	private ActorService actorService;
	

	
	@Autowired
	private LanguageService languageService;	
	
	//Constructor -----------------------------------
	public MessageController() {
		super();
	}
	
	//Listing ---------------------------------------
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list(@RequestParam int folderId) {
		ModelAndView result;
		Folder folder = folderService.findOne(folderId);
		
		Assert.isTrue(actorService.findByPrincipal().equals(folder.getActor()));
		
		result = new ModelAndView("message/list");

		result.addObject("flagged", folder.getName().equals("Spam box")&&folder.getSystemFolder());
		result.addObject("messages", folder.getMessages());
		result.addObject("requestURI", "message/list.do");
		return result;
	}
	
	//Flag message ----------------------------------
	@RequestMapping(value = "/flag", method = RequestMethod.GET)
	public ModelAndView flag(@RequestParam int messageId, RedirectAttributes redirectAttrs) {
		ModelAndView result;
		Message message = messageService.findOne(messageId);
		
		if(message.getFolder().equals("Spam box")&&message.getFolder().getSystemFolder()){
			result = new ModelAndView("redirect:list.do?folderId=" + message.getFolder().getId());
			redirectAttrs.addFlashAttribute("message", "message.already.flagged");
		}else{
			try {
				messageService.flagAsSpamAndSave(message);
				result = new ModelAndView("redirect:list.do?folderId=" + message.getFolder().getId());
			} catch (Throwable oops) {
				result = new ModelAndView("redirect:/folder/list.do");
				redirectAttrs.addFlashAttribute("message", "message.commit.error");
			}
		}
		
		return result;
	}	
	//Send message ----------------------------------
	@RequestMapping(value = "/send", method = RequestMethod.GET)
	public ModelAndView send() {
		MessageForm messageForm = new MessageForm();
		return createEditModelAndView(messageForm, null);
	}
	
	
	
	//Edit -----------------------------------------
	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid MessageForm messageForm, BindingResult binding, RedirectAttributes redirectAttrs) {
		ModelAndView result;

		if (messageForm.getRecipient()==0){
			result = createEditModelAndView(messageForm, "message.select.recipient");
		}else if (binding.hasErrors()) {
			result = createEditModelAndView(messageForm, null);
		} else {
			try {				
				Message message = messageService.reconstruct(messageForm);
				messageService.save(message);
				result = new ModelAndView("redirect:/folder/list.do");
			} catch(ObjectOptimisticLockingFailureException exc) {
				result = createEditModelAndView(messageForm, "message.concurrencyError");
			} catch (Throwable oops) {	
				result = createEditModelAndView(messageForm, "message.commit.error");				
			}
		}

		return result;
	}
	
	
	//Delete ---------------------------------------
	@RequestMapping(value = "/delete", method = RequestMethod.GET)
	public ModelAndView deleteMessage(@RequestParam int messageId, RedirectAttributes redirectAttrs) {
		ModelAndView result;
		Message message = messageService.findOne(messageId);
		
		try {
			messageService.delete(message);
			result = new ModelAndView("redirect:list.do?folderId=" + message.getFolder().getId());
		} catch (Throwable oops) {
			result = new ModelAndView("redirect:/folder/list.do");
			redirectAttrs.addFlashAttribute("message", "message.delete.error");
		}
		
		return result;
	}
	
	//Ancillary methods -----------------------------
		
	protected ModelAndView createEditModelAndView(MessageForm messageForm, String message) {
		ModelAndView result;
		Collection<Actor> actors = actorService.findAll();
		Collection<Language> languages = languageService.findAll();	
		result = new ModelAndView("message/send");
		result.addObject("messageForm", messageForm);
		result.addObject("actors", actors);
		result.addObject("message", message);
		result.addObject("languages", languages);
		return result;

	}
	


}

