package app.hiber.repository;

import app.model.Schedule;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ScheduleRepository {
    List<Schedule> findAll();
    void save(Schedule item);
    void deleteAll();
    long count();
    Optional<Schedule> findById(Integer id);
}
