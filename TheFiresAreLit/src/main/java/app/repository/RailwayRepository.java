package app.repository;

import app.dto.Railway;

public interface RailwayRepository {
    Railway getRailway();

//    Station getStationByRailway(Railway railway);
    void setRailway(Railway railway);

}
