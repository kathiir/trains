package app;

import app.mapper.Mapper;
import app.dto.RouteDto;

//import app.hiber.config.Config;
//import app.hiber.repository.EventRepository;
//import app.hiber.services.EventService;
//import app.hiber.services.RouteService;
import app.data.config.Config;
import app.data.repository.EventRepository;
import app.data.services.EventService;
import app.data.services.RouteService;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackageClasses = {Config.class, EventService.class, RouteDto.class, EventRepository.class, Mapper.class})
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
//        routeService.generateRandomRoute();
        routeService.run();
        System.out.println(routeService.getTrainLog());
//        routeService.wait().
    }

}
