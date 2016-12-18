package converters;

import javax.transaction.Transactional;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import repositories.JoinExchangeRepository;

import domain.JoinExchange;

@Component
@Transactional
public class StringToJoinExchangeConverter  implements Converter<String, JoinExchange>{
	@Autowired
	private JoinExchangeRepository joinExchangeRepository;
	
	@Override
	public JoinExchange convert(String text) {
		JoinExchange result;
		int id;
		try{
			if(StringUtils.isEmpty(text))
				result=null;
			else{
				id=Integer.valueOf(text);
				result=joinExchangeRepository.findOne(id);
			}
		}catch (Throwable oops) {
			throw new IllegalArgumentException(oops);
		}
		
		return result;
	}
}
