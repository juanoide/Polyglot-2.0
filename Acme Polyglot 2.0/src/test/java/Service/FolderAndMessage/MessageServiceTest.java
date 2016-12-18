package Service.FolderAndMessage;



import java.util.Collection;

import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.util.Assert;


import domain.Language;
import domain.LanguageExchange;
import domain.Message;
import domain.Polyglot;
import forms.MessageForm;
import forms.MessageLanguageExchangeForm;

import services.ActorService;
import services.FolderService;

import services.LanguageService;
import services.MessageService;
import services.PolyglotService;
import utilities.AbstractTest;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:spring/datasource.xml",
					"classpath:spring/config/packages.xml" })
@Transactional
@TransactionConfiguration(defaultRollback = false)
public class MessageServiceTest  extends AbstractTest {
	
	// Service under test ------------------------
	@Autowired
	private MessageService messageService;
	
	// Supporting services -----------------------
	@Autowired
	private ActorService actorService;
	@Autowired
	private FolderService folderService;
	
	@Autowired
	private LanguageService languageService;
	
	
	@Autowired
	private PolyglotService polyglotService;
	
	/*
	 * Actors can create send message a other actors, etc...
	 */
	// POSITIVE TEST CASE: An polyglot can seending message from languageExchange
	@Test
	public void sendingMessageFromLanguageExchangeTest(){	
		authenticate("polyglot1");			
		int senderId = actorService.findByPrincipal().getId();	
		int numMsgSentActual = folderService.findOutBoxOfActor(senderId).getMessages().size();
		int languageExchangeId = 0;
		
		Polyglot c = polyglotService.findByPrincipal();
		
		Collection<LanguageExchange> languageExchanges =  c.getOrganiseExchanges();
		
		for(LanguageExchange langExchAux:languageExchanges){
			languageExchangeId=langExchAux.getId();
			break;
		}
			
		
		
		
		MessageLanguageExchangeForm messageLanguageExchangeForm = new MessageLanguageExchangeForm();
		Collection<Language> languages = languageService.findAll();
		Language language = null;
		for(Language l: languages){
			language= l;
			break;
		}

		messageLanguageExchangeForm.setTitle("Subject Test");
		messageLanguageExchangeForm.setText("Body Test");
		messageLanguageExchangeForm.setLinks("https://projetsii3q7vprzje3n44wfxr/issues, https://projetsiasasafa/issues");
		messageLanguageExchangeForm.setTag("cosas, test");
		messageLanguageExchangeForm.setLanguage(language);
		messageLanguageExchangeForm.setLanguageExchange(languageExchangeId);
	
		Collection<Message> messages = messageService.reconstructLanguageExchange(messageLanguageExchangeForm);
		
		for(Message message: messages){
			messageService.save(message);
		}
	
		unauthenticate();		
		
		int numMsgSentExpected = folderService.findOutBoxOfActor(senderId).getMessages().size();
	

		Assert.isTrue(numMsgSentExpected == numMsgSentActual);
	
	}
	// POSITIVE TEST CASE: An polyglot can seending message a admin
	@Test
	public void sendingMessageFromUserToAdminTest(){	
		authenticate("polyglot1");			
		int senderId = actorService.findByPrincipal().getId();	
		int numMsgSentActual = folderService.findOutBoxOfActor(senderId).getMessages().size();
		unauthenticate();	
		
		authenticate("admin");			
		int recipientId = actorService.findByPrincipal().getId();	
		int numMsgReceivedActual = folderService.findInBoxOfActor(recipientId).getMessages().size();
		unauthenticate();

		authenticate("polyglot1");			
		MessageForm messageForm = new MessageForm();
		Collection<Language> languages = languageService.findAll();
		Language language = null;
		for(Language l: languages){
			language= l;
			break;
		}

		messageForm.setTitle("Subject Test");
		messageForm.setText("Body Test");
		messageForm.setLinks("https://projetsii3q7vprzje3n44wfxr/issues, https://projetsiasasafa/issues");
		messageForm.setTag("cosas, test");
		messageForm.setLanguage(language);
		messageForm.setRecipient(recipientId);
		Message message = messageService.reconstruct(messageForm);
		messageService.save(message);
		unauthenticate();		
		
		int numMsgSentExpected = folderService.findOutBoxOfActor(senderId).getMessages().size();
		int numMsgReceivedExpected = folderService.findInBoxOfActor(recipientId).getMessages().size();

		Assert.isTrue(numMsgSentExpected == numMsgSentActual+1);
		Assert.isTrue(numMsgReceivedExpected == numMsgReceivedActual+1);
	}
	
	
	// NEGATIVE TEST CASE: An ANONYMOUS cannot seending message
	
