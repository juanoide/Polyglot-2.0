package converters;

import javax.transaction.Transactional;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import repositories.ExpectedTalkRepository;

import domain.ExpectedTalk;

@Component
@Transactional
public class StringToExpectedTalkConverter  implements Converter<String, ExpectedTalk>{
	@Autowired
	private ExpectedTalkRepository expectedTalkRepository;
	
	@Override
	public ExpectedTalk convert(String text) {
		ExpectedTalk result;
		int id;
		try{
			if(StringUtils.isEmpty(text))
				result=null;
			else{
				id=Integer.valueOf(text);
				result=expectedTalkRepository.findOne(id);
			}
		}catch (Throwable oops) {
			throw new IllegalArgumentException(oops);
		}
		
		return result;
	}
}
