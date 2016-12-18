package repositories;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import security.UserAccount;



import domain.Agent;



@Repository
public interface AgentRepository extends JpaRepository<Agent, Integer>{ 
	@Query("select a from Agent a where a.userAccount=?1")
	Agent findByUserAccount(UserAccount userAccount);
	
	@Query("select a from Agent a where a.userAccount.username=?1")
	Agent findByUserAccountUserName(String userAccount);
	

	

}


