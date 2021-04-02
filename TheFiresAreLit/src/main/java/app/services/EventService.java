package app.services;

import app.dto.Event;
import app.repository.EventRepository;
import app.repository.RailRepository;
import app.repository.TrainRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
public class EventService {

    private final EventRepository eventRepository;
    private final TrainRepository trainRepository;
    private final RailRepository railRepository;
    private final Random random = new Random();

    @Autowired
    public EventService(EventRepository eventRepository, TrainRepository trainRepository, RailRepository railRepository) {
        this.eventRepository = eventRepository;
        this.trainRepository = trainRepository;
        this.railRepository = railRepository;
    }


    public void generateRandomEvent(int currentTime) {
        Event event = new Event();

        event.setBeginning(currentTime);
        event.setEnding(currentTime + 5);

        if (random.nextBoolean() && !trainRepository.getAll().isEmpty()){
            event.setTrain(trainRepository.getAll().get(random.nextInt(trainRepository.getAll().size())));
        } else {
            event.setRailSection(railRepository.getAll().get(random.nextInt(railRepository.getAll().size())));
        }

        eventRepository.addEvent(event);
    }
}
