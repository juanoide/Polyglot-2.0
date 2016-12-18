package converters;

import javax.transaction.Transactional;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import domain.Description;

@Component
@Transactional
public class DescriptionToStringConverter implements Converter<Description, String>{
	@Override
	public String convert(Description source) {
		String result;

		if (source == null)
			result = null;
		else
			result = String.valueOf(source.getId());

		return result;
	}
}
