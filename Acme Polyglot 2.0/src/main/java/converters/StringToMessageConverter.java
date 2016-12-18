package converters;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import repositories.MessageRepository;
import domain.Message;

@Component
@Transactional
public class StringToMessageConverter implements Converter<String, Message> {
	
	@Autowired MessageRepository repo;

	public Message convert(String source) {
		try {
			if(source.equals("")) {
				return null;
			} else {
				return repo.findOne(Integer.valueOf(source));
			}
		} catch(Throwable thrown) {
			throw new IllegalArgumentException(thrown);
		}
	}

}
