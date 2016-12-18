package converters;

import javax.transaction.Transactional;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import domain.Language;

@Component
@Transactional
public class LanguageToStringConverter implements Converter<Language, String>{
	@Override
	public String convert(Language source) {
		String result;

		if (source == null)
			result = null;
		else
			result = String.valueOf(source.getId());

		return result;
	}
}
