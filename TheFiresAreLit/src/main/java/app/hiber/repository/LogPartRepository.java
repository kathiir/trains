package app.hiber.repository;

import app.model.LogPart;

import java.util.List;
import java.util.Optional;

public interface LogPartRepository {
    List<LogPart> findAll();
    void save(LogPart item);
    void deleteAll();
    long count();
    Optional<LogPart> findById(Integer id);
}
