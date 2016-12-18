package converters;

import javax.transaction.Transactional;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import domain.ExpectedTalk;

@Component
@Transactional
public class ExpectedTalkToStringConverter implements Converter<ExpectedTalk, String>{
	@Override
	public String convert(ExpectedTalk source) {
		String result;

		if (source == null)
			result = null;
		else
			result = String.valueOf(source.getId());

		return result;
	}
}
