package app.repositoryHibernate;

import app.model.Rail;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RailRepository {
    List<Rail> findAll();
    void save(Rail item);
    void deleteAll();
    long count();
    Optional<Rail> findById(long id);
    Optional<Rail> getRandomItem();
}
