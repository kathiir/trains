package app.repository.impl;

import app.dto.Rail;
import app.repository.RailRepository;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Repository
public class RailRepositoryImpl implements RailRepository {
    private static List<Rail> source = new ArrayList<>();

    @Override
    public List<Rail> getAll() {
        return source;
    }

//    @Override
//    public Rail getPreviousRail(Rail rail) {
//        return source.stream().filter(p -> Objects.equals(p.getNext(), rail)).findFirst().orElse(null);
//    }

    @Override
    public void addRail(Rail rail) {
        source.add(rail);
    }
}
