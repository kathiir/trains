package app.mapper;

import app.dto.EventDto;
import app.dto.RailDto;
import app.dto.RailwayDto;
import app.dto.RouteDto;
import app.dto.RoutePartDto;
import app.dto.TrainDto;
import app.model.Event;
import app.model.Log;
import app.model.LogPart;
import app.model.Rail;
import app.model.Railway;
import app.model.Schedule;
import app.model.SchedulePart;
import app.model.Train;

import java.util.List;

@org.mapstruct.Mapper(componentModel = "spring")
public abstract class Mapper {
    public abstract RailDto toRailDto(Rail rail);

    @DoIgnore
    public RailDto toSimpleRailDto(Rail rail) {
        RailDto railDto = new RailDto();
        railDto.setId(rail.getId());
        railDto.setStation(rail.isStation());
        railDto.setName(rail.getName());

        Rail next = rail.getNext();
        if (next!= null) {
            RailDto nextDto = new RailDto();

            nextDto.setId(next.getId());
            nextDto.setStation(next.isStation());
            nextDto.setName(next.getName());

            railDto.setNext(nextDto);
        }


        return railDto;
    };

    public abstract Rail toRail(RailDto railDto);

    public EventDto toEventDto(Event event) {
        EventDto eventDto = new EventDto();
        eventDto.setId(event.getId());
        eventDto.setBeginning(event.getBeginning());
        eventDto.setEnding(event.getEnding());
        if (event.getTrain() != null) {
            eventDto.setTrain(toSimpleTrainDto(event.getTrain()));
        } else {
            eventDto.setRail(toSimpleRailDto(event.getRail()));
        }

        return eventDto;
    }

    public abstract List<EventDto> toEventDtos(List<Event> events);

    public Event toEvent(EventDto eventDto) {
        Event event = new Event();
        event.setId(eventDto.getId());
        event.setBeginning(eventDto.getBeginning());
        event.setEnding(eventDto.getEnding());
        if (eventDto.getTrain() != null) {
            event.setTrain(toSimpleTrain(eventDto.getTrain()));
        } else {
            event.setRail(toRail(eventDto.getRail()));
        }

        return event;
    }

    public RailwayDto toRailwayDto(Railway railway) {

        RailwayDto railwayDto = new RailwayDto();
        railwayDto.setId(railway.getId());

        Rail rail = railway.getRails();

        RailDto railDto = toRailDto(rail);


        while (railDto != null) {
            railwayDto.getRailDtos().add(railDto);
//            Integer id = railDto.getId();
            if (railDto.isStation()) {
                railwayDto.getStationDtos().add(railDto);
            }
            railDto = railDto.getNext();
        }

        return railwayDto;
    }

    public Railway toRailway(RailwayDto railwayDto) {
        Railway railway = new Railway();

        if (railwayDto.getId() != null) {
            railway.setId(railwayDto.getId());
        }

        if (!railwayDto.getRailDtos().isEmpty()) {
            Rail rail = toRail(railwayDto.getRailDtos().get(0));

            railway.setRails(rail);

//            List<Station> stations = new ArrayList<>();
//            for (StationDto st :
//                    railwayDto.getStationDtos()) {
//                stations.add(toStation(st));
//            }
//            railway.setStations(stations);
        }

        return railway;
    }

    public RouteDto toRouteDto(Log log, Schedule schedule) {
        RouteDto routeDto = new RouteDto();
        routeDto.setLog_id(log.getId());
        routeDto.setSchedule_id(schedule.getId());

        var scheduleDto = scheduleToRoutePartDto(schedule.getSchedule());
        scheduleDto.sort((o1, o2) -> {
            if (o1.getArrival() == null) {
                return 1;
            } else if (o2.getArrival() == null) {
                return -1;
            }
            return o1.getArrival().compareTo(o2.getArrival());
        });
        routeDto.setSchedule(scheduleDto);
        var logDto = logToRoutePartDto(log.getLog());
        logDto.sort((o1, o2) -> {
            if (o1.getArrival() == null) {
                return 1;
            } else if (o2.getArrival() == null) {
                return -1;
            }
            return o1.getArrival().compareTo(o2.getArrival());
        });
        routeDto.setLog(logDto);

        return routeDto;
    }

