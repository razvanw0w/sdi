package ro.sdi.lab24.controller;

import ro.sdi.lab24.model.Client;
import ro.sdi.lab24.model.Movie;
import ro.sdi.lab24.model.Rental;
import ro.sdi.lab24.repository.Repository;

public class RentalController
{
    Repository<Integer, Client> clientRepository;
    Repository<Integer, Movie> movieRepository;
    Repository<Rental.RentalID, Rental> rentalRepository;

    public RentalController(
            Repository<Integer, Client> clientRepository,
            Repository<Integer, Movie> movieRepository,
            Repository<Rental.RentalID, Rental> rentalRepository
    )
    {
        this.clientRepository = clientRepository;
        this.movieRepository = movieRepository;
        this.rentalRepository = rentalRepository;
    }

    public void addRental(int movieId, int clientId, String time)
    {

    }

    public void deleteRental(int movieId, int clientId)
    {

    }

    public Iterable<Rental> getRentals()
    {
        return null;
    }

    public void updateRental(int movieId, int clientId, String time)
    {

    }

    //TODO Razvan
}
