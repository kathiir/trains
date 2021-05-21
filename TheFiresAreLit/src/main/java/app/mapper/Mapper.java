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

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@org.mapstruct.Mapper(componentModel = "spring")
public abstract class Mapper {
    public abstract RailDto toRailDto(Rail rail);

    public abstract Rail toRail(RailDto railDto);

    public abstract EventDto toEventDto(Event event);

    public abstract Event toEvent(EventDto eventDto);

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

        routeDto.setSchedule(scheduleToRoutePartDto(schedule.getSchedule()));
        routeDto.setLog(logToRoutePartDto(log.getLog()));

        return routeDto;
    }

    public Log toLog(RouteDto routeDto) {
        Log log = new Log();
        log.setId(routeDto.getLog_id());
        log.setLog(toLogPart(routeDto.getLog()));

        return log;
    }

    public Schedule toSchedule(RouteDto routeDto) {
        Schedule schedule = new Schedule();
        schedule.setId(routeDto.getSchedule_id());
        schedule.setSchedule(toSchedulePart(routeDto.getSchedule()));

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
        routePartDto.setStation(toRailDto(logPart.getStation()));

        return routePartDto;
    }

    public RoutePartDto toRoutePartDto(SchedulePart schedulePart) {
        RoutePartDto routePartDto = new RoutePartDto();

        routePartDto.setId(schedulePart.getId());
        routePartDto.setArrival(schedulePart.getArrival());
        routePartDto.setDepartment(schedulePart.getDepartment());
        routePartDto.setStation(toRailDto(schedulePart.getStation()));

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
            trainDto.setRail(toRailDto(rail));
        }

        trainDto.setRoute(toRouteDto(train.getLog(), train.getSchedule()));

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


        return train;
    }

}
