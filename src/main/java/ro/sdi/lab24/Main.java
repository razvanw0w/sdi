package ro.sdi.lab24;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import ro.sdi.lab24.client.view.Console;
import ro.sdi.lab24.config.Config;

import javax.xml.parsers.ParserConfigurationException;
import java.util.concurrent.ExecutorService;

public class Main {
    public static void main(String[] args) throws ParserConfigurationException {
        AnnotationConfigApplicationContext context =
                new AnnotationConfigApplicationContext(Config.class.getPackageName());

        Console.run(args);
        context.getBean(ExecutorService.class).shutdown();
    }
}
