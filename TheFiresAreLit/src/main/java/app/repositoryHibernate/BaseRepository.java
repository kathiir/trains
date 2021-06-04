package app.repositoryHibernate;

import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BaseRepository<T> {
    List<T> findAll();
    void save(T item);
    void deleteAll();
    long count();
    Optional<T> findById(long id);
}
