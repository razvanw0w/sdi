package ro.sdi.lab24;

import ro.sdi.lab24.controller.ClientController;
import ro.sdi.lab24.controller.Controller;
import ro.sdi.lab24.controller.MovieController;
import ro.sdi.lab24.controller.RentalController;
import ro.sdi.lab24.model.Client;
import ro.sdi.lab24.model.Movie;
import ro.sdi.lab24.model.Rental;
import ro.sdi.lab24.model.serialization.ClientCSVSerializer;
import ro.sdi.lab24.model.serialization.MovieCSVSerializer;
import ro.sdi.lab24.model.serialization.RentalCSVSerializer;
import ro.sdi.lab24.repository.FileRepository;
import ro.sdi.lab24.repository.Repository;
import ro.sdi.lab24.validation.ClientValidator;
import ro.sdi.lab24.validation.MovieValidator;
import ro.sdi.lab24.validation.RentalValidator;
import ro.sdi.lab24.view.Console;

public class Main
{
    public static void main(String[] args)
    {
        /*Repository<Integer, Client> clientRepository = new MemoryRepository<>();
        Repository<Integer, Movie> movieRepository = new MemoryRepository<>();
        Repository<Rental.RentalID, Rental> rentalRepository = new MemoryRepository<>();*/

        ClientValidator clientValidator = new ClientValidator();
        MovieValidator movieValidator = new MovieValidator();
        RentalValidator rentalValidator = new RentalValidator();
        Repository<Integer, Client> clientRepository = new FileRepository<>(
                "files/clients.txt",
                new ClientCSVSerializer(),
                clientValidator
        );
        Repository<Integer, Movie> movieRepository = new FileRepository<>(
                "files/movies.txt",
                new MovieCSVSerializer(),
                movieValidator
        );
        Repository<Rental.RentalID, Rental> rentalRepository = new FileRepository<>(
                "files/rentals.txt",
                new RentalCSVSerializer(),
                rentalValidator
        );

        Controller controller = new Controller(clientRepository, movieRepository, rentalRepository,
                                               clientValidator,
                                               movieValidator,
                                               rentalValidator
        );
        ClientController clientController = new ClientController(
                clientRepository,
                clientValidator
        );
        MovieController movieController = new MovieController(
                movieRepository,
                movieValidator
        );
        RentalController rentalController = new RentalController(
                clientRepository,
                movieRepository,
                rentalRepository,
                rentalValidator
        );
        Console.initialize(
                controller,
                clientController,
                movieController,
                rentalController
        );

        Console.run(args);
    }
}
