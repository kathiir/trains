package app.repository;

import app.model.Rail;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RailRepository extends CrudRepository<Rail, Integer> {

}
