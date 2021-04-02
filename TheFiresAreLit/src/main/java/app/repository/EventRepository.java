package app.repository;

import app.dto.Event;
import app.dto.Rail;
import app.dto.Train;

import java.util.List;

public interface EventRepository {
    List<Event> getAll();

    List<Event> getByTrain(Train train);
    List<Event> getByRail(Rail rail);
    void addEvent(Event event);
    void deleteEvent(Event event);
}
