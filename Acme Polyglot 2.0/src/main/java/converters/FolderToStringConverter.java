package converters;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import domain.Folder;

@Component
@Transactional
public class FolderToStringConverter implements Converter<Folder, String> {

	@Override
	public String convert(Folder customer) {
		String result;

		if (customer == null)
			result = null;
		else
			result = String.valueOf(customer.getId());

		return result;
	}

}
