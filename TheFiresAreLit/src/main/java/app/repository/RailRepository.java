package app.repository;

import app.dto.Rail;

import java.util.List;

public interface RailRepository {
    List<Rail> getAll();
//    Rail getPreviousRail(Rail rail);
    void addRail(Rail rail);

}
