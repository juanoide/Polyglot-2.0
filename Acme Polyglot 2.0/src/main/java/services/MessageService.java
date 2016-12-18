package services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.MessageRepository;
import security.LoginService;
import domain.Actor;
import domain.Folder;
import domain.JoinExchange;
import domain.LanguageExchange;
import domain.Message;
import domain.Polyglot;
import forms.MessageForm;
import forms.MessageLanguageExchangeForm;

@Service
@Transactional
public class MessageService {
	// Managed Repository ------------------------------
	@Autowired
	private MessageRepository messageRepository;

	// Supporting Services -----------------------------
	@Autowired
	private ActorService actorService;
	
	@Autowired
	private PolyglotService polyglotService;
	
	@Autowired
	private LanguageExchangeService languageExchangeService;
	@Autowired
	private FolderService folderService;
	



	// Constructors ------------------------------------
	public MessageService() {
		super();
	}

	// Simple CRUD Methods -----------------------------
	public Message create() {
		Message result = new Message();
		result.setMoment(new Date(System.currentTimeMillis()-1000));
		Actor actor = actorService.findByPrincipal();
		result.setSender(actor);
		result.setSpam(false);
		result.setFolder(folderService.findOutBoxOfActor(actor.getId()));
		return result;
	}
	
	public Message createWithLanguageExchange(int languageExchangeId) {
		Message result = new Message();
		result.setMoment(new Date(System.currentTimeMillis()-1000));
		Actor actor = actorService.findByPrincipal();
		result.setSender(actor);
		result.setSpam(false);
		result.setFolder(folderService.findLanguageExchangefolder(languageExchangeId, actor.getId()));
		return result;
	}
	
	public Message findOne(int messageId) {
		Assert.isTrue(messageId != 0);
		Message result = messageRepository.findOne(messageId);
		Assert.notNull(result);
		return result;
	}
	
	public void save(Message message) {
		Assert.notNull(message);
		if (message.getId() == 0) {
			Message message2 = new Message();
			
			message2.setSpam(message.getSpam());			
			message2.setText(message.getText());
			message2.setMoment(message.getMoment());
			message2.setRecipient(message.getRecipient());
			message2.setLinks(message.getLinks());
			message2.setTag(message.getTag());
			message2.setSender(message.getSender());
			message2.setTitle(message.getTitle());
			message2.setFolder(folderService.findInBoxOfActor(message.getRecipient().getId()));
			message2.setLanguage(message.getLanguage());
			message2.getFolder().getMessages().add(message2);
			folderService.save(message2.getFolder());
			messageRepository.saveAndFlush(message2);
			
			message.getFolder().getMessages().add(message);
			folderService.save(message.getFolder());
		}
		messageRepository.saveAndFlush(message);
	}
	
	
	public void saveLanguageExchange(Collection<Message> messages, int languageExchangeId) {
		Assert.notNull(messages);
		LanguageExchange languageExchange = languageExchangeService.findOne(languageExchangeId);
		Boolean variable = true;
		Polyglot c = polyglotService.findByPrincipal();
		if(languageExchange.getPolyglotOrganise()!=c){
			variable=false;
			for(JoinExchange join: languageExchange.getJoinExchanges()){
				if(join.getPolyglot().getId()==c.getId()){
					variable=true;
				}
			}
		
		}
		Assert.isTrue(LoginService.getPrincipal().containsAuthority("POLYGLOT"));
		Assert.isTrue(variable);
		
		for(Message message:messages){
		if (message.getId() == 0) {
			Message message2 = new Message();
			
			message2.setSpam(message.getSpam());			
			message2.setText(message.getText());
			message2.setMoment(message.getMoment());
			message2.setRecipient(message.getRecipient());
			message2.setLinks(message.getLinks());
			message2.setTag(message.getTag());
			message2.setSender(message.getSender());
			message2.setTitle(message.getTitle());
			
			message2.setFolder(folderService.findLanguageExchangefolder(languageExchangeId,message.getRecipient().getId()));
			message2.setLanguage(message.getLanguage());
			message2.getFolder().getMessages().add(message2);
			folderService.save(message2.getFolder());
			messageRepository.saveAndFlush(message2);
			
			message.getFolder().getMessages().add(message);
			folderService.save(message.getFolder());
		}
		messageRepository.saveAndFlush(message);
		}
	}
	
