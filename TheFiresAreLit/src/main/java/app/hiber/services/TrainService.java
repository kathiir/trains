package app.hiber.services;

import app.dto.TrainDto;
import app.mapper.Mapper;
import app.model.Train;
import app.hiber.repository.TrainRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class TrainService {

    private final TrainRepository trainRepository;
    private final Mapper mapper;
//    private final Random random = new Random();

    @Autowired
    public TrainService(TrainRepository trainRepository, Mapper mapper) {
        this.trainRepository = trainRepository;
        this.mapper = mapper;

    }

    public TrainDto randomTrain() {
        Optional<Train> item = trainRepository.getRandomItem();

        if (item.isEmpty()) {
            return null;
        }

        return mapper.toSimpleTrainDto(item.orElse(null));
    }


}
