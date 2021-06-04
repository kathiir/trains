package app.repositoryHibernate;

import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RandomItemRepository<T> extends BaseRepository<T> {

    Optional<T> getRandomItem();
}
