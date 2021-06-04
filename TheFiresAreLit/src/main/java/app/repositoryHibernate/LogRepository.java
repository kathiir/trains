package app.repositoryHibernate;

import app.model.Log;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface LogRepository {
    List<Log> findAll();
    void save(Log item);
    void deleteAll();
    long count();
    Optional<Log> findById(long id);
}
