package app.data.services;

import app.dto.RailDto;
import app.dto.RouteDto;
import app.dto.RoutePartDto;
import app.dto.TrainDto;
import app.mapper.Mapper;
import app.model.Log;
import app.model.Schedule;
import app.data.repository.EventRepository;
import app.data.repository.LogRepository;
import app.data.repository.RailRepository;
import app.data.repository.ScheduleRepository;
import app.data.repository.TrainRepository;
import com.jakewharton.fliptables.FlipTable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Service
public class RouteService {

    private final LogRepository logRepository;
    private final ScheduleRepository scheduleRepository;
    private final TrainRepository trainRepository;
    private final RailRepository railRepository;
    private final EventRepository eventRepository;

    private final RailwayService railwayService;
    private final EventService eventService;

    private final Random random = new Random();
    private final Mapper mapper;

    private int currentTime;

    @Autowired
    public RouteService(LogRepository logRepository, ScheduleRepository scheduleRepository, TrainRepository trainRepository,
                        RailRepository railRepository, EventRepository eventRepository,
                        RailwayService railwayService, EventService eventService, Mapper mapper) {
        this.logRepository = logRepository;
        this.scheduleRepository = scheduleRepository;
        this.trainRepository = trainRepository;
        this.railRepository = railRepository;
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
        train.setRail(schedule.getSchedule().get(0).getStation());

        trainRepository.save(train);

//        update();
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

                if (random.nextInt(30) == 15) {
                    eventService.generateRandomEvent(currentTime);
                }

                var events = mapper.toEventDtos(eventRepository.findAll());

                if (events.stream().anyMatch(event ->
                        (event.getTrain() != null && event.getTrain().getId().equals(trainDto.getId()) && event.getEnding() >= currentTime)
                                || (event.getRail() != null && event.getRail().getId().equals(trainDto.getRail().getId()) && event.getEnding() >= currentTime)
                )) {
                    currentTime++;
                    return;
                }

                if (trainDto.getRail().getNext().isStation()) {
                    RoutePartDto routePartDto = new RoutePartDto();
                    routePartDto.setStation(trainDto.getRail().getNext());
                    routePartDto.setArrival(currentTime);
                    routePartDto.setDepartment(currentTime + 2 + random.nextInt(7));
                    log.add(routePartDto);
                }

                if (trainDto.getRail().isStation()) {
                    if (currentTime >= log.get(log.size() - 1).getDepartment()) {

                        var routePartDto = log.get(log.size() - 1);
                        routePartDto.setDepartment(currentTime);
                        trainDto.setRail(trainDto.getRail().getNext());
                    }
                } else {

                    trainDto.setRail(trainDto.getRail().getNext());
                }

                var tr = mapper.toTrain(trainDto);
                logRepository.save(tr.getLog());
                trainRepository.save(tr);
            }

        }

        currentTime++;
    }

    public void run() {
        restart();
        generateRandomRoute();

        for (int i = 0; i < 60 * 60; i++) {


            update();
        }
    }

    public void restart() {
        currentTime = 0;
        trainRepository.deleteAll();
        logRepository.deleteAll();
        scheduleRepository.deleteAll();
    }

    public String getTrainLog() {
//        StringBuilder stringBuilder = new StringBuilder();

//        stringBuilder.append("Arr")

        String[] headers = {"Station", "Arrival", "Departure"};

        List<String[]> list = new ArrayList<>();

        var tr = trainRepository.findAll().iterator().next();
        if (tr != null) {
            var train = mapper.toTrainDto(tr);
            RouteDto routeDto = train.getRoute();
            var schedule = routeDto.getSchedule();
            var log = routeDto.getLog();
            for (int i = 0; i < routeDto.getSchedule().size(); i++) {
                String[] strings = {schedule.get(i).getStation().getName(),
                        schedule.get(i).getArrival() + "\n"
                                + log.get(i).getArrival()
                        , schedule.get(i).getDepartment() + "\n"
                        + log.get(i).getDepartment()
                };
                list.add(strings);
            }

        }

        String[][] argh = new String[list.size()][3];
        for (int i = 0; i < argh.length; i++) {
            argh[i] = list.get(i);
        }


        return FlipTable.of(headers, argh);
    }
}
