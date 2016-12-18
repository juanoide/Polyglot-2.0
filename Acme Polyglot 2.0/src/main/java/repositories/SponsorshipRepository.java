package repositories;


import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;



import domain.Sponsorship;

@Repository
public interface SponsorshipRepository extends JpaRepository<Sponsorship, Integer>{


	@Query("select a from Sponsorship a where a=?1")
	Sponsorship findBySponsorship(Sponsorship sponsorship);
	
	@Query("select c from Sponsorship c join c.agent i where i.id = ?1")
	Collection<Sponsorship> sponsorshipOfAgentID(int id);
	
	@Query("select c from Sponsorship c join c.agent i where i.id = ?1")
	Collection<Sponsorship> findAllAgentLogin(int id);
	
}
