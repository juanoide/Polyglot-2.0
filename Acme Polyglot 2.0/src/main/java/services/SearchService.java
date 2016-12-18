package services;


import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;




import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;



import domain.Actor;
import domain.Search;


import repositories.SearchRepository;






@Service
@Transactional
public class SearchService {

	// Managed repository ------------------------------------------------
	@Autowired
	private SearchRepository searchRepository;
	
	//Service
	
	
	@Autowired
	private ActorService actorService;

	
	
	
	// Simple CRUD methods ------------------------------------
	public Collection<Search> findAll(){
		Collection<Search> result;
		result = searchRepository.findAll();
		return result;
	}
	
	public Search findOne(int id){
		Search result;
		result = searchRepository.findOne(id);
		return result;
	}
	
	public Search findOneNoId(Search des){
		Search result;
		result = searchRepository.findBySearch(des);
		return result;
	}
	
	
	public Search create(){
		Search result = new Search();
	
		 
		 
		return result;
	}
	
	
	// Other bussiness methods ----------------------------
	

	public void remove(int searchId){
		Search search = searchRepository.findOne(searchId);
		searchRepository.delete(search);
	}

	public void deleteAllSearch(Collection<Search> searchs) {
		searchRepository.deleteInBatch(searchs);
		
	}
	
	public Collection<Search> keywordsSums(){
		//Collection<Search> searchs= new ArrayList<Search>();
//		Object[]mapSearchs=searchRepository.keywordsSums();
		/*
		for(String name: mapSearchs.keySet()){
			Search search = new Search();
			search.setName(name);
			search.setCount(mapSearchs.get(name));
			searchs.add(search);
		}
		*/
		//Collection<Search> searchs= searchRepository.findAll();
		//Collection<Search> searchs2= new ArrayList<Search>();
		
		Map<String, Search> map = new HashMap<String, Search>();
		for(Search s : searchRepository.findAll()){
		    Search instance = new Search();
		    if(map.containsKey(s.getName())){
		        instance=map.get(s.getName());
		        instance.setCount(instance.getCount()+s.getCount());
		        }
		    
		    
		    else{
		    	instance.setName(s.getName());
		    	instance.setCount(s.getCount());
		    	map.put(instance.getName(), instance);
		    }
		    
		    
		    
		}
		
		Collection<Search> searchs2= new ArrayList<Search>();
		if(map.values()!=null){
		searchs2 =map.values();
		}
		
		return searchs2;
	}
	
	
	public void save(Search search){
		Assert.notNull(search);
	
		 searchRepository.saveAndFlush(search);
	}
	
	public void saveModified(Search search){
		Assert.notNull(search);
		 searchRepository.saveAndFlush(search);
	}
	

	public void delete(Search searchId){
		searchRepository.delete(searchId);
	}
	
	
	public void createWithEntrante(String cadena){
			//TODO
		Boolean yaexiste =false;
		Actor actor =actorService.findByPrincipalNullable();
		Integer count=1;
		if(actor!=null){
		
			if(actor.getSearchs().isEmpty()==false){
				
			
			for(Search searchAux: actor.getSearchs()){
				if(searchAux.getName().equals(cadena)){
				Search searchAux2=	searchRepository.findOne(searchAux.getId());
				searchAux2.setCount(searchAux2.getCount()+count);
				this.save(searchAux2);
				yaexiste =true;
				break;
				}
			}
			}
			
			
			if(yaexiste==false){
				Search result =new Search();
				result.setCount(count);
				result.setActor(actor);
				result.setName(cadena);
				this.save(result);
				
			}
					
		}
	}

	
	
	

	
	
}