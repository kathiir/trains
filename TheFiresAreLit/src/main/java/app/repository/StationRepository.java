package app.repository;

import app.dto.Rail;
import app.dto.Station;

import java.util.List;

public interface StationRepository {
    List<Station> getAll();
    void addStation(Station station);

    boolean isStation(Rail rail);

}
