package ro.sdi.lab24;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.xml.parsers.ParserConfigurationException;

import ro.sdi.lab24.controller.ClientController;
import ro.sdi.lab24.controller.ClientControllerImpl;
import ro.sdi.lab24.controller.Controller;
import ro.sdi.lab24.controller.ControllerImpl;
import ro.sdi.lab24.controller.MovieController;
import ro.sdi.lab24.controller.MovieControllerImpl;
import ro.sdi.lab24.controller.RentalController;
import ro.sdi.lab24.controller.RentalControllerImpl;
import ro.sdi.lab24.view.Console;

public class Main {
    public static void main(String[] args) throws ParserConfigurationException {
        ExecutorService executorService = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
        Controller controller = new ControllerImpl(executorService);
        ClientController clientController = new ClientControllerImpl(executorService);
        MovieController movieController = new MovieControllerImpl(executorService);
        RentalController rentalController = new RentalControllerImpl(executorService);

        Console.initialize(
                controller,
                clientController,
                movieController,
                rentalController
        );

        executorService.submit(new ResponseDaemon(Console.responseBuffer));
        Console.run(args);
        executorService.shutdown();
    }
}
