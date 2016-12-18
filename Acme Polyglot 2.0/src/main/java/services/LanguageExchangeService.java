package services;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;







import domain.Description;
import domain.ExpectedTalk;
import domain.Folder;
import domain.JoinExchange;
import domain.Language;
import domain.LanguageExchange;



import domain.Polyglot;

import forms.LanguageExchangeEditForm;
import forms.LanguageExchangeForm;

import repositories.LanguageExchangeRepository;
import security.LoginService;
import security.UserAccount;




@Service
@Transactional
public class LanguageExchangeService {

	// Managed repository ------------------------------------------------
	@Autowired
	private LanguageExchangeRepository languageExchangeRepository;
	
	
	// Supporting Service -------------------------------------------------

	@Autowired
	private PolyglotService polyglotService;
	
	@Autowired
	private DescriptionService descriptionService;
	
	@Autowired
	private ExpectedTalkService expectedTalkService;

	
	@Autowired
	private JoinExchangeService joinExchangeService;
		
	@Autowired
	private FolderService folderService;
	

	
	// Simple CRUD methods ------------------------------------
	public Collection<LanguageExchange> findAll(){
		Collection<LanguageExchange> result;
		result = languageExchangeRepository.findAll();
		return result;
	}
	
	public Collection<LanguageExchange> findAllNoCancel(){
		Collection<LanguageExchange> result;
		result = languageExchangeRepository.languageExchangeNoCancel();		
		return result;
	}
	
	public Collection<LanguageExchange> findAllPolyglotLogin(){
		Polyglot pol;
		UserAccount loginNow = LoginService.getPrincipal();
		polyglotService.isPolyglot();
		pol=polyglotService.findByUserAccount(loginNow);
		Collection<LanguageExchange> result;
		result = languageExchangeRepository.findAllPolyglotLogin(pol.getId());		
		return result;
	}
	
	public Collection<LanguageExchange> findAllPolyglotLogin(int polyglotId){

		Collection<LanguageExchange> result;
		result = languageExchangeRepository.findAllPolyglotLogin(polyglotId);		
		return result;
	}
	
	
	public LanguageExchange findOne(int id){
		LanguageExchange result;
		result = languageExchangeRepository.findOne(id);
		return result;
	}
	
	public LanguageExchange create(){
		LanguageExchange result = new LanguageExchange();
		UserAccount loginNow = LoginService.getPrincipal();
		polyglotService.isPolyglot();
			
		 Collection<ExpectedTalk> expectedTalks = new ArrayList<ExpectedTalk>();	
		 Collection<JoinExchange> joinExchanges= new ArrayList<JoinExchange>();	
		 Collection<Description> descriptions= new ArrayList<Description>();
		 Collection<Folder> folders= new ArrayList<Folder>();
		
		 Polyglot p = polyglotService.findByUserAccount(loginNow);
		 
		 result.setPolyglotOrganise(p);
		 result.setExpectedTalks(expectedTalks);
		 result.setJoinExchanges(joinExchanges);
		 result.setDescriptions(descriptions);
		 result.setFolders(folders);
		 result.setCancel(false);
		
		return result;
	}
	
	public void save(LanguageExchange languageExchange){
		Assert.notNull(languageExchange);
		Assert.isTrue(LoginService.getPrincipal().containsAuthority("POLYGLOT"));
		Assert.isTrue(languageExchange.getPolyglotOrganise()==polyglotService.findByPrincipal());
	LanguageExchange guardado=	languageExchangeRepository.saveAndFlush(languageExchange);
	Collection<ExpectedTalk> collectionExpectedTalk=new ArrayList<ExpectedTalk>();
	for(ExpectedTalk a: languageExchange.getExpectedTalks()){
		ExpectedTalk aux3 = expectedTalkService.create();

		aux3.setLanguage(a.getLanguage());
		aux3.setLanguageExchange(guardado);
		
	

		aux3 = expectedTalkService.save(aux3);
		collectionExpectedTalk.add(aux3);
	}
		
		
		Folder folder = folderService.create();
		
		folder.setLanguageExchange(guardado);
		folder.setSystemFolder(true);
	
	for(Description des: languageExchange.getDescriptions()){
		folder.setName(des.getTitle());
		break;
	}
	Collection<Folder> collectionFolders= new ArrayList<Folder>();
	collectionFolders.add(folder);
	guardado.setFolders(collectionFolders);
	
	
	
	
	
		
		

	
	guardado.setExpectedTalks(collectionExpectedTalk);
	
	
	languageExchangeRepository.saveAndFlush(guardado);
	folderService.save(folder);
	}
	

	
	
	
	public void saveModified(LanguageExchange languageExchange) {
		Assert.notNull(languageExchange);
		Assert.isTrue(LoginService.getPrincipal().containsAuthority("POLYGLOT"));
		Assert.isTrue(languageExchange.getPolyglotOrganise()==polyglotService.findByPrincipal());
		
		for(Description des: languageExchange.getDescriptions()){
			Assert.notNull(des);
		}
		
		languageExchangeRepository.saveAndFlush(languageExchange);
		
	}
	
