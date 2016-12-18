package converters;
import javax.transaction.Transactional;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import repositories.AgentRepository;

import domain.Agent;

@Component
@Transactional
public class AgentToStringConverter implements Converter<String, Agent>{
	@Autowired
	private AgentRepository agentRepository;
	
	@Override
	public Agent convert(String text) {
		Agent result;
		int id;
		try{
			if(StringUtils.isEmpty(text))
				result=null;
			else{
				id=Integer.valueOf(text);
				result=agentRepository.findOne(id);
			}
		}catch (Throwable oops) {
			throw new IllegalArgumentException(oops);
		}
		
		return result;
	}
}