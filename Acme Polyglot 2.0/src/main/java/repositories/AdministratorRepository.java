package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import security.UserAccount;

import domain.Administrator;

@Repository
public interface AdministratorRepository extends JpaRepository<Administrator, Integer>{
	@Query("select a from Administrator a where a.userAccount=?1")
	Administrator findByUserAccount(UserAccount userAccount);
	


	@Query("select a from Administrator a where a.userAccount.id = ?1")
	Administrator findByUserAccountID(int UserAccountID);
	
	@Query("select avg(a.size) from LanguageExchange lan join lan.sponsorships a where lan.polyglotOrganise.id= ?1")
	Double averageLanguageExchangeSponsorshipPolyglot(int polyglotid);
	
	@Query("select min(a.messages.size) from Folder a")
	Integer minMessageForFolder();
	@Query("select max(a.messages.size) from Folder a")
	Integer maxMessageForFolder();
	
	@Query("select avg(a.messages.size) from Folder a")
	Double avgMessageForFolder();
	
	@Query("select min(a.messages.size), max(a.messages.size), avg(a.messages.size) from Folder a")
	Collection<Integer> minMaxAvgMessageForFolder();
}