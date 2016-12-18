package services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;


import domain.JoinExchange;
import domain.LanguageExchange;
import domain.Polyglot;
import domain.Search;
import forms.PolyglotForm;

import repositories.PolyglotRepository;
import security.Authority;
import security.LoginService;
import security.UserAccount;

@Transactional
@Service
public class PolyglotService {
	//	Managed repository -----------------------------------------
	@Autowired
	private PolyglotRepository polyglotRepository;
	
	
	//	Supporting services ----------------------------------------
	@Autowired
	private FolderService folderService;
	//	Constructor ------------------------------------------------
	//	Simple CRUD methods ----------------------------------------
	public Polyglot create(){
		Polyglot result = new Polyglot();
		
		UserAccount userAccount = new UserAccount();
		List<Authority> authorities= new ArrayList<Authority>();
		Authority a=new Authority();
		a.setAuthority(Authority.POLYGLOT);
		authorities.add(a);
		userAccount.setAuthorities(authorities);
		
		result.setUserAccount(userAccount);
		

		
		Collection<LanguageExchange> languageExchanges = new ArrayList<LanguageExchange>();
		Collection<JoinExchange> joinExchanges =new ArrayList<JoinExchange>();
		Collection<Search> searchs =new ArrayList<Search>();
		result.setOrganiseExchanges(languageExchanges);
		result.setJoinExchanges(joinExchanges);
		result.setSearchs(searchs);

		
		return result;
	}
	
	public Collection<Polyglot> findAll(){
		Collection<Polyglot> result;
		result = polyglotRepository.findAll();
		return result;
	}
	
	public Polyglot findOne(int polyglotId){
		Polyglot result = polyglotRepository.findOne(polyglotId);
		return result;
	}
	public void save(Polyglot polyglot){
		Assert.notNull(polyglot);
		polyglotRepository.saveAndFlush(polyglot);
	}
	//	Other business methods -------------------------------------
	public Polyglot findByUserAccount(UserAccount account){
		Polyglot result = polyglotRepository.findByUserAccount(account);
		return result;
	}
	
	public Polyglot findByUserAccountUsername(String account){
		Polyglot result = polyglotRepository.findByUserAccountUserName(account);
		return result;
	}
	
	public void isPolyglot() {
		UserAccount account=LoginService.getPrincipal();
		Collection<Authority> authorities= account.getAuthorities();
		Boolean res=false;
		for(Authority a:authorities){
			if(a.getAuthority().equals("POLYGLOT")) res=true;
		}
		Assert.isTrue(res);
	}
	

	
	public Polyglot findByPrincipal() {

		Polyglot result;
		UserAccount userAccount;
		userAccount = LoginService.getPrincipal();
		result = findByUserAccount(userAccount);

		return result;

	}
	
	public Polyglot reconstruct(PolyglotForm polyglotForm){
		Assert.isTrue(polyglotForm.getPassword().equals(polyglotForm.getRepeatPassword()));
		Assert.isTrue(polyglotForm.getValid());
		Polyglot result = create();
		folderService.generateSystemFolders(result);
		UserAccount userAccount=result.getUserAccount();
		userAccount.setUsername(polyglotForm.getUsername());
		userAccount.setActive(true);
		Md5PasswordEncoder encoder;		
		encoder= new Md5PasswordEncoder();
		String password=encoder.encodePassword(polyglotForm.getPassword(), null);
		userAccount.setPassword(password);
		
		result.setUserAccount(userAccount);
		
		result.setName(polyglotForm.getName());
		result.setSurname(polyglotForm.getSurname());
		result.setEmail(polyglotForm.getEmail());
		result.setPhone(polyglotForm.getPhoneNumber());
		
	
		return result;
	}
	
	
}
