package converters;

import javax.transaction.Transactional;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import domain.LanguageExchange;

@Component
@Transactional
public class LanguageExchangeToStringConverter implements Converter<LanguageExchange, String>{
	@Override
	public String convert(LanguageExchange source) {
		String result;

		if (source == null)
			result = null;
		else
			result = String.valueOf(source.getId());

		return result;
	}
}
