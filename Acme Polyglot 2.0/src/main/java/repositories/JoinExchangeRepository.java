package repositories;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


import domain.JoinExchange;

@Repository
public interface JoinExchangeRepository extends JpaRepository<JoinExchange,Integer>{

}
