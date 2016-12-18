package converters;

import javax.transaction.Transactional;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import repositories.LanguageExchangeRepository;

import domain.LanguageExchange;

@Component
@Transactional
public class StringToLanguageExchangeConverter  implements Converter<String, LanguageExchange>{
	@Autowired
	private LanguageExchangeRepository languageExchangeRepository;
	
	@Override
	public LanguageExchange convert(String text) {
		LanguageExchange result;
		int id;
		try{
			if(StringUtils.isEmpty(text))
				result=null;
			else{
				id=Integer.valueOf(text);
				result=languageExchangeRepository.findOne(id);
			}
		}catch (Throwable oops) {
			throw new IllegalArgumentException(oops);
		}
		
		return result;
	}
}
