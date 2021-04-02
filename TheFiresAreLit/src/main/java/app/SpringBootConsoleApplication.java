package app;

import app.dto.Route;
import app.repository.EventRepository;
import app.services.RouteService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;

@ComponentScan(basePackageClasses = {RouteService.class, EventRepository.class, Route.class})
public class SpringBootConsoleApplication {

    private static final Logger LOG = LoggerFactory.getLogger(SpringBootConsoleApplication.class);

    public static void main(String[] args) {
        ApplicationContext applicationContext = new AnnotationConfigApplicationContext(SpringBootConsoleApplication.class);
        RouteService routeService = applicationContext.getBean(RouteService.class);
        routeService.run();
        System.out.println(routeService.getTrainLog());
    }

}
