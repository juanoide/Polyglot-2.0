package converters;

import javax.transaction.Transactional;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import domain.JoinExchange;

@Component
@Transactional
public class JoinExchangeToStringConverter implements Converter<JoinExchange, String>{
	@Override
	public String convert(JoinExchange source) {
		String result;

		if (source == null)
			result = null;
		else
			result = String.valueOf(source.getId());

		return result;
	}
}
