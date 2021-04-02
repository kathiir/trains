package app.repository.impl;

import app.dto.Route;
import app.dto.Train;
import app.repository.RouteRepository;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Repository
public class RouteRepositoryImpl implements RouteRepository {
    private static List<Route> source = new ArrayList<>();

    @Override
    public List<Route> getAll() {
        return source;
    }

    @Override
    public void addRoute(Route route) {
        source.add(route);
    }
//
//    @Override
//    public Route getByTrain(Train train) {
//        return source.stream().filter(p -> Objects.equals(p.getTrain(), train)).findFirst().orElse(null);
//    }
}
