package app.repository;

import app.dto.Route;
import app.dto.Train;

import java.util.List;

public interface RouteRepository {
    List<Route> getAll();

    void addRoute(Route route);

//    Route getByTrain(Train train);
}
