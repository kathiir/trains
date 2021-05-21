package app;

import app.config.SpringDataConfig;
import app.dto.RouteDto;
import app.mapper.Mapper;
import app.repository.EventRepository;
import app.services.EventService;
import app.services.RouteService;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackageClasses = {SpringDataConfig.class, EventService.class, RouteDto.class, EventRepository.class, Mapper.class})
public class SpringConsoleApplication {


    public static void main(String[] args) {
        ApplicationContext applicationContext = new AnnotationConfigApplicationContext(SpringConsoleApplication.class);
//        RouteService routeService = applicationContext.getBean(RouteService.class);
//        routeService.run();
//        System.out.println(routeService.getTrainLog());
//        RailwayService railwayService = applicationContext.getBean(RailwayService.class);
//        railwayService.

//        EventRepository eventRepository = applicationContext.getBean(EventRepository.class);

        RouteService routeService = applicationContext.getBean(RouteService.class);
        routeService.generateRandomRoute();
//        routeService.wait().
    }

}
