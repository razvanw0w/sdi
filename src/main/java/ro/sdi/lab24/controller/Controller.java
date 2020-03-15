package ro.sdi.lab24.controller;

import ro.sdi.lab24.controller.dto.RentedMovieStatistic;
import ro.sdi.lab24.model.Client;
import ro.sdi.lab24.model.Movie;
import ro.sdi.lab24.model.Rental;
import ro.sdi.lab24.repository.Repository;
import ro.sdi.lab24.validation.Validator;

import java.util.Comparator;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

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

    public Iterable<RentedMovieStatistic> getTop10RentedMovies() {
        Stream<Rental> rentalStream = StreamSupport.stream(rentalRepository.findAll().spliterator(), false);
        Map<String, Long> occurenceMap = rentalStream
                .map(rental -> movieRepository.findOne(rental.getId().getMovieId()).get().getName())
                .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));


        return occurenceMap
                .entrySet()
                .stream()
                .map(entry -> new RentedMovieStatistic(entry.getKey(), entry.getValue()))
                .sorted(Comparator.comparingLong(RentedMovieStatistic::getNumberOfRentals).reversed())
                .limit(10)
                .collect(Collectors.toUnmodifiableList());
    }
}
