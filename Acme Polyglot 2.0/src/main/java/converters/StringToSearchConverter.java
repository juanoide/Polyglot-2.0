package converters;

import javax.transaction.Transactional;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import repositories.SearchRepository;

import domain.Search;

@Component
@Transactional
public class StringToSearchConverter  implements Converter<String, Search>{
	@Autowired
	private SearchRepository searchRepository;
	
	@Override
	public Search convert(String text) {
		Search result;
		int id;
		try{
			if(StringUtils.isEmpty(text))
				result=null;
			else{
				id=Integer.valueOf(text);
				result=searchRepository.findOne(id);
			}
		}catch (Throwable oops) {
			throw new IllegalArgumentException(oops);
		}
		
		return result;
	}
}
