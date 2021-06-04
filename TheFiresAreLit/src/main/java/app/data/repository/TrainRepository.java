package app.data.repository;

import app.model.Train;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TrainRepository extends PagingAndSortingRepository<Train, Integer> {

}