    public Log toLog(RouteDto routeDto) {
        Log log = new Log();
        log.setId(routeDto.getLog_id());
        log.setLog(toLogPart(routeDto.getLog()));

        for (LogPart part :
                log.getLog()) {
            part.setLog(log);
        }

        return log;
    }

    public Schedule toSchedule(RouteDto routeDto) {
        Schedule schedule = new Schedule();
        schedule.setId(routeDto.getSchedule_id());
        schedule.setSchedule(toSchedulePart(routeDto.getSchedule()));

        for (SchedulePart part :
                schedule.getSchedule()) {
            part.setSchedule(schedule);
        }

        return schedule;
    }

    public abstract List<RoutePartDto> logToRoutePartDto(List<LogPart> logPart);

    public abstract List<RoutePartDto> scheduleToRoutePartDto(List<SchedulePart> schedulePart);

    public abstract List<LogPart> toLogPart(List<RoutePartDto> routePartDto);

    public abstract List<SchedulePart> toSchedulePart(List<RoutePartDto> routePartDto);

    public RoutePartDto toRoutePartDto(LogPart logPart) {
        RoutePartDto routePartDto = new RoutePartDto();

        routePartDto.setId(logPart.getId());
        routePartDto.setArrival(logPart.getArrival());
        routePartDto.setDepartment(logPart.getDepartment());
        routePartDto.setStation(toSimpleRailDto(logPart.getStation()));

        return routePartDto;
    }

    public RoutePartDto toRoutePartDto(SchedulePart schedulePart) {
        RoutePartDto routePartDto = new RoutePartDto();

        routePartDto.setId(schedulePart.getId());
        routePartDto.setArrival(schedulePart.getArrival());
        routePartDto.setDepartment(schedulePart.getDepartment());
        routePartDto.setStation(toSimpleRailDto(schedulePart.getStation()));

        return routePartDto;
    }

    public LogPart toLogPart(RoutePartDto routePartDto) {
        LogPart logPart = new LogPart();

        logPart.setId(routePartDto.getId());
        logPart.setArrival(routePartDto.getArrival());
        logPart.setDepartment(routePartDto.getDepartment());
        logPart.setStation(toRail(routePartDto.getStation()));

        return logPart;
    }

    public SchedulePart toSchedulePart(RoutePartDto routePartDto) {
        SchedulePart logPart = new SchedulePart();

        logPart.setId(routePartDto.getId());
        logPart.setArrival(routePartDto.getArrival());
        logPart.setDepartment(routePartDto.getDepartment());
        logPart.setStation(toRail(routePartDto.getStation()));

        return logPart;
    }

    public TrainDto toTrainDto(Train train) {
        TrainDto trainDto = new TrainDto();
        trainDto.setId(train.getId());
        trainDto.setName(train.getName());

        Rail rail = train.getRail();
        if (rail != null) {
            trainDto.setRail(toSimpleRailDto(rail));
        }

        trainDto.setRoute(toRouteDto(train.getLog(), train.getSchedule()));

        return trainDto;
    }

    @DoIgnore
    public TrainDto toSimpleTrainDto(Train train) {
        TrainDto trainDto = new TrainDto();
        trainDto.setId(train.getId());
        trainDto.setName(train.getName());

//        Rail rail = train.getRail();
//        if (rail != null) {
//            trainDto.setRail(toSimpleRailDto(rail));
//        }
//
//        trainDto.setRoute(toRouteDto(train.getLog(), train.getSchedule()));

        return trainDto;
    }

    public Train toTrain(TrainDto trainDto) {
        Train train = new Train();
        train.setId(trainDto.getId());
        train.setName(trainDto.getName());
        RouteDto route = trainDto.getRoute();
        if (route != null) {
            train.setLog(toLog(route));
            train.setSchedule(toSchedule(route));
        }

        train.setRail(toRail(trainDto.getRail()));


        return train;
    }

    @DoIgnore
    public Train toSimpleTrain(TrainDto trainDto) {
        Train train = new Train();
        train.setId(trainDto.getId());
        train.setName(trainDto.getName());
//        RouteDto route = trainDto.getRoute();
//        if (route != null) {
//            train.setLog(toLog(route));
//            train.setSchedule(toSchedule(route));
//        }
//
//        train.setRail(toRail(trainDto.getRail()));


        return train;
    }

}
