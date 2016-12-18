package converters;

import javax.transaction.Transactional;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import domain.Message;

@Component
@Transactional
public class MessageToStringConverter  implements Converter<Message, String> {

	public String convert(Message source) {
		if(source == null) {
			return null;
		} else {
			return String.valueOf(source.getId());
		}
	}
	
	

}