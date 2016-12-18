package repositories;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import security.UserAccount;



import domain.Polyglot;


@Repository
public interface PolyglotRepository extends JpaRepository<Polyglot, Integer>{ 
	@Query("select a from Polyglot a where a.userAccount=?1")
	Polyglot findByUserAccount(UserAccount userAccount);
	
	@Query("select a from Polyglot a where a.userAccount.username=?1")
	Polyglot findByUserAccountUserName(String userAccount);

	@Query("select count(*) from Polyglot l join l.organiseExchanges smo1 where l=?1")
	Integer numberOrganiseExchanges(Polyglot polyglot);
	
	@Query("select count(*) from Polyglot l join l.joinExchanges smo1 where l=?1")
	Integer numberJoinExchanges(Polyglot polyglot);


	
	
}


