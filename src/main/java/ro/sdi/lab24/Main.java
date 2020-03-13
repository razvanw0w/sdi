package ro.sdi.lab24;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Optional;
import java.util.Properties;

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
import ro.sdi.lab24.repository.MemoryRepository;
import ro.sdi.lab24.repository.Repository;
import ro.sdi.lab24.validation.ClientValidator;
import ro.sdi.lab24.validation.MovieValidator;
import ro.sdi.lab24.validation.RentalValidator;
import ro.sdi.lab24.view.Console;

public class Main
{
    public static void main(String[] args)
    {
        String repositoryType = "memory";

        try (InputStream inputStream = new FileInputStream("repository.properties"))
        {
            Properties properties = new Properties();
            properties.load(inputStream);
            repositoryType = Optional.ofNullable(properties.getProperty("type")).orElse("memory");
        }
        catch (IOException ignored)
        {
        }


        Repository<Integer, Client> clientRepository = null;
        Repository<Integer, Movie> movieRepository = null;
        Repository<Rental.RentalID, Rental> rentalRepository = null;

        ClientValidator clientValidator = new ClientValidator();
        MovieValidator movieValidator = new MovieValidator();
        RentalValidator rentalValidator = new RentalValidator();

        switch (repositoryType)
        {
            case "csv":
                clientRepository = new FileRepository<>(
                        "files/clients.csv",
                        new ClientCSVSerializer(),
                        clientValidator
                );
                movieRepository = new FileRepository<>(
                        "files/movies.csv",
                        new MovieCSVSerializer(),
                        movieValidator
                );
                rentalRepository = new FileRepository<>(
                        "files/rentals.csv",
                        new RentalCSVSerializer(),
                        rentalValidator
                );
                break;
            case "xml":
                //TODO
                break;
            case "db":
                //TODO
                break;
            default:
                clientRepository = new MemoryRepository<>();
                movieRepository = new MemoryRepository<>();
                rentalRepository = new MemoryRepository<>();
                break;
        }

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
