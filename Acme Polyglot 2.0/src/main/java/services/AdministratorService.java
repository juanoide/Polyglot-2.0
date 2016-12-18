package services;

import java.util.ArrayList;
import java.util.Collection;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import domain.Administrator;
import domain.Language;

import repositories.AdministratorRepository;
import security.Authority;
import security.LoginService;
import security.UserAccount;

@Transactional
@Service
public class AdministratorService {
	//	Managed repository -----------------------------------------
	@Autowired
	private AdministratorRepository administratorRepository;
	//	Supporting services ----------------------------------------
	//	Constructor ------------------------------------------------
	//	Simple CRUD methods ----------------------------------------
	public Administrator create(){
		Administrator result = new Administrator();
		
		UserAccount userAccount=new UserAccount();
		
		Authority authority=new Authority();
		authority.setAuthority(Authority.ADMIN);
		Collection<Authority> authorities = new ArrayList<Authority>();
		authorities.add(authority);
		userAccount.setAuthorities(authorities);
		result.setUserAccount(userAccount);
		
		Collection<Language> languageExchanges = new ArrayList<Language>();
		

		result.setLanguages(languageExchanges);
		
		return result;
	}
	
	public Administrator findOne(int administratorId){
		Administrator result;
		result=administratorRepository.findOne(administratorId);
		return result;
	}
	
	public Collection<Administrator> findAll(){
		Collection<Administrator> result;
		result=administratorRepository.findAll();
		return result;
	}
	
	public Collection<Integer> minMaxAvgMessageForFolder(){
		Collection<Integer> result;
		
		result=administratorRepository.minMaxAvgMessageForFolder();
		return result;
	}
	
	public Integer minMessageForFolder(){
		Integer result;
		
		result=administratorRepository.minMessageForFolder();
		return result;
	}
	
	public Integer maxMessageForFolder(){
		Integer result;
		
		result=administratorRepository.maxMessageForFolder();
		return result;
	}
	
	public Double avgMessageForFolder(){
		Double result;
		
		result=administratorRepository.avgMessageForFolder();
		return result;
	}
	
	public void save(Administrator administrator){
		isAdmin();
		administratorRepository.saveAndFlush(administrator);
	}
	
	public void delete(Administrator administrator){
		Assert.notNull(administrator);
		administratorRepository.delete(administrator);
	} 
	// ----------------- Other business methods ------------------
	public Administrator findByUserAccount(UserAccount account){
		Administrator result = administratorRepository.findByUserAccount(account);
		return result;
	}
	
	public void isAdmin() {
		UserAccount account=LoginService.getPrincipal();
		Collection<Authority> authorities= account.getAuthorities();
		Boolean res=false;
		for(Authority a:authorities){
			if(a.getAuthority().equals("ADMIN")) res=true;
		}
		Assert.isTrue(res);
	}
	
	public Administrator findByPrincipal() {

		Administrator result;
		UserAccount userAccount;
		userAccount = LoginService.getPrincipal();
		result = findByUserAccount(userAccount);

		return result;

	}
	
	public Double averageLanguageExchangeSponsorshipPolyglot(int polyglotid){
		
		Double result = administratorRepository.averageLanguageExchangeSponsorshipPolyglot(polyglotid);
		
		return result;
	}
	
}
