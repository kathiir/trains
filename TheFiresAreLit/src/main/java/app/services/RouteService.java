package app.services;

import app.dto.RailDto;
import app.dto.RouteDto;
import app.dto.RoutePartDto;
import app.dto.TrainDto;
import app.mapper.Mapper;
import app.model.Log;
import app.model.Schedule;
import app.model.Train;
import app.repository.EventRepository;
import app.repository.LogRepository;
import app.repository.RailRepository;
import app.repository.ScheduleRepository;
import app.repository.TrainRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
public class RouteService {

    private final LogRepository logRepository;
    private final ScheduleRepository scheduleRepository;
    private final TrainRepository trainRepository;
    private final RailRepository stationRepository;
    private final EventRepository eventRepository;

    private final RailwayService railwayService;
    private final EventService eventService;

    private final Random random = new Random();
    private final Mapper mapper;

    private int currentTime;

    @Autowired
    public RouteService(LogRepository logRepository, ScheduleRepository scheduleRepository, TrainRepository trainRepository,
                        RailRepository stationRepository, EventRepository eventRepository,
                        RailwayService railwayService, EventService eventService, Mapper mapper) {
        this.logRepository = logRepository;
        this.scheduleRepository = scheduleRepository;
        this.trainRepository = trainRepository;
        this.stationRepository = stationRepository;
        this.eventRepository = eventRepository;
        this.railwayService = railwayService;
        this.eventService = eventService;
        this.mapper = mapper;

        currentTime = 0;
    }

    public void generateRandomRoute() {
        RouteDto routeDto = new RouteDto();

        RailDto start = railwayService.getRandomStartingStation();
        RailDto end = railwayService.getRandomEndingStation(start);

        int time = random.nextInt(24 * 60);
//        int time = 5;


        routeDto.getSchedule().add(new RoutePartDto(start, time, time + 5));
        routeDto.getLog().add(new RoutePartDto(start, time, time + 5));

        RailDto current = start;

        time = time + 5;

        while (true) {
            current = railwayService.getNextRail(current);
            if (current.isStation()) {
                routeDto.getSchedule().add(new RoutePartDto(current, time, time + 5));
                time = time + 5;
                if (current.getName().equals(end.getName())) {
                    break;
                }
            } else {
                time++;
            }
        }

        Log log = mapper.toLog(routeDto);
        Schedule schedule = mapper.toSchedule(routeDto);
//
        logRepository.save(log);
        scheduleRepository.save(schedule);


        TrainDto trainDto = new TrainDto();
        trainDto.setName("Trainello");
//        trainDto.setRoute(routeDto);
//        trainDto.setRail(start);

        var train = mapper.toTrain(trainDto);
        train.setSchedule(schedule);
        train.setLog(log);

        trainRepository.save(train);
    }

    public void update() {

        var train = trainRepository.findAll().iterator().next();
        if (train != null) {
            var trainDto = mapper.toTrainDto(train);
            var schedule = trainDto.getRoute().getSchedule();
            var log = trainDto.getRoute().getLog();

            if (!schedule.isEmpty() && schedule.get(0).getDepartment() <= currentTime) {
                if (schedule.size() == log.size()) {
                    return;
                }
                if (!eventRepository.getByRail(train.getRailDto()).isEmpty()) {
                    if (eventRepository.getByRail(train.getRailDto()).stream().anyMatch(event -> event.getEnding() >= currentTime)) {
                        return;

                    }
                }
                if (!eventRepository.getByTrain(train).isEmpty()) {
                    if (eventRepository.getByTrain(train).stream().anyMatch(event -> event.getEnding() >= currentTime)) {
                        return;

                    }
                }
                if (stationRepository.isStation(railwayService.getNextRail(train.getRailDto()))) {
                    log.add(Triple.of(((StationDto) railwayService.getNextRail(train.getRailDto())),
                            currentTime, currentTime + 2 + random.nextInt(4)));

                }
                if (stationRepository.isStation(train.getRailDto())) {
                    if (currentTime >= log.get(log.size() - 1).getDepartment()) {
                        log.set(log.size() - 1, Triple.of(log.get(log.size() - 1).getStationDto(),
                                log.get(log.size() - 1).getArrival(), currentTime));
                        train.setRailDto(railwayService.getNextRail(train.getRailDto()));
                    }
                } else {
                    train.setRailDto(railwayService.getNextRail(train.getRailDto()));
                }
            }

        }

        trainRepository.getAll().forEach(train -> {
            var schedule = train.getRouteDto().getSchedule();
            var log = train.getRouteDto().getLog();
            if (!schedule.isEmpty() && schedule.get(0).getDepartment() <= currentTime) {
                if (schedule.size() == log.size()) {
                    return;
                }
                if (!eventRepository.getByRail(train.getRailDto()).isEmpty()) {
                    if (eventRepository.getByRail(train.getRailDto()).stream().anyMatch(event -> event.getEnding() >= currentTime)) {
                        return;

                    }
                }
                if (!eventRepository.getByTrain(train).isEmpty()) {
                    if (eventRepository.getByTrain(train).stream().anyMatch(event -> event.getEnding() >= currentTime)) {
                        return;

                    }
                }
                if (stationRepository.isStation(railwayService.getNextRail(train.getRailDto()))) {
                    log.add(Triple.of(((StationDto) railwayService.getNextRail(train.getRailDto())),
                            currentTime, currentTime + 2 + random.nextInt(4)));

                }
                if (stationRepository.isStation(train.getRailDto())) {
                    if (currentTime >= log.get(log.size() - 1).getDepartment()) {
                        log.set(log.size() - 1, Triple.of(log.get(log.size() - 1).getStationDto(),
                                log.get(log.size() - 1).getArrival(), currentTime));
                        train.setRailDto(railwayService.getNextRail(train.getRailDto()));
                    }
                } else {
                    train.setRailDto(railwayService.getNextRail(train.getRailDto()));
                }
            }
        });

        currentTime++;
    }

    public void restart() {
        currentTime = 0;
        trainRepository.deleteAll();
        logRepository.deleteAll();
        scheduleRepository.deleteAll();
    }
}
