package app.services;

import app.dto.Rail;
import app.dto.Railway;
import app.dto.Station;
import app.repository.RailRepository;
import app.repository.RailwayRepository;
import app.repository.StationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.Random;

@Service
public class RailwayService {

    private final RailRepository railRepository;
    private final StationRepository stationRepository;
    private final RailwayRepository railwayRepository;
    private final Random random = new Random();

    @Autowired
    public RailwayService(RailRepository railRepository, StationRepository stationRepository, RailwayRepository railwayRepository) {
        this.railRepository = railRepository;
        this.stationRepository = stationRepository;
        this.railwayRepository = railwayRepository;
        generateRailway();
    }

    public void generateRailway() {
        Railway railway = new Railway();
        int stationNum = 5 + random.nextInt(15);

        for (int i = 0; i < stationNum - 1; i++) {
            Station station = new Station("Station" + i);
            stationRepository.addStation(station);
            railRepository.addRail(station);
            railway.getRails().add(station);
            railway.getStations().add(station);
            int railLength = 25 + random.nextInt(100);
//            int railLength = 5;
            for (int j = 0; j < railLength; j++) {
                Rail rail = new Rail();
                railway.getRails().add(rail);
                railRepository.addRail(rail);
            }
        }
        Station station = new Station("Station" + (stationNum - 1));
        stationRepository.addStation(station);
        railRepository.addRail(station);
        railway.getRails().add(station);
        railway.getStations().add(station);
        railwayRepository.setRailway(railway);
    }

    public Rail getNextRail(Rail rail) {
        int railIndex = railwayRepository.getRailway().getRails().indexOf(rail);
        if (railIndex < 0 || railIndex + 1 == railwayRepository.getRailway().getRails().size()) {
            return null;
        }
        return railwayRepository.getRailway().getRails().get(railIndex + 1);
    }

    public Rail getPreviousRail(Rail rail) {
        int railIndex = railwayRepository.getRailway().getRails().indexOf(rail);
        if (railIndex < 1) {
            return null;
        }
        return railwayRepository.getRailway().getRails().get(railIndex - 1);
    }

    public boolean isLastInRailway(Rail rail) {
        return Objects.equals(railwayRepository.getRailway().getRails().get(railwayRepository.getRailway().getRails().size()-1), rail);
    }

    public boolean isFirstInRailway(Rail rail) {
        return Objects.equals(railwayRepository.getRailway().getRails().get(0), rail);
    }

    public Station getRandomStartingStation() {
        Station station = railwayRepository.getRailway().getStations().get(random.nextInt(railwayRepository.getRailway().getStations().size() - 1));
        return station;
    }

    public Station getRandomEndingStation(Station begStation) {

        if (isLastInRailway(begStation)) {
            return begStation;
        }

        int stationsCount = railwayRepository.getRailway().getStations().size();
        int beginningIndex = railwayRepository.getRailway().getStations().indexOf(begStation);
        Station station = railwayRepository.getRailway().getStations().get(beginningIndex + 1 + random.nextInt(stationsCount - beginningIndex - 1));

        return station;
    }
}
