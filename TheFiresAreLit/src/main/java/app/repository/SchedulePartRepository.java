package app.repository;

import app.model.SchedulePart;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SchedulePartRepository extends PagingAndSortingRepository<SchedulePart, Integer> {

}
