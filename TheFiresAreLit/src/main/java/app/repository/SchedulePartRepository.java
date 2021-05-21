package app.repository;

import app.model.SchedulePart;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SchedulePartRepository extends CrudRepository<SchedulePart, Integer> {

}
