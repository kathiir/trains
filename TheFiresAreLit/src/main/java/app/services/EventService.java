package app.services;

import app.mapper.Mapper;
import app.repository.EventRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
public class EventService {
    private final Random random = new Random();
    private final Mapper mapper;

    @Autowired
    public EventService(EventRepository eventRepository, Mapper mapper) {
        this.mapper = mapper;
    }
}
