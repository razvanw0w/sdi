package ro.sdi.lab24.core.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import ro.sdi.lab24.core.model.Client;
import ro.sdi.lab24.core.model.Movie;
import ro.sdi.lab24.core.model.Rental;
import ro.sdi.lab24.core.model.serialization.database.ClientTableAdapter;
import ro.sdi.lab24.core.model.serialization.database.MovieTableAdapter;
import ro.sdi.lab24.core.model.serialization.database.RentalTableAdapter;
import ro.sdi.lab24.core.service.dto.ClientGenre;
import ro.sdi.lab24.core.service.dto.RentedMovieStatistic;
import ro.sdi.lab24.core.validation.Validator;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.Comparator;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

@org.springframework.stereotype.Service
public class ReportService {
    private static final Logger log = LoggerFactory.getLogger(ReportService.class);

    @Autowired
    ClientTableAdapter clientRepository;

    @Autowired
    MovieTableAdapter movieRepository;

    @Autowired
    RentalTableAdapter rentalRepository;

    @Autowired
    Validator<Client> clientValidator;

    @Autowired
    Validator<Movie> movieValidator;

    @Autowired
    Validator<Rental> rentalValidator;

    @PersistenceContext
    EntityManager entityManager;

    public ReportService(
            ClientTableAdapter clientRepository,
            MovieTableAdapter movieRepository,
            RentalTableAdapter rentalRepository,
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
        log.trace("Fetching top 10 rented movies");
        Stream<Rental> rentalStream = StreamSupport.stream(rentalRepository.findAll().spliterator(), false);
        Map<String, Long> occurrenceMap = rentalStream
                .map(rental -> movieRepository.findById(rental.getMovie().getId()).get().getName())
                .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));


        return occurrenceMap
                .entrySet()
                .stream()
                .map(entry -> new RentedMovieStatistic(entry.getKey(), entry.getValue()))
                .sorted(Comparator.comparingLong(RentedMovieStatistic::getNumberOfRentals).reversed())
                .limit(10)
                .collect(Collectors.toUnmodifiableList());
    }

    /**
     * Retrieves the most rented genre for each client, or the empty string if the client did not rent any movies
     */
    public Iterable<ClientGenre> getClientGenres() {
        log.trace("Fetching client favorite genres");
        return StreamSupport.stream(clientRepository.findAll().spliterator(), false)
                .map(client -> new ClientGenre(client,
                        StreamSupport.stream(rentalRepository.findAll().spliterator(), false)
                                .filter(rental -> rental.getClient().getId() == client.getId())
                                        .map(this::getMovieGenre)
                                        .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()))
                                        .entrySet()
                                        .stream()
                                        .max(Map.Entry.comparingByValue())
                                        .map(Map.Entry::getKey)
                                        .orElse("")
                        )
                )
                .collect(Collectors.toUnmodifiableList());
    }

    private String getMovieGenre(Rental rental) {
        return StreamSupport.stream(movieRepository.findAll().spliterator(), false)
                .filter(movie -> movie.getId() == rental.getMovie().getId())
                .findAny()
                .map(Movie::getGenre)
                .orElse("");
    }
}