	// Other bussiness methods ----------------------------
	
	

	public LanguageExchange reconstruct(LanguageExchangeForm languageExchangeForm){
		
		Assert.isTrue(LoginService.getPrincipal().containsAuthority("POLYGLOT"));
		Assert.isTrue(languageExchangeForm.getLanguages()!=null);
		Assert.isTrue(languageExchangeForm.getDescriptionLanguage()!=null);
		LanguageExchange result= create();
		
		result.setDate(languageExchangeForm.getDate());
		result.setPlace(languageExchangeForm.getPlace());
		
		//description class--->
		
		
		Description des = descriptionService.create();
		
		des.setTitle(languageExchangeForm.getTitle());
		des.setText(languageExchangeForm.getText());
		
		//links sin parsear, vamos a parsear para separarlo por comas.
		String linksWithoutParsing = languageExchangeForm.getLinks();
		
		String[] tokens= linksWithoutParsing.split(",");
		Collection<String> links = new ArrayList<String>();
		for(String l:tokens){
			links.add(l);
	
		}
				
		des.setLinks(links);
		//tag sin parsear, vamos a parsear para separarlo por comas.
		String tagWithoutParsing = languageExchangeForm.getTag();
		
		String[] tokens2= tagWithoutParsing.split(",");
		Collection<String> tag = new ArrayList<String>();
		for(String l:tokens2){
			tag.add(l);
	
		}
		
		des.setTag(tag);
		
		des.setLanguage(languageExchangeForm.getDescriptionLanguage());
		
	
		
		
		
		result.getDescriptions().add(des);
		//Fin de description<----
		
		//Create de expectedTalkService
	
		Collection<Language> languagesForm = languageExchangeForm.getLanguages();
		Collection<ExpectedTalk> expectedTalksOfLanguageExchange = new ArrayList<ExpectedTalk>();
		
		
		for(Language l:languagesForm){
			
		
		ExpectedTalk exp=	expectedTalkService.create();
		
		exp.setLanguage(l);
		exp.setLanguageExchange(result);
	
		expectedTalksOfLanguageExchange.add(exp);
			
		}
		
	
	
		
		result.setExpectedTalks(expectedTalksOfLanguageExchange);
		

		
		return result;
		
		
	}
	
