package app.services;

import app.dto.Rail;
import app.dto.Route;
import app.dto.Station;
import app.dto.Train;
import app.repository.*;
import com.jakewharton.fliptables.FlipTable;
import org.apache.commons.lang3.tuple.Triple;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class RouteService {

    private final RouteRepository routeRepository;
    private final TrainRepository trainRepository;
    private final StationRepository stationRepository;
    private final EventRepository eventRepository;

    private final RailwayService railwayService;
    private final EventService eventService;

    private final Random random = new Random();

    private int currentTime;


    @Autowired
    public RouteService(RouteRepository routeRepository, TrainRepository trainRepository,
                        EventRepository eventRepository,
                        StationRepository stationRepository,
                        RailwayService railwayService, EventService eventService) {
        this.routeRepository = routeRepository;
        this.trainRepository = trainRepository;
        this.stationRepository = stationRepository;
        this.railwayService = railwayService;
        this.eventRepository = eventRepository;
        this.eventService = eventService;

        currentTime = 0;
    }

    void generateRandomRoute() {
        Route route = new Route();

        Station start = railwayService.getRandomStartingStation();
        Station end = railwayService.getRandomEndingStation(start);

        int time = random.nextInt(24 * 60);
//        int time = 5;


        route.getSchedule().add(Triple.of(start, time, time + 5));
        route.getLog().add(Triple.of(start, time, time + 5));

        Rail current = start;

        time = time + 5;

        while (true) {
            current = railwayService.getNextRail(current);
            if (stationRepository.isStation(current)) {
                route.getSchedule().add(Triple.of(((Station) current), time, time + 5));
                time = time + 5;
                if (((Station) current).getName().equals(end.getName())) {
                    break;
                }
            } else {
                time++;
            }
        }

        routeRepository.addRoute(route);

        Train train = new Train();
        train.setName("Trainello");
        train.setRail(start);
        train.setRoute(route);

        trainRepository.addTrain(train);
    }

    public void update() {

        trainRepository.getAll().forEach(train -> {
            var schedule = train.getRoute().getSchedule();
            var log = train.getRoute().getLog();
            if (!schedule.isEmpty() && schedule.get(0).getRight() <= currentTime) {
                if (schedule.size() == log.size()) {
                    return;
                }
                if (!eventRepository.getByRail(train.getRail()).isEmpty()) {
                    if (eventRepository.getByRail(train.getRail()).stream().anyMatch(event -> event.getEnding() >= currentTime)) {
                        return;

                    }
                }
                if (!eventRepository.getByTrain(train).isEmpty()) {
                    if (eventRepository.getByTrain(train).stream().anyMatch(event -> event.getEnding() >= currentTime)) {
                        return;

                    }
                }
                if (stationRepository.isStation(railwayService.getNextRail(train.getRail()))) {
                    log.add(Triple.of(((Station) railwayService.getNextRail(train.getRail())),
                            currentTime, currentTime + 2 + random.nextInt(4)));

                }
                if (stationRepository.isStation(train.getRail())) {
                    if (currentTime >= log.get(log.size() - 1).getRight()) {
                        log.set(log.size() - 1, Triple.of(log.get(log.size() - 1).getLeft(),
                                log.get(log.size() - 1).getMiddle(), currentTime));
                        train.setRail(railwayService.getNextRail(train.getRail()));
                    }
                } else {
                    train.setRail(railwayService.getNextRail(train.getRail()));
                }
            }
        });

        currentTime++;
    }

    public void restart() {
        currentTime = 0;
        trainRepository.getAll().clear();
        routeRepository.getAll().clear();
    }

    public void run() {
        restart();
        generateRandomRoute();
//        while (trainRepository.getAll().stream()
//                .anyMatch(train -> train.getRoute().getLog().size() != train.getRoute().getSchedule().size())) {

        for (int i = 0; i < 60 * 60; i++) {


            if (random.nextInt(30) == 15) {
                eventService.generateRandomEvent(currentTime);
            }

            update();
        }
    }

    public String getTrainLog() {
//        StringBuilder stringBuilder = new StringBuilder();

//        stringBuilder.append("Arr")

        String[] headers = {"Station", "Arrival", "Departure"};

        List<String[]> list = new ArrayList<>();

        if (!trainRepository.getAll().isEmpty()) {
            Route route = trainRepository.getAll().get(0).getRoute();
            var schedule = route.getSchedule();
            var log = route.getLog();
            for (int i = 0; i < route.getSchedule().size(); i++) {
                String[] strings = {schedule.get(i).getLeft().getName(),
                        schedule.get(i).getMiddle() + "\n"
                                + log.get(i).getMiddle()
                        , schedule.get(i).getRight() + "\n"
                        + log.get(i).getRight()
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
