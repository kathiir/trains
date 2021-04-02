package app.repository.impl;

import app.dto.Rail;
import app.dto.Train;
import app.repository.TrainRepository;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Repository
public class TrainRepositoryImpl implements TrainRepository {
    private static List<Train> source = new ArrayList<>();

    @Override
    public List<Train> getAll() {
        return source;
    }

    @Override
    public Train getByName(String name) {
        return source.stream().filter(p -> Objects.equals(p.getName(), name)).findFirst().orElse(null);
    }

    @Override
    public Train getTrainByRail(Rail rail) {
        return source.stream().filter(p -> Objects.equals(p.getRail(), rail)).findFirst().orElse(null);
    }

    @Override
    public void addTrain(Train train) {
        source.add(train);
    }
}