	public LanguageExchangeEditForm constructForm(LanguageExchange languageExchange) {
		Assert.notNull(languageExchange);
		LanguageExchangeEditForm lform = new LanguageExchangeEditForm();

		Collection<Language> languages= new ArrayList<Language>();
		for(ExpectedTalk l:languageExchange.getExpectedTalks()){
	
						
			languages.add(l.getLanguage());
			
				
			}
		
		lform.setDate(languageExchange.getDate());
		lform.setLanguages(languages);
		lform.setPlace(languageExchange.getPlace());
		
		return lform;
	}
	
	
	
public LanguageExchange reconstructEdit(LanguageExchangeEditForm languageExchangeForm, int languageExchangeid){
		Assert.isTrue(LoginService.getPrincipal().containsAuthority("POLYGLOT"));
		Assert.isTrue(languageExchangeForm.getDate()!=null);
		
	
		LanguageExchange result = languageExchangeRepository.findOne(languageExchangeid);

		Assert.isTrue(result!=null);
		
	
		result.setDate(languageExchangeForm.getDate());
		result.setPlace(languageExchangeForm.getPlace());
	
		//Create de expectedTalkService
	
		Collection<Language> languagesForm = languageExchangeForm.getLanguages();

		Collection<Language> languagesNoExist =new ArrayList<Language>();
		Collection<ExpectedTalk> expectedTalkEliminate =new ArrayList<ExpectedTalk>();
		
		Collection<ExpectedTalk> expectedTalksOfLanguageExchange = result.getExpectedTalks();
		boolean languageExpectedNew = false;
		boolean languageExpectedEliminate = true;
		
		for(Language aux: languagesForm){
			for(ExpectedTalk expectedTalk: expectedTalksOfLanguageExchange){
				if((expectedTalk.getLanguage()==aux)){
					languageExpectedNew = true;
				}
			}
			if(languageExpectedNew==false){
					languagesNoExist.add(aux);
			}
			languageExpectedNew = false;
		}
		
		
		
		
		
		for(Language l:languagesNoExist){
	
			
		
		ExpectedTalk exp=	expectedTalkService.create();
		
		exp.setLanguage(l);
		exp.setLanguageExchange(result);
		expectedTalkService.save(exp);
		result.getExpectedTalks().add(exp);
		
		}
		
		for(ExpectedTalk expectedTalkAux: expectedTalksOfLanguageExchange){
			for(Language aux: languagesForm){
				if((expectedTalkAux.getLanguage()==aux)){
					languageExpectedEliminate = false;
				}
			}
			if(languageExpectedEliminate==true){
				System.out.println(expectedTalkAux.getId());
				expectedTalkEliminate.add(expectedTalkAux);
			
			}
			languageExpectedEliminate = true;
		}
		
		for(ExpectedTalk expc:expectedTalkEliminate){
			System.out.println("entra aqui cuantas veces");
			expectedTalkService.delete(expc);
			result.getExpectedTalks().remove(expc);
			
			}
		

		
		return result;
		
		
	}
	
	
	public void JoinToLanguageExchange(LanguageExchange lexc){
		Assert.isTrue(LoginService.getPrincipal().containsAuthority("POLYGLOT"));
		Assert.notNull(lexc);
		Polyglot c;
		
		UserAccount loginNow = LoginService.getPrincipal();
		polyglotService.isPolyglot();
		c=polyglotService.findByUserAccount(loginNow);
		
		JoinExchange join= joinExchangeService.create();
		
		join.setPolyglot(c);
		join.setLanguageExchange(lexc);
		
		joinExchangeService.save(join);
		if(lexc.getPolyglotOrganise().getId()!=c.getId()){
			
		
		
			Folder folder = folderService.create();
		
			folder.setLanguageExchange(lexc);
			folder.setSystemFolder(true);
		for(Description des: lexc.getDescriptions()){
			folder.setName(des.getTitle());
			break;
		}
		folderService.save(folder);
		
		}	
		
		
		
	}
	
	public void UnJoinToLanguageExchange(LanguageExchange lexc){
		Polyglot pol;
		
		UserAccount loginNow = LoginService.getPrincipal();
		polyglotService.isPolyglot();
		pol=polyglotService.findByUserAccount(loginNow);
		for(JoinExchange joinExchangePoly: pol.getJoinExchanges()){
			for(JoinExchange joinExchangeLexc: lexc.getJoinExchanges()){
				if(joinExchangePoly==joinExchangeLexc){
					joinExchangeService.delete(joinExchangePoly);	

				}
			}
		}
		if(lexc.getPolyglotOrganise().getId()!=pol.getId()){
		Folder folder = folderService.findLanguageExchangefolder(lexc.getId(), pol.getId());
		folder.setSystemFolder(false);
	
	folderService.deleteSystemFolder(folder);
		}
	}
	
	

	
	public void remove(int languageExchangeId){
		LanguageExchange languageExchange = languageExchangeRepository.findOne(languageExchangeId);
		languageExchangeRepository.delete(languageExchange);
	}


	
	
	public void delete(LanguageExchange languageExchange){
		Assert.notNull(languageExchange);
		Assert.isTrue(LoginService.getPrincipal().containsAuthority("POLYGLOT"));
		Assert.isTrue(languageExchange.getPolyglotOrganise()==polyglotService.findByPrincipal());
		languageExchange.setCancel(true);
		
	}
	
	
	
	public Collection<LanguageExchange> listExchangePastOrganised3Months(){
		Collection<LanguageExchange> result=new ArrayList<LanguageExchange>();
		   Calendar fecha1 = Calendar.getInstance();
			Calendar fecha2 = Calendar.getInstance();
			fecha2.add(Calendar.MONTH, -3);
			//fecha2.setTimeInMillis(fecha.getTime());
			Date date3months=fecha2.getTime();
			Date dateActual= fecha1.getTime();
		for(LanguageExchange langExcAux:languageExchangeRepository.findAll()){
			if(langExcAux.getDate().before(dateActual) && langExcAux.getDate().after(date3months)){
				result.add(langExcAux);
			}
			
		}
		
		
		
		//result = languageExchangeRepository.listExchangePastOrganised3Months();
		return result;
	}
	
