package app.repository;

import app.model.LogPart;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LogPartRepository extends CrudRepository<LogPart, Integer> {

}