	public void delete(Message message) {
		Assert.notNull(message);
		Actor actor = actorService.findByPrincipal();
		Assert.notNull(actor);
		Assert.isTrue(actor.equals(message.getFolder().getActor()));
		if (!message.getFolder().getName().equals("Trash box")) {
			
			message.getFolder().getMessages().remove(message);
			folderService.save(message.getFolder());
				
			Folder folder = folderService.findTrashFolderOfActor(actor.getId());			
			folder.getMessages().add(message);

			message.setFolder(folder);
			folderService.save(folder);
			messageRepository.saveAndFlush(message);
		} else {
			message.getFolder().getMessages().remove(message);
			folderService.save(message.getFolder());
			messageRepository.delete(message);
		}
	}

	public void remove(Message message) {

			messageRepository.delete(message);
		}
	
	
	// Other Business Methods ---------------------------

	public Message reconstruct(MessageForm messageForm) {
		Assert.notNull(messageForm);
		Assert.notNull(messageForm.getLanguage());
		Actor actor = actorService.findByPrincipal();
		Assert.notNull(actor);
		Message result = create();
		Actor recipient = actorService.findOne(messageForm.getRecipient());
		Assert.notNull(recipient);
		result.setText(messageForm.getText());
		result.setRecipient(recipient);
		
		
		
	String linksWithoutParsing = messageForm.getLinks();
		
		String[] tokens= linksWithoutParsing.split(",");
		Collection<String> links = new ArrayList<String>();
		for(String l:tokens){
			links.add(l);
	
		}
				
		result.setLinks(links);
		//tag sin parsear, vamos a parsear para separarlo por comas.
		String tagWithoutParsing = messageForm.getTag();
		
		String[] tokens2= tagWithoutParsing.split(",");
		Collection<String> tag = new ArrayList<String>();
		for(String l:tokens2){
			tag.add(l);
	
		}
		result.setTag(tag);
		
		
		result.setTitle(messageForm.getTitle());
		result.setLanguage(messageForm.getLanguage());
		return result;
	}
	
	public Collection<Message> reconstructLanguageExchange(MessageLanguageExchangeForm messageForm) {
		Assert.notNull(messageForm);
		Actor actor = actorService.findByPrincipal();
		Assert.notNull(actor);
		LanguageExchange languageExchange = languageExchangeService.findOne(messageForm.getLanguageExchange());
		Collection<Message>result=new ArrayList<Message>();
		for(JoinExchange joinExchangeAux: languageExchange.getJoinExchanges()){
			
		if(actor!=joinExchangeAux.getPolyglot()){
		
		Message message = createWithLanguageExchange(messageForm.getLanguageExchange());
		Actor recipient = joinExchangeAux.getPolyglot();
		Assert.notNull(recipient);
		message.setText(messageForm.getText());
		message.setRecipient(recipient);
		
		
		
	String linksWithoutParsing = messageForm.getLinks();
		
		String[] tokens= linksWithoutParsing.split(",");
		Collection<String> links = new ArrayList<String>();
		for(String l:tokens){
			links.add(l);
	
		}
				
		message.setLinks(links);
		//tag sin parsear, vamos a parsear para separarlo por comas.
		String tagWithoutParsing = messageForm.getTag();
		
		String[] tokens2= tagWithoutParsing.split(",");
		Collection<String> tag = new ArrayList<String>();
		for(String l:tokens2){
			tag.add(l);
	
		}
		message.setTag(tag);
		
		
		message.setTitle(messageForm.getTitle());
		message.setLanguage(messageForm.getLanguage());
		result.add(message);
		}
		}
		
		return result;
	}

	public void flagAsSpamAndSave(Message message) {
		Assert.notNull(message);
		Actor actor = actorService.findByPrincipal();
		Assert.notNull(actor);
		Assert.isTrue(actor.equals(message.getFolder().getActor()));
		
		message.getFolder().getMessages().remove(message);		
		Folder folder = folderService.findSpamBoxOfActor(actor.getId());			
		folder.getMessages().add(message);
		message.setFolder(folder);
		message.setSpam(true);
		messageRepository.saveAndFlush(message);
	}
	
	
}
