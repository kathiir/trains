package app.repository.impl;

import app.dto.Event;
import app.dto.Rail;
import app.dto.Train;
import app.repository.EventRepository;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Repository
public class EventRepositoryImpl implements EventRepository {
    private static List<Event> source = new ArrayList<>();

    @Override
    public List<Event> getAll() {
        return source;
    }

    @Override
    public List<Event> getByTrain(Train train) {
        return source.stream().filter(p -> Objects.equals(p.getTrain(), train)).collect(Collectors.toList());
    }

    @Override
    public List<Event> getByRail(Rail rail) {
        return source.stream().filter(p -> Objects.equals(p.getRailSection(), rail)).collect(Collectors.toList());
    }

    @Override
    public void addEvent(Event event) {
        source.add(event);
    }

    @Override
    public void deleteEvent(Event event) {
        source.remove(event);
    }
}
