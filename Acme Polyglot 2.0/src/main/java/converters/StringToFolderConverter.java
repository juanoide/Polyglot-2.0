package converters;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import repositories.FolderRepository;
import domain.Folder;

@Component
@Transactional
public class StringToFolderConverter implements Converter<String, Folder> {
	
	@Autowired FolderRepository repo;

	public Folder convert(String source) {
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
