package app.repository;

import app.model.Railway;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RailwayRepository extends PagingAndSortingRepository<Railway, Integer> {

}
