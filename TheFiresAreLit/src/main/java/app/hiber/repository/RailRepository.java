package app.hiber.repository;

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
    Optional<Rail> findById(Integer id);
    Optional<Rail> getRandomItem();
}
