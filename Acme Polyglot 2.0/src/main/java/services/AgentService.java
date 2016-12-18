package services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;



import domain.Agent;
import domain.Sponsorship;


import repositories.AgentRepository;
import security.Authority;
import security.LoginService;
import security.UserAccount;

@Transactional
@Service
public class AgentService {
	//	Managed repository -----------------------------------------
	@Autowired
	private AgentRepository agentRepository;
	//	Supporting services ----------------------------------------

	//	Constructor ------------------------------------------------
	//	Simple CRUD methods ----------------------------------------
	public Agent create(){
		Agent result = new Agent();
		
		UserAccount userAccount = new UserAccount();
		List<Authority> authorities= new ArrayList<Authority>();
		Authority a=new Authority();
		a.setAuthority(Authority.AGENT);
		authorities.add(a);
		userAccount.setAuthorities(authorities);
		
		result.setUserAccount(userAccount);
		

		
		Collection<Sponsorship> sponsorships = new ArrayList<Sponsorship>();
	

		result.setSponsorships(sponsorships);
		

		
		return result;
	}
	
	public Collection<Agent> findAll(){
		Collection<Agent> result;
		result = agentRepository.findAll();
		return result;
	}
	
	public Agent findOne(int agentId){
		Agent result = agentRepository.findOne(agentId);
		return result;
	}
	public void save(Agent agent){
		Assert.notNull(agent);
		agentRepository.saveAndFlush(agent);
	}
	//	Other business methods -------------------------------------
	public Agent findByUserAccount(UserAccount account){
		Agent result = agentRepository.findByUserAccount(account);
		return result;
	}
	
	public Agent findByUserAccountUsername(String account){
		Agent result = agentRepository.findByUserAccountUserName(account);
		return result;
	}
	
	public void isAgent() {
		UserAccount account=LoginService.getPrincipal();
		Collection<Authority> authorities= account.getAuthorities();
		Boolean res=false;
		for(Authority a:authorities){
			if(a.getAuthority().equals("AGENT")) res=true;
		}
		Assert.isTrue(res);
	}
	

	
	public Agent findByPrincipal() {

		Agent result;
		UserAccount userAccount;
		userAccount = LoginService.getPrincipal();
		result = findByUserAccount(userAccount);

		return result;

	}
	

	
	
}
