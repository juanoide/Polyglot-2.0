package services;

import java.util.Collection;
import java.util.HashSet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.ActorRepository;
import security.Authority;
import security.LoginService;
import security.UserAccount;
import domain.Actor;


@Service
@Transactional
public class ActorService {

	// Managed repository ------------------------------------------------
	@Autowired
	private ActorRepository actorRepository;

	
	
	

	
	// Simple CRUD methods ------------------------------------
	public Collection<Actor> findAll(){
		Collection<Actor> result;
		result = actorRepository.findAll();
		return result;
	}
	
	public Actor findOne(int id){
		Actor result;
		result = actorRepository.findOne(id);
		return result;
	}
	
	// Other bussiness methods ----------------------------
	public Actor findByUserAccountID(int UserAccountID){
		Actor result;
		result = actorRepository.findByUserAccountID(UserAccountID);
		return result;
	}
	
	public Actor findByUsername(String username){
		Actor result;
		result = actorRepository.findByUsername(username);
		return result;
	}
	
	public Actor findByPrincipal(){
		Actor result;
		UserAccount user = LoginService.getPrincipal();
		int userID = user.getId();
		result = findByUserAccountID(userID);
		return result;
	}
	
	public Actor findByPrincipalNullable(){
		Actor result;
		try{
		UserAccount user = LoginService.getPrincipal();
		int userID = user.getId();
		result = findByUserAccountID(userID);
		return result;
		}catch(Exception oops){
			return null;
		}
	}
	
	
	public UserAccount createUserAccount(String authorityString){
		checkAuthority(authorityString);
		UserAccount result;
		Authority authority;
		
		result = new UserAccount();
		authority = new Authority();
		authority.setAuthority(authorityString);
		
		result.setAuthorities(new HashSet<Authority>());
		result.getAuthorities().add(authority);
		
		return result;
	}
	
	public void checkAuthority(String authorityString){
		Authority auth;
		auth = new Authority();
		auth.setAuthority(authorityString);
		
		Collection<Authority> posibleAuths;
		posibleAuths = Authority.listAuthorities();
		Assert.isTrue(posibleAuths.contains(auth));
	}
	
	public void disable(int actorId) {
		Assert.isTrue(LoginService.getPrincipal().containsAuthority("ADMIN"));
		Assert.isTrue(actorId!=0);
		Actor actor = this.findOne(actorId);
		actor.getUserAccount().setActive(false);
		
		actorRepository.save(actor);
	}

	public void enable(int actorId) {
		Assert.isTrue(LoginService.getPrincipal().containsAuthority("ADMIN"));
		Assert.isTrue(actorId!=0);
		Actor actor = this.findOne(actorId);
		actor.getUserAccount().setActive(true);
		
		actorRepository.save(actor);
	}

	
	// Utilities ....
	
	public String encodePassword(String password){
		Md5PasswordEncoder encoder;
		String result;
		if(password == null || "".equals(password)){
			result = null;
		}else{
			encoder = new Md5PasswordEncoder();
			result = encoder.encodePassword(password, null);
		}
		return result;
	}

	public Boolean isLoginSomeone(UserAccount account) {
		Collection<Authority> authorities= account.getAuthorities();
		Boolean res=false;
		for(Authority a:authorities){
			if(a.getAuthority().equals("CONSUMER")||a.getAuthority().equals("CLERK")|| a.getAuthority().equals("ADMINISTRATOR")) res=true;
		}
		return res;
	}

	
}