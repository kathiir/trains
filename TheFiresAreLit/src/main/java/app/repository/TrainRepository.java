package app.repository;

import app.dto.Rail;
import app.dto.Train;

import java.util.List;

public interface TrainRepository {
    List<Train> getAll();

    Train getByName(String name);

    Train getTrainByRail(Rail rail);

    void addTrain(Train train);

//    Rail getRailByTrain(Train train);
}
