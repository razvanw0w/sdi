package ro.sdi.lab24;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import ro.sdi.lab24.view.Console;

import javax.xml.parsers.ParserConfigurationException;

public class Main {
    public static void main(String[] args) throws ParserConfigurationException {
        AnnotationConfigApplicationContext context =
                new AnnotationConfigApplicationContext(
                        "ro.sdi.lab24.client.config"
                );

        Console console = context.getBean(Console.class);
        console.run(args); // evident ca ii statica da voiam
        // sa fortez constructia autowired-urilor
    }
}
