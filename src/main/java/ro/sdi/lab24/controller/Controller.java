package ro.sdi.lab24.controller;

import ro.sdi.lab24.model.Client;
import ro.sdi.lab24.model.Movie;
import ro.sdi.lab24.model.Rental;
import ro.sdi.lab24.repository.Repository;
import ro.sdi.lab24.validation.Validator;

public class Controller {
    Repository<Integer, Client> clientRepository;
    Repository<Integer, Movie> movieRepository;
    Repository<Rental.RentalID, Rental> rentalRepository;
    Validator<Client> clientValidator;
    Validator<Movie> movieValidator;
    Validator<Rental> rentalValidator;

    public Controller(
            Repository<Integer, Client> clientRepository,
            Repository<Integer, Movie> movieRepository,
            Repository<Rental.RentalID, Rental> rentalRepository,
            Validator<Client> clientValidator,
            Validator<Movie> movieValidator,
            Validator<Rental> rentalValidator
    ) {
        this.clientRepository = clientRepository;
        this.movieRepository = movieRepository;
        this.rentalRepository = rentalRepository;
        this.clientValidator = clientValidator;
        this.movieValidator = movieValidator;
        this.rentalValidator = rentalValidator;
    }
}
