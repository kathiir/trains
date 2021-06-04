package app.data.repository;

import app.model.Rail;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RailRepository extends PagingAndSortingRepository<Rail, Integer> {

}
