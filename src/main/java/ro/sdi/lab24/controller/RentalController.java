package ro.sdi.lab24.controller;

import ro.sdi.lab24.exception.AlreadyExistingElementException;
import ro.sdi.lab24.exception.ElementNotFoundException;
import ro.sdi.lab24.model.Client;
import ro.sdi.lab24.model.Movie;
import ro.sdi.lab24.model.Rental;
import ro.sdi.lab24.repository.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class RentalController
{
    Repository<Integer, Client> clientRepository;
    Repository<Integer, Movie> movieRepository;
    Repository<Rental.RentalID, Rental> rentalRepository;
    private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");

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

    /**
     * This function adds a rental to the repository
     *
     * @param movieId:  the ID of the movie
     * @param clientId: the ID of the client
     * @param time:     date and time of rental
     * @throws ElementNotFoundException        if movie or client doesn't exist in the repository
     * @throws AlreadyExistingElementException if the rental already exists in the repository
     */
    public void addRental(int movieId, int clientId, String time)
    {
        checkRentalID(movieId, clientId);
        Rental rental = new Rental(movieId, clientId, LocalDateTime.parse(time, formatter));
        rentalRepository.save(rental).ifPresent(opt ->
        {
            throw new AlreadyExistingElementException("Rental of movie " +
                    Integer.toString(movieId) +
                    " and client " + Integer.toString(clientId) +
                    " already exists");
        });
    }

    private void checkRentalID(int movieId, int clientId)
    {
        movieRepository.findOne(movieId).orElseThrow(() -> new ElementNotFoundException(String.format("Movie %d does not exists", movieId)));
        clientRepository.findOne(clientId).orElseThrow(() -> new ElementNotFoundException(String.format("Client %d does not exists", clientId)));
    }

    /**
     * This function deletes a rental from the repository
     *
     * @param movieId:  the ID of the movie
     * @param clientId: the ID of the client
     * @throws ElementNotFoundException if the movie, client don't exist of if the rental itself doesn't exist in the repository
     */
    public void deleteRental(int movieId, int clientId)
    {
        checkRentalID(movieId, clientId);
        rentalRepository.delete(new Rental.RentalID(movieId, clientId)).orElseThrow(() -> new ElementNotFoundException(String.format("Rental of movie %d and client %d does not exist", movieId, clientId)));
    }

    /**
     * This function returns an iterable collection of the current state of the rentals in the repository
     *
     * @return all: an iterable collection of rentals
     */
    public Iterable<Rental> getRentals()
    {
        return rentalRepository.findAll();
    }

    /**
     * This function updates a rental based on the movie ID and client ID with its new time
     *
     * @param movieId:  the ID of the movie
     * @param clientId: the ID of the client
     * @param time:     date and time - the object of updation
     * @throws ElementNotFoundException if the movie or client does not exist in the repository or
     *                                  if the rental is nowhere to be found
     */
    public void updateRental(int movieId, int clientId, String time)
    {
        checkRentalID(movieId, clientId);
        Rental rental = new Rental(movieId, clientId, LocalDateTime.parse(time, formatter));
        rentalRepository.update(rental).orElseThrow(() -> new ElementNotFoundException(String.format("Rental of movie %d and client %d does not exist", movieId, clientId)));
    }
}
