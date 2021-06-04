package app.hiber.services;

import app.dto.RailDto;
import app.hiber.repository.RailRepository;
import app.mapper.Mapper;
import app.model.Rail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class RailService {

    private final Mapper mapper;
    private final RailRepository railRepository;

    @Autowired
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
