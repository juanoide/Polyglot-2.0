package converters;

import javax.transaction.Transactional;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import domain.Search;

@Component
@Transactional
public class SearchToStringConverter implements Converter<Search, String>{
	@Override
	public String convert(Search source) {
		String result;

		if (source == null)
			result = null;
		else
			result = String.valueOf(source.getId());

		return result;
	}
}
