package app.repository.impl;

import app.dto.Rail;
import app.dto.Station;
import app.repository.StationRepository;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Repository
public class StationRepositoryImpl implements StationRepository {
    private static List<Station> source = new ArrayList<>();

    @Override
    public List<Station> getAll() {
        return source;
    }

    @Override
    public void addStation(Station station) {
        source.add(station);
    }

    @Override
    public boolean isStation(Rail rail) {
        return source.stream().anyMatch(p -> Objects.equals(p, rail));
    }
}