	public Collection<LanguageExchange> listExchangeFutureOrganised3Months(){
		Collection<LanguageExchange> result=new ArrayList<LanguageExchange>();
		
		   Calendar fecha1 = Calendar.getInstance();
					Calendar fecha2 = Calendar.getInstance();
					fecha2.add(Calendar.MONTH, 3);
					//fecha2.setTimeInMillis(fecha.getTime());
					Date date3months=fecha2.getTime();
					Date dateActual= fecha1.getTime();
				for(LanguageExchange langExcAux:languageExchangeRepository.findAll()){
					if(langExcAux.getDate().after(dateActual) && langExcAux.getDate().before(date3months)){
						result.add(langExcAux);
					}
					
				}
				
		
		
	//	result = languageExchangeRepository.listExchangeFutureOrganised3Months();
		return result;
	}
	
	public Double languageExchangeAvgSponsorizedPerPolyglot(){
		Collection<LanguageExchange> langaugeExchangesSponsorized= new ArrayList<LanguageExchange>();		
		Collection<Polyglot> polyglots= polyglotService.findAll();	
		
		for(LanguageExchange langExcAux:languageExchangeRepository.findAll()){
			if(langExcAux.getSponsorships().size()>0){
				langaugeExchangesSponsorized.add(langExcAux);
			}
			
		}
		
		Double result=(((double) langaugeExchangesSponsorized.size())/(double) (polyglots.size()));
		
		return result;
	}
	
	public Integer languageExchangeMaxSponsorizedPerPolyglot(){
		Collection<Polyglot> polyglots= polyglotService.findAll();
		Integer result=0;
	
		Integer aux=0;
	for(Polyglot pol: polyglots){
		for(LanguageExchange langExcAux: pol.getOrganiseExchanges()){
			if(langExcAux.getSponsorships().size()>0){
				aux++;
			}
			
		}
		if(aux>result){
			result=aux;
		}
		aux=0;
		
	}

		return result;
	}
	
	
	public Integer languageExchangeMinSponsorizedPerPolyglot(){
		Collection<Polyglot> polyglots= polyglotService.findAll();
		Integer result=Integer.MAX_VALUE;
	
		Integer aux=0;
		boolean variable=false;
		boolean nohay=true;
	for(Polyglot pol: polyglots){
		for(LanguageExchange langExcAux: pol.getOrganiseExchanges()){
			if(langExcAux.getSponsorships().size()>0){
				aux++;
				variable=true;
				nohay=false;
			}
			
		}
		if(variable==true){
			
		
		if(aux<result){
			result=aux;
			}
		}
		variable=false;
		aux=0;
		
	}
	if(nohay==true){
		result=0;
	}

		return result;
	}
	
	
	public Collection<LanguageExchange> languageExchangeOfPolyglotLogin(){
		Assert.isTrue(LoginService.getPrincipal().containsAuthority("POLYGLOT"));
		Polyglot pol;
		UserAccount loginNow = LoginService.getPrincipal();
		polyglotService.isPolyglot();
		pol=polyglotService.findByUserAccount(loginNow);
		Collection<LanguageExchange> result;
		result = languageExchangeRepository.languageExchangeOfPolyglotID(pol.getId());
		return result;
	}
	
	public Collection<LanguageExchange> languageExchangeOfPolyglotJoined(int polyglotId){
		Assert.isTrue(LoginService.getPrincipal().containsAuthority("POLYGLOT"));
		Collection<LanguageExchange> result;
		result = languageExchangeRepository.languageExchangeOfPolyglotID(polyglotId);
		return result;
	}
	
	public Collection<LanguageExchange> filterNoCancel(Collection<LanguageExchange> languageExchanges){
		Collection<LanguageExchange> result =new ArrayList<LanguageExchange>();
		
		for(LanguageExchange lexch: languageExchanges){
			if(lexch.getCancel()==false){
				result.add(lexch);
			}
		}
		return result;
	}
	
	public boolean NolastOneDescription (LanguageExchange languageExchange){
	
		boolean result=false;
		
		if(languageExchange.getDescriptions().size()>1){
			result=true;
		}
		
		return result;
	}


	public Collection<LanguageExchange> languageExchangeForKeyword(String keyword){
		
		Collection<LanguageExchange> result= new HashSet<LanguageExchange>();
		
		result.addAll(languageExchangeRepository.languageExchangeForKeyword(keyword));
		
		return result;
		
	}
	
	public Collection<Description> languageDescriptionExchangeForKeyword(int languageId, String keyword){
		
		Collection<Description> result;
		result=languageExchangeRepository.languageExchangeDescriptionForKeyword(languageId, keyword);
		
		//metodo que busqque descriptions por tag y lo añado a result
		
		return result;
		
	}
	
}