package app.servicesHibernate;

import app.dto.EventDto;
import app.dto.TrainDto;
import app.mapper.Mapper;
import app.repositoryHibernate.EventRepository;
import app.repositoryHibernate.RailRepository;
import app.repositoryHibernate.TrainRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
public class EventService {

    private final Random random = new Random();

    private final TrainService trainService;
    private final RailService railService;
    private final EventRepository eventRepository;
    private final TrainRepository trainRepository;
    private final Mapper mapper;

    @Autowired
    public EventService(TrainService trainService, RailService railService, EventRepository eventRepository,
                        TrainRepository trainRepository,
                        RailRepository railRepository, Mapper mapper) {
        this.trainService = trainService;
        this.railService = railService;
        this.eventRepository = eventRepository;
        this.trainRepository = trainRepository;
        this.mapper = mapper;
    }

    public void generateRandomEvent(int currentTime) {
        EventDto eventDto = new EventDto();

        eventDto.setBeginning(currentTime);
        eventDto.setEnding(currentTime + 5);

        if (random.nextBoolean() && trainRepository.count() != 0){
            TrainDto train = trainService.randomTrain();
//            System.out.println("Train");
            eventDto.setTrain(train);
        } else {
//            System.out.println("hgh");

            eventDto.setRail(railService.randomRail());
        }

        eventRepository.save(mapper.toEvent(eventDto));
    }
}
