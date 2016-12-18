package services;

import java.util.Collection;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;




import domain.ExpectedTalk;

import repositories.ExpectedTalkRepository;




@Service
@Transactional
public class ExpectedTalkService {

	// Managed repository ------------------------------------------------
	@Autowired
	private ExpectedTalkRepository expectedTalkRepository;
	
	

	
	
	
	// Simple CRUD methods ------------------------------------
	public Collection<ExpectedTalk> findAll(){
		Collection<ExpectedTalk> result;
		result = expectedTalkRepository.findAll();
		return result;
	}
	
	public ExpectedTalk findOne(int id){
		ExpectedTalk result;
		result = expectedTalkRepository.findOne(id);
		return result;
	}
	
	public ExpectedTalk create(){
		ExpectedTalk result = new ExpectedTalk();
	
		
		return result;
	}
	
	public ExpectedTalk save(ExpectedTalk expectedTalk){
		return expectedTalkRepository.saveAndFlush(expectedTalk);
	}
	
	public ExpectedTalk saveModified(ExpectedTalk expectedTalk){
		
		return expectedTalkRepository.save(expectedTalk);
	}
	
	
	
	// Other bussiness methods ----------------------------
	

	public void remove(int expectedTalkId){
		ExpectedTalk expectedTalk = expectedTalkRepository.findOne(expectedTalkId);
		expectedTalkRepository.delete(expectedTalk);
	}

	public void deleteAllExpectedTalk(Collection<ExpectedTalk> expectedTalk) {
		expectedTalkRepository.deleteInBatch(expectedTalk);
		
	}
	
	

	
	
	public void delete(ExpectedTalk expectedTalkId){
		expectedTalkRepository.delete(expectedTalkId);
	}
	

	
	
}