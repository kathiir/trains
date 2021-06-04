package app.data.repository;

import app.model.Event;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EventRepository extends PagingAndSortingRepository<Event, Integer> {

//    List<Event> getEventsByRail(Rail rail);
//    List<Event> getEventsByTrain(Train train);
    List<Event> findAll();
}
