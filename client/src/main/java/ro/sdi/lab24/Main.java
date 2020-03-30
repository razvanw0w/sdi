package ro.sdi.lab24;

import ro.sdi.lab24.controller.*;
import ro.sdi.lab24.view.Console;

import javax.xml.parsers.ParserConfigurationException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

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

        Console.run(args);
        executorService.shutdown();
    }
}
