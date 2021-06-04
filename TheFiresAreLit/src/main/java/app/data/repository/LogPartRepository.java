package app.data.repository;

import app.model.LogPart;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LogPartRepository extends PagingAndSortingRepository<LogPart, Integer> {

}
