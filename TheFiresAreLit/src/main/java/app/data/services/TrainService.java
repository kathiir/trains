package app.data.services;

import app.dto.TrainDto;
import app.mapper.Mapper;
import app.model.Train;
import app.data.repository.TrainRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

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
        long qty = trainRepository.count();
        int idx = (int)(Math.random() * qty);
        Page<Train> trainPage = trainRepository.findAll(PageRequest.of(idx, 1));

        Train train = trainPage.iterator().next();
        if (train == null) {

            return null;
        }

        return mapper.toSimpleTrainDto(train);
    }


}
