package ro.sdi.lab24.client;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import ro.sdi.lab24.client.view.Console;

public class ClientApp {
    public static void main(String[] args) {
        AnnotationConfigApplicationContext context =
                new AnnotationConfigApplicationContext("ro.sdi.lab24.client.config");
        Console.run(args);
    }
}
