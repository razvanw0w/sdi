package ro.sdi.lab24;

import ro.sdi.lab24.controller.ClientController;
import ro.sdi.lab24.controller.Controller;
import ro.sdi.lab24.controller.MovieController;
import ro.sdi.lab24.controller.RentalController;
import ro.sdi.lab24.model.Client;
import ro.sdi.lab24.model.Movie;
import ro.sdi.lab24.model.Rental;
import ro.sdi.lab24.repository.InMemoryRepository;
import ro.sdi.lab24.repository.Repository;
import ro.sdi.lab24.validation.ClientValidator;
import ro.sdi.lab24.validation.MovieValidator;
import ro.sdi.lab24.validation.RentalValidator;
import ro.sdi.lab24.view.Console;

public class Main
{
    public static void main(String[] args)
    {
        Repository<Integer, Client> clientRepository = new InMemoryRepository<>(new ClientValidator());
        Repository<Integer, Movie> movieRepository= new InMemoryRepository<>(new MovieValidator());
        Repository<Rental.RentalID, Rental> rentalRepository= new InMemoryRepository<>(new RentalValidator());

        Controller controller = new Controller(clientRepository, movieRepository, rentalRepository);
        ClientController clientController = new ClientController(clientRepository);
        MovieController movieController = new MovieController(movieRepository);
        RentalController rentalController = new RentalController(clientRepository, movieRepository, rentalRepository);
        Console console = new Console(controller, clientController, movieController, rentalController);

        console.run(args);
    }
}
