package app.repository;

import app.model.Railway;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RailwayRepository extends CrudRepository<Railway, Integer> {

}
