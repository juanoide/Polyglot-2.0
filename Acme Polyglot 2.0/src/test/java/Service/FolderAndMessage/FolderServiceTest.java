package Service.FolderAndMessage;



import javax.transaction.Transactional;
import javax.validation.ConstraintViolationException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.util.Assert;

import domain.Folder;

import forms.FolderForm;


import services.ActorService;
import services.FolderService;

import utilities.AbstractTest;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:spring/datasource.xml",
					"classpath:spring/config/packages.xml" })
@Transactional
@TransactionConfiguration(defaultRollback = false)
public class FolderServiceTest  extends AbstractTest {
	
	// Service under test ------------------------
	@Autowired
	private FolderService folderService;
	
	// Supporting services -----------------------
	@Autowired
	private ActorService actorService;

/*
 * Actors can create additional folders, rename, or delete them. actos can flag their message as spam, etc...
 */

	
	// POSITIVE TEST CASE: An agent can create folder
	@Test
	public void createFolderAsAuditorTest() {
		authenticate("agent2");
		int principalId = actorService.findByPrincipal().getId();
		int numFoldersNow = folderService.findFoldersOfActor(principalId).size();
		FolderForm folderFormAux = new FolderForm();
		folderFormAux.setName("New Agent2 Folder for testing its creation");
		Folder folder = folderService.reconstruct(folderFormAux,0);
		folderService.save(folder);
		int numFoldersExpected = folderService.findFoldersOfActor(principalId).size();
		unauthenticate();	
		
		Assert.isTrue(numFoldersNow  + 1 == numFoldersExpected);
	}
	
	// POSITIVE TEST CASE: An agent can edit folder
	@Test
	public void editFolderAsUserTest() {
		authenticate("agent1");
		int principalId = actorService.findByPrincipal().getId();
		FolderForm folderFormAux = new FolderForm();
		folderFormAux.setName("New auditor5 folder for testing its edition");
		Folder folder = folderService.reconstruct(folderFormAux,0);
		folderService.save(folder);
		
		Folder folderToEdit = folderService.nonSystemFoldersOfActor(principalId).iterator().next();
		String actualName = folderToEdit.getName();
		FolderForm folderFormAux2 = new FolderForm();
		folderFormAux2.setName("New folder name");
		Folder folderToSave = folderService.reconstruct(folderFormAux2, folderToEdit.getId());
		folderService.save(folderToSave);
		Folder editedFolder = folderService.nonSystemFoldersOfActor(principalId).iterator().next();
		String expectedName = editedFolder.getName();
		Assert.isTrue(!actualName.equals(expectedName));
		unauthenticate();		
	}
	
	// POSITIVE TEST CASE: An polyglot can delete folder

	@Test
	public void deleteFolderAsAuditorTest() {
		authenticate("polyglot1");
		int principalId = actorService.findByPrincipal().getId();
		FolderForm folderFormAux = new FolderForm();
		folderFormAux.setName("New Polyglot1 Folder for testing its deletion");
		Folder folder = folderService.reconstruct(folderFormAux,0);
		folderService.save(folder);
		int numFoldersNow = folderService.findFoldersOfActor(principalId).size();
		Folder folderToDelete = folderService.nonSystemFoldersOfActor(principalId).iterator().next();
		folderService.delete(folderToDelete);
		int numFoldersExpected = folderService.findFoldersOfActor(principalId).size();
		unauthenticate();	
		Assert.isTrue(numFoldersNow - 1 == numFoldersExpected);
	}
	
	
	
	// NEGATIVE TEST CASE: An agent cannot create folder whithout name

	
	@Test(expected = ConstraintViolationException.class)
	@Rollback(true)
	public void createFolderWithInvalidNameNegativeTest() {
		authenticate("agent2");
		FolderForm folderFormAux = new FolderForm();
		folderFormAux.setName("");
		Folder folder = folderService.reconstruct(folderFormAux,0);
		folderService.save(folder);
		unauthenticate();	
	}



	

}
