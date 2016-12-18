package repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;


import domain.Description;

@Repository
public interface DescriptionRepository extends JpaRepository<Description, Integer>{

	@Query("select a from Description a where a=?1")
	Description findByDescription(Description description);
	
}
