package app.repositoryHibernate;

import app.model.Train;

import java.util.List;
import java.util.Optional;

public interface TrainRepository {
    List<Train> findAll();
    void save(Train item);
    void deleteAll();
    long count();
    Optional<Train> findById(long id);
    Optional<Train> getRandomItem();
}
