package repositories;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import domain.ExpectedTalk;

@Repository
public interface ExpectedTalkRepository extends JpaRepository<ExpectedTalk,Integer>{

}
