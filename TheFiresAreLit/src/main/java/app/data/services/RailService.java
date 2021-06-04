package app.data.services;

import app.dto.RailDto;
import app.mapper.Mapper;
import app.model.Rail;
import app.data.repository.RailRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Service
public class RailService {

    private final Mapper mapper;
    private final RailRepository railRepository;

    public RailService(Mapper mapper, RailRepository railRepository) {
        this.mapper = mapper;
        this.railRepository = railRepository;
    }

    public RailDto randomRail() {
        long qty = railRepository.count();
        int idx = (int)(Math.random() * qty);
        Page<Rail> trainPage = railRepository.findAll(PageRequest.of(idx, 1));

        Rail rail = trainPage.iterator().next();
        if (rail == null) {

            return null;
        }

        return mapper.toSimpleRailDto(rail);
    }
}
