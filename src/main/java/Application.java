import org.apache.commons.lang3.time.StopWatch;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.ComponentScan;


/**
 * User : Emmanuel PIERRE
 * Date: 30/05/14
 * Time: 15:26
 */
@ComponentScan
@EnableAutoConfiguration
public class Application {

    private static Logger logger = LoggerFactory.getLogger(Application.class);

    public static void main(String[] args) {
        StopWatch stopWatch = new StopWatch();
        logger.debug("STARTING STOPWATCH");
        stopWatch.start();

        ApplicationContext ctx = SpringApplication.run(Application.class, args);

        logger.debug("STOPPING STOPWATCH");
        stopWatch.stop();
        logger.debug("Stopwatch time: " + stopWatch);


    }

}
