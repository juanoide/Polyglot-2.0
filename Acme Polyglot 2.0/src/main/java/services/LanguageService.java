package services;

import java.util.ArrayList;
import java.util.Collection;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;




import domain.Administrator;
import domain.Description;
import domain.ExpectedTalk;
import domain.Language;
import forms.LanguageEditForm;
import forms.LanguageForm;

import repositories.LanguageRepository;
import security.LoginService;
import security.UserAccount;





@Service
@Transactional
public class LanguageService {

	// Managed repository ------------------------------------------------
	@Autowired
	private LanguageRepository languageRepository;
	
	
	// Supporting Service -------------------------------------------------

	@Autowired
	private AdministratorService administratorService;
	
	@Autowired
	private DescriptionService descriptionService;
	
	
	// Simple CRUD methods ------------------------------------
	public Collection<Language> findAll(){
		Collection<Language> result;
		result = languageRepository.findAll();
		return result;
	}
	
	public Language findOne(int id){
		Language result;
		result = languageRepository.findOne(id);
		return result;
	}
	
	// Other bussiness methods ----------------------------
	
	

public Language reconstructEdit(LanguageEditForm languageForm, int languageid){
		
		Assert.isTrue(LoginService.getPrincipal().containsAuthority("ADMIN"));
		Assert.isTrue(languageForm.getCode()!=null);
	
		Language result = languageRepository.findOne(languageid);

	
		
	
		result.setCode(languageForm.getCode());
	
	
		

		
		return result;
		
		
	}
	
	public LanguageEditForm constructForm(Language language) {
		Assert.notNull(language);
		LanguageEditForm lform = new LanguageEditForm();

		
	
		
		lform.setCode(language.getCode());
	
		
		return lform;
	}
	
	
	
	
public Language reconstruct(LanguageForm languageForm){
		
		Assert.isTrue(LoginService.getPrincipal().containsAuthority("ADMIN"));
		Assert.isTrue(languageForm.getDescriptionLanguage()!=null);
		Assert.isTrue(languageForm.getCode()!=null);
		Language result= create();
		
		result.setCode(languageForm.getCode());
		
		//description class--->
		
		
		Description des = descriptionService.create();
		
		des.setTitle(languageForm.getTitle());
		des.setText(languageForm.getText());
		
		//links sin parsear, vamos a parsear para separarlo por comas.
		String linksWithoutParsing = languageForm.getLinks();
		
		String[] tokens= linksWithoutParsing.split(",");
		Collection<String> links = new ArrayList<String>();
		for(String l:tokens){
			links.add(l);
	
		}
				
		des.setLinks(links);
		//tag sin parsear, vamos a parsear para separarlo por comas.
		String tagWithoutParsing = languageForm.getTag();
		
		String[] tokens2= tagWithoutParsing.split(",");
		Collection<String> tag = new ArrayList<String>();
		for(String l:tokens2){
			tag.add(l);
	
		}
		
		des.setTag(tag);
		
		des.setLanguage(languageForm.getDescriptionLanguage());
		
	
		
		
		
		result.getDescriptionsOwners().add(des);
		//Fin de description<----
		
		
		
		return result;
		
		
	}
	
	public Language searchLanguageForCode(String code){

		
		Language result = languageRepository.searchLanguageForCode(code);
		return result;
		
		
	}
	
	

	public void remove(int languageId){
		Language language = languageRepository.findOne(languageId);
		languageRepository.delete(language);
	}

	public void deleteAllLanguage(Collection<Language> language) {
		languageRepository.deleteInBatch(language);
		
	}
	
	
	public void save(Language language){
		Assert.notNull(language);
		languageRepository.saveAndFlush(language);
	}
	
	public void saveModified(Language language) {
		Assert.notNull(language);
		
		Assert.isTrue(LoginService.getPrincipal().containsAuthority("ADMIN"));
		
		for(Description des: language.getDescriptions()){
			Assert.notNull(des);
		}

		languageRepository.saveAndFlush(language);
	}
	
	public Language create(){
		Language result = new Language();
		UserAccount loginNow = LoginService.getPrincipal();
		administratorService.isAdmin();
	
		 Collection<ExpectedTalk> expectedTalks = new ArrayList<ExpectedTalk>();	
		 Collection<Description> descriptions= new ArrayList<Description>();
		 Collection<Description> descriptionsOwners= new ArrayList<Description>();
		 
		 Administrator p = administratorService.findByUserAccount(loginNow);
		 
		 result.setAdministrator(p);
		 result.setExpectedTalks(expectedTalks);
		 result.setDescriptions(descriptions);
		 result.setDescriptionsOwners(descriptionsOwners);
		
		return result;
	}
	
	public void delete(Language languageId){
		Assert.notNull(languageId);
		Assert.isTrue(LoginService.getPrincipal().containsAuthority("ADMIN"));
		Assert.isTrue(languageId.getExpectedTalks().size()==0);
		Assert.isTrue(languageId.getDescriptions().size()==0);
		Assert.isTrue(languageId.getMessages().size()==0);
		
		languageRepository.delete(languageId);
	}
	
	public boolean NoExist(LanguageForm languageForm){
		boolean result=true;
		
		Collection<Language> languages = languageRepository.findAll();
		
		for(Language l: languages){
			if(l.getCode().equals(languageForm.getCode())){
				result=false;
			}
		}
		
		
		return result;
		
		
	}
	
	public boolean NoExist(LanguageEditForm languageForm){
		boolean result=true;
		
		Collection<Language> languages = languageRepository.findAll();
		
		for(Language l: languages){
			if(l.getCode().equals(languageForm.getCode())){
				result=false;
			}
		}
		
		
		return result;
		
		
	}
	
	public boolean NolastOneDescription (Language language){
		
		boolean result=false;
		
		if(language.getDescriptionsOwners().size()>1){
			result=true;
		}
		
		return result;
	}

	

	
	
}