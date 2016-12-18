package repositories;





import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;


import domain.Search;

@Repository
public interface SearchRepository extends JpaRepository<Search, Integer>{

	@Query("select a from Search a where a=?1")
	Search findBySearch(Search search);
	
	@Query("select s.name, sum(s.count) from Search s group by s.name")
	Object[]keywordsSums();
	
}
