package converters;
import javax.transaction.Transactional;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import domain.Administrator;

@Component
@Transactional
public class AdministratorToStringConverter implements Converter<Administrator, String>{
	@Override
	public String convert(Administrator source) {
		String result;

		if (source == null)
			result = null;
		else
			result = String.valueOf(source.getId());

		return result;
	}
}
