package services;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.FolderRepository;
import domain.Actor;
import domain.Folder;
import domain.Message;
import forms.FolderForm;

@org.springframework.stereotype.Service
@Transactional
public class FolderService {
	// Managed Repository ------------------------------
	@Autowired
	private FolderRepository folderRepository;

	// Supporting Services -----------------------------
	@Autowired
	private MessageService messageService;
	

	
	@Autowired
	private ActorService actorService;


	// Constructors ------------------------------------
	public FolderService() {
		super();
	}

	// Simple CRUD Methods -----------------------------
	
	public Folder create(){
		Folder result = new Folder();
		result.setMessages(new ArrayList<Message>());
		result.setActor(actorService.findByPrincipal());
		return result;
	}
	
	
	public Folder findOne(int folderId) {
		Assert.isTrue(folderId != 0);
		Folder result = folderRepository.findOne(folderId);
		Assert.notNull(result);
		return result;
	}
	
	public void save(Folder folder) {
		Assert.notNull(folder);
		Actor actor = actorService.findByPrincipal();
		Assert.notNull(actor);	
		folderRepository.saveAndFlush(folder);
	}
	
	public void delete(Folder folder) {
		Assert.notNull(folder);
		Actor actor = actorService.findByPrincipal();
		Assert.isTrue(folderRepository.exists(folder.getId()));
		Assert.isTrue(!folder.getSystemFolder());
		Assert.isTrue(folder.getActor().getId()==actor.getId());
		for (Message m: folder.getMessages()) {
			messageService.remove(m);
		}
		folderRepository.delete(folder);
		folderRepository.flush();
	}

	public void deleteSystemFolder(Folder folder) {
		Assert.notNull(folder);
		Actor actor = actorService.findByPrincipal();
		Assert.isTrue(folderRepository.exists(folder.getId()));
		Assert.isTrue(folder.getActor().getId()==actor.getId());
		for (Message m: folder.getMessages()) {
			messageService.remove(m);
		}
		folderRepository.delete(folder);
		folderRepository.flush();
	}

	
	
	// Other Business Methods ---------------------------
	
	public Collection<Folder> findFoldersOfActor(int actorId){
		return folderRepository.foldersOfActor(actorId);
	}
	
	public Collection<Folder> nonSystemFoldersOfActor(int actorId){
		return folderRepository.nonSystemFoldersOfActor(actorId);
	}
	
	public Folder findTrashFolderOfActor (int actorId){
		return folderRepository.foldersTrashOfActor(actorId);
	}
	
	public Folder findLanguageExchangefolder(int languageExchangeId, int actorId){
		//LanguageExchange languageExchange = languageExchangeService.findOne(languageExchangeId);
		Collection<Folder> folders =folderRepository.foldersOfActor(actorId);
		Folder folder =create();
		 for(Folder fold:folders){
			 if(fold.getLanguageExchange()!=null){
			 if(fold.getLanguageExchange().getId()==languageExchangeId){
				 folder=fold;
			 }
			 }
		 }
		 
		 return folder;
	}
	
	public Folder findInBoxOfActor(int actorId) {
		Folder folder = folderRepository.foldersInBoxOfActor(actorId);
		Assert.notNull(folder);
		return folder;
	}
	
	public Folder findOutBoxOfActor(int actorId) {
		Folder folder = folderRepository.foldersOutBoxOfActor(actorId);
		Assert.notNull(folder);
		return folder;
	}
	
	public void generateSystemFolders(Actor actor){
		Assert.notNull(actor);
		Collection<Folder> folders = new ArrayList<Folder>();
		Folder inBox = new Folder();
		Folder outBox = new Folder();
		Folder trashBox = new Folder();
		Folder spamBox = new Folder();
		inBox.setActor(actor);
		outBox.setActor(actor);
		trashBox.setActor(actor);
		spamBox.setActor(actor);
		inBox.setMessages(new ArrayList<Message>());
		outBox.setMessages(new ArrayList<Message>());
		trashBox.setMessages(new ArrayList<Message>());
		spamBox.setMessages(new ArrayList<Message>());
		inBox.setName("In box");
		outBox.setName("Out box");
		trashBox.setName("Trash box");
		spamBox.setName("Spam box");
		inBox.setSystemFolder(true);
		outBox.setSystemFolder(true);
		trashBox.setSystemFolder(true);
		spamBox.setSystemFolder(true);
		folders.add(inBox);
		folders.add(outBox);
		folders.add(trashBox);
		folders.add(spamBox);
		actor.setFolders(folders);
	}
	
	public Folder findSpamBoxOfActor(int actorId){
		return folderRepository.foldersSpamBoxOfActor(actorId);
	}

	public Folder reconstruct(FolderForm folderForm, int folderId) {
		Folder result;
		if(folderId==0){
			result = create();
			result.setSystemFolder(false);
		}else{
			result = findOne(folderId);
			Assert.isTrue(!result.getSystemFolder());
		}
		Assert.notNull(result);
		result.setName(folderForm.getName());
		return result;
	}
	
}
