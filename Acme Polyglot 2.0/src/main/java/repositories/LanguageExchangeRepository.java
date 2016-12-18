package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Description;
import domain.LanguageExchange;

@Repository
public interface LanguageExchangeRepository extends JpaRepository<LanguageExchange, Integer>{

	@Query("select c from LanguageExchange c where (c.date)>adddate(current_date,-90) and (c.date)<adddate(current_date,0)")
	Collection<LanguageExchange> listExchangePastOrganised3Months();
	
	@Query("select c from LanguageExchange c where (c.date)<adddate(current_date,90) and (c.date)>adddate(current_date,0)")
	Collection<LanguageExchange> listExchangeFutureOrganised3Months();
	
	@Query("select c from LanguageExchange c join c.joinExchanges s join s.polyglot i where i.id = ?1")
	Collection<LanguageExchange> languageExchangeOfPolyglotID(int id);
	
	@Query("select c from LanguageExchange c join c.polyglotOrganise i where i.id = ?1")
	Collection<LanguageExchange> findAllPolyglotLogin(int id);
	
	@Query("select c from LanguageExchange c where c.cancel is false")
	Collection<LanguageExchange> languageExchangeNoCancel();
	
	@Query("select t from LanguageExchange t where t.cancel=false and (t.place like %?1%)")
	Collection<LanguageExchange> languageExchangeForKeyword(String keyword);
								//(t.SKU like %?1% or t.name like %?1% or t.description like %?1%)
	
	//select d from Description d join d.tag m where d.language.id='40' and(d.title like '%roma%' or d.text like '%roma%' or m like '%ingles%')
	
	@Query("select d from Description d join d.tag m where d.language.id=?1 and (d.title like %?2% or d.text like %?2% or m like %?2%))")
	Collection<Description> languageExchangeDescriptionForKeyword(int languageId, String keyword);
	/*
	@Query("select d from Description d where d.language.id=?1 and(d.title like %?2% or d.text like %?2%))")
	Collection<Description> languageExchangeDescriptionForKeyword(int languageId, String keyword); */
	
	
}
