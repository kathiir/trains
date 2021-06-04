package app.servicesHibernate;

import app.dto.RailDto;
import app.mapper.Mapper;
import app.model.Rail;
import app.repositoryHibernate.impl.RailRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class RailService {

    private final Mapper mapper;
    private final RailRepository railRepository;

    public RailService(Mapper mapper, RailRepository railRepository) {
        this.mapper = mapper;
        this.railRepository = railRepository;
    }

    public RailDto randomRail() {
        Optional<Rail> item = railRepository.getRandomItem();

        if (item.isEmpty()) {
            return null;
        }

        return mapper.toSimpleRailDto(item.orElse(null));
    }
}
