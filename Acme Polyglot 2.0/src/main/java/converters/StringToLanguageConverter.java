package converters;

import javax.transaction.Transactional;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import repositories.LanguageRepository;

import domain.Language;

@Component
@Transactional
public class StringToLanguageConverter  implements Converter<String, Language>{
	@Autowired
	private LanguageRepository languageRepository;
	
	@Override
	public Language convert(String text) {
		Language result;
		int id;
		try{
			if(StringUtils.isEmpty(text))
				result=null;
			else{
				id=Integer.valueOf(text);
				result=languageRepository.findOne(id);
			}
		}catch (Throwable oops) {
			throw new IllegalArgumentException(oops);
		}
		
		return result;
	}
}
