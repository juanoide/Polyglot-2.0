package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Language;

@Repository
public interface LanguageRepository extends JpaRepository<Language, Integer>{

	
	@Query("select count(*) from Language l join l.expectedTalks smo1 where l=?1")
	Collection<Language> countExpectedTalksOfLanguage(Language language);
	
	@Query("select l from Language l where l.code=?1")
	Language searchLanguageForCode(String code);

}