	@Test(expected = IllegalArgumentException.class)
	@Rollback(true)
	public void sendingMessageAsNonAuthenticatedNegativeTest1(){			
		authenticate("admin");			
		int recipientId = actorService.findByPrincipal().getId();
		unauthenticate();

		authenticate("polyglot1");		
		Collection<Language> languages = languageService.findAll();
		Language language = null;
		for(Language l: languages){
			language= l;
			break;
		}
		MessageForm messageForm = new MessageForm();
		messageForm.setLanguage(language);
		messageForm.setTitle("Subject Test");
		messageForm.setText("Body Test");
		messageForm.setLanguage(language);
		messageForm.setLinks("https://projetsii3q7vprzje3n44wfxr/issues, https://projetsiasasafa/issues");
		messageForm.setTag("cosas, test");
		messageForm.setRecipient(recipientId);
		Message message = messageService.reconstruct(messageForm);
		unauthenticate();		
		
		messageService.save(message);
	}

	
	// NEGATIVE TEST CASE: An ANONYMOUS cannot seending message whithout language
	
	
	@Test(expected = IllegalArgumentException.class)
	@Rollback(true)
	public void sendingMessageAsNonLanguageNegativeTest(){			
		authenticate("polyglot1");			
		int senderId = actorService.findByPrincipal().getId();	
		int numMsgSentActual = folderService.findOutBoxOfActor(senderId).getMessages().size();
		unauthenticate();	
		
		authenticate("admin");			
		int recipientId = actorService.findByPrincipal().getId();	
		int numMsgReceivedActual = folderService.findInBoxOfActor(recipientId).getMessages().size();
		unauthenticate();
		
		authenticate("polyglot1");			
		MessageForm messageForm = new MessageForm();
		Language language =null;
	

		messageForm.setTitle("Subject Test");
		messageForm.setText("Body Test");
		messageForm.setLinks("https://projetsii3q7vprzje3n44wfxr/issues, https://projetsiasasafa/issues");
		messageForm.setTag("cosas, test");
		messageForm.setLanguage(language);
		messageForm.setRecipient(recipientId);
		Message message = messageService.reconstruct(messageForm);
		messageService.save(message);
		unauthenticate();		
		
		int numMsgSentExpected = folderService.findOutBoxOfActor(senderId).getMessages().size();
		int numMsgReceivedExpected = folderService.findInBoxOfActor(recipientId).getMessages().size();

		Assert.isTrue(numMsgSentExpected == numMsgSentActual+1);
		Assert.isTrue(numMsgReceivedExpected == numMsgReceivedActual+1);
	}
	
	// POSITIVE TEST CASE: An administrator can seending message go to trashBox
	
	@Test
	public void deleteMessageFromTrashBoxAsAdminTest() {
		authenticate("admin");
		int actorId = actorService.findByPrincipal().getId();
		Collection<Message> trashBoxMessages = folderService.findTrashFolderOfActor(actorId).getMessages();
		int numMessagesActual = trashBoxMessages.size()+
				folderService.findInBoxOfActor(actorId).getMessages().size()+
				folderService.findOutBoxOfActor(actorId).getMessages().size()+
				folderService.findSpamBoxOfActor(actorId).getMessages().size();		
		Message msgToDelete = trashBoxMessages.iterator().next();
		messageService.delete(msgToDelete);		
		unauthenticate();	

		int numMessagesExpected = folderService.findTrashFolderOfActor(actorId).getMessages().size()+
				folderService.findInBoxOfActor(actorId).getMessages().size()+
				folderService.findOutBoxOfActor(actorId).getMessages().size()+
				folderService.findSpamBoxOfActor(actorId).getMessages().size();	
		Assert.isTrue(numMessagesActual - 1 == numMessagesExpected);
	}

	// NEGATIVE TEST CASE: An polyglot cannot send message with no recipient
	
	@Test(expected = IllegalArgumentException.class)
	@Rollback(true)
	public void sendMessageWithNoRecipientNegativeTest() {		
		authenticate("polyglot1");			
		MessageForm messageForm = new MessageForm();
		Collection<Language> languages = languageService.findAll();
		
		Language language = null;
		for(Language l: languages){
			language= l;
			break;
		}
		messageForm.setTitle("Subject Test");
		messageForm.setText("Body Test");
		messageForm.setLanguage(language);
		messageForm.setLinks("https://projetsii3q7vprzje3n44wfxr/issues, https://projetsiasasafa/issues");
		messageForm.setTag("cosas, test");
		Message message = messageService.reconstruct(messageForm);
		messageService.save(message);
		unauthenticate();		
	}
	
	// POSITIVE TEST CASE: An admin can flag message as spam

	
		@Test
		public void flagMessageAsSpamTest() {
			authenticate("admin");
			int actorId = actorService.findByPrincipal().getId();
			Collection<Message> inBoxMessages = folderService.findInBoxOfActor(actorId).getMessages();
			int numInBoxMsgActual = inBoxMessages.size();
			int numSpamBoxMsgActual = folderService.findSpamBoxOfActor(actorId).getMessages().size();		
			Message msgToFlag = inBoxMessages.iterator().next();
			messageService.flagAsSpamAndSave(msgToFlag);		
			unauthenticate();	

			int numInBoxMsgExpected = folderService.findInBoxOfActor(actorId).getMessages().size();
			int numSpamBoxMsgExpected = folderService.findSpamBoxOfActor(actorId).getMessages().size();
			
			Assert.isTrue(numInBoxMsgActual - 1 == numInBoxMsgExpected);
			Assert.isTrue(numSpamBoxMsgActual + 1 == numSpamBoxMsgExpected);
		}
	
}
