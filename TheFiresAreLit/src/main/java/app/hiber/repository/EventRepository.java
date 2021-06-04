package app.hiber.repository;

import app.model.Event;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface EventRepository {
    List<Event> findAll();
    void save(Event item);
    void deleteAll();
    long count();
    Optional<Event> findById(Integer id);
}
