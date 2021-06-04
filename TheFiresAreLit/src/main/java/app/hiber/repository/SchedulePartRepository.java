package app.hiber.repository;

import app.model.SchedulePart;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SchedulePartRepository {
    List<SchedulePart> findAll();
    void save(SchedulePart item);
    void deleteAll();
    long count();
    Optional<SchedulePart> findById(Integer id);
}
