package services;

import java.util.Collection;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;




import domain.JoinExchange;

import repositories.JoinExchangeRepository;




@Service
@Transactional
public class JoinExchangeService {

	// Managed repository ------------------------------------------------
	@Autowired
	private JoinExchangeRepository joinExchangeRepository;
	
	

	
	
	
	// Simple CRUD methods ------------------------------------
	public Collection<JoinExchange> findAll(){
		Collection<JoinExchange> result;
		result = joinExchangeRepository.findAll();
		return result;
	}
	
	public JoinExchange findOne(int id){
		JoinExchange result;
		result = joinExchangeRepository.findOne(id);
		return result;
	}
	
	public JoinExchange create(){
		JoinExchange result = new JoinExchange();
	
		
		return result;
	}
	
	public void save(JoinExchange joinExchange){
		joinExchangeRepository.saveAndFlush(joinExchange);
	}
	
	
	// Other bussiness methods ----------------------------
	

	public void remove(int joinExchangeId){
		JoinExchange joinExchange = joinExchangeRepository.findOne(joinExchangeId);
		joinExchangeRepository.delete(joinExchange);
	}

	public void deleteAllJoinExchange(Collection<JoinExchange> joinExchange) {
		joinExchangeRepository.deleteInBatch(joinExchange);
		
	}
	
	

	
	
	public void delete(JoinExchange joinExchange){
		joinExchangeRepository.delete(joinExchange);
	}
	

	
	
}