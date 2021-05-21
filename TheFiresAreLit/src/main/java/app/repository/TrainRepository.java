package app.repository;

import app.model.Train;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TrainRepository extends CrudRepository<Train, Integer> {

}
