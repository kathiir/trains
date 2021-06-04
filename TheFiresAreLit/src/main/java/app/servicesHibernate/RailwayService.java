package app.servicesHibernate;

import app.dto.RailDto;
import app.dto.RailwayDto;
import app.mapper.Mapper;
import app.model.Rail;
import app.repositoryHibernate.RailRepository;
import app.repositoryHibernate.RailwayRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.Random;

@Service
public class RailwayService {

    private final RailRepository railRepository;
    private final RailwayRepository railwayRepository;
    private final Random random = new Random();
    private final Mapper mapper;

    @Autowired
    public RailwayService(RailRepository railRepository, RailwayRepository railwayRepository, Mapper mapper) {
        this.railRepository = railRepository;
        this.railwayRepository = railwayRepository;
        this.mapper = mapper;
    }

    @PostConstruct
    private void init() {
        generateRailway();
    }

    public void generateRailway() {
        RailwayDto railwayDto = new RailwayDto();
        int stationNum = 5 + random.nextInt(5);

        RailDto prev = new RailDto("Station" + 0);

        for (int i = 1; i < 10; i++) {

            int railLength = 20 + random.nextInt(20);

            for (int j = 0; j < 40; j++) {
                RailDto railDto = new RailDto();

                prev.setNext(railDto);

//                railRepository.addRail(prev);
                railwayDto.getRailDtos().add(prev);

                if (j == 0) {
//                    stationRepository.addStation((StationDto) prev);
                    railwayDto.getStationDtos().add(prev);
                }

                prev = railDto;
            }
            RailDto stationDto = new RailDto("Station" + i);

            prev.setNext(stationDto);

//            railRepository.addRail(prev);
            railwayDto.getRailDtos().add(prev);

            prev = stationDto;
        }

        railwayDto.getStationDtos().add(prev);
        railwayDto.getRailDtos().add(prev);

//        stationRepository.addStation((StationDto) prev);
//        railRepository.addRail(prev);

//        System.out.println(railwayDto.toString());

//        railwayRepository.

        var railway = mapper.toRailway(railwayDto);

        railwayRepository.save(railway);
    }

    public RailDto getNextRail(RailDto railDto) {
        Rail rail = railRepository.findById(railDto.getNext().getId()).orElse(null);
        if (rail != null) {
            return mapper.toSimpleRailDto(rail);
        }
        return null;
    }

    public RailDto getRandomStartingStation() {
        var railway = railwayRepository.findAll().iterator().next();

        RailDto railDto = null;
        if (railway != null) {
            RailwayDto railwayDto = mapper.toRailwayDto(railway);
            List<RailDto> list = railwayDto.getStationDtos();
            railDto = list.get(random.nextInt(list.size() - 1));
        }
        return railDto;
    }


    public RailDto getRandomEndingStation(RailDto begStationDto) {

        if (begStationDto.getNext() == null) {
            return begStationDto;
        }

        var railway = railwayRepository.findAll().iterator().next();
        RailDto stationDto = null;
        if (railway != null) {
            RailwayDto railwayDto = mapper.toRailwayDto(railway);
            List<RailDto> list = railwayDto.getStationDtos();

            int stationsCount = list.size();
            int beginningIndex = list.indexOf(begStationDto);
            stationDto = list.get(beginningIndex + 1 + random.nextInt(stationsCount - beginningIndex - 1));
        }

        return stationDto;
    }


}
