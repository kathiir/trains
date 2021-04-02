package app.repository.impl;

import app.dto.Railway;
import app.repository.RailwayRepository;
import org.springframework.stereotype.Repository;

@Repository
public class RailwayRepositoryImpl implements RailwayRepository {

    private static Railway source = new Railway();


    @Override
    public Railway getRailway() {
        return source;
    }

//    @Override
//    public Station getStationByRailway(Railway railway) { //с бд заменить на id
//        return railway.getBeginning();
//    }

    @Override
    public void setRailway(Railway railway) {
        source = railway;
    }
}
