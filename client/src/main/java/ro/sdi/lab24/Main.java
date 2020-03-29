package ro.sdi.lab24;

import ro.sdi.lab24.controller.ClientController;
import ro.sdi.lab24.controller.Controller;
import ro.sdi.lab24.controller.MovieController;
import ro.sdi.lab24.controller.RentalController;
import ro.sdi.lab24.view.Console;

import javax.xml.parsers.ParserConfigurationException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Main {
    public static void main(String[] args) throws ParserConfigurationException {
        ExecutorService executorService = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
        Controller controller = null;
        ClientController clientController = null;
        MovieController movieController = null;
        RentalController rentalController = null;

        Console.initialize(
                controller,
                clientController,
                movieController,
                rentalController
        );

        Console.run(args);
        executorService.shutdown();
    }
}
