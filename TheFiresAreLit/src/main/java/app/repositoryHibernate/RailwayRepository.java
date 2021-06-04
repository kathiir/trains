package app.repositoryHibernate;

import app.model.Railway;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RailwayRepository {
    List<Railway> findAll();
    void save(Railway item);
    void deleteAll();
    long count();
    Optional<Railway> findById(long id);
}
