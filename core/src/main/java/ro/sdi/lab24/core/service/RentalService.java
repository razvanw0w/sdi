package ro.sdi.lab24.core.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ro.sdi.lab24.core.exception.AlreadyExistingElementException;
import ro.sdi.lab24.core.exception.DateTimeInvalidException;
import ro.sdi.lab24.core.exception.ElementNotFoundException;
import ro.sdi.lab24.core.model.Client;
import ro.sdi.lab24.core.model.Movie;
import ro.sdi.lab24.core.model.Rental;
import ro.sdi.lab24.core.model.specification.RentalDateSpecification;
import ro.sdi.lab24.core.repository.ClientRepository;
import ro.sdi.lab24.core.repository.MovieRepository;
import ro.sdi.lab24.core.repository.RentalRepository;
import ro.sdi.lab24.core.validation.Validator;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class RentalService {
    private static final Logger log = LoggerFactory.getLogger(RentalService.class);

    @Autowired
    ClientRepository clientRepository;

    @Autowired
    MovieRepository movieRepository;

    @Autowired
    RentalRepository rentalRepository;

    @Autowired
    Validator<Rental> rentalValidator;

    @PersistenceContext
    EntityManager entityManager;

    public DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");

    /**
     * This function adds a rental to the repository
     *
     * @param movieId:  the ID of the movie
     * @param clientId: the ID of the client
     * @param time:     date and time of rental
     * @throws ElementNotFoundException        if movie or client doesn't exist in the repository
     * @throws AlreadyExistingElementException if the rental already exists in the repository
     * @throws DateTimeInvalidException        if the date and time cannot be parsed
     */
    @Transactional
    public void addRental(int movieId, int clientId, String time) {
        checkRentalID(movieId, clientId);
        Rental rental = new Rental(
                entityManager.getReference(Client.class, clientId),
                entityManager.getReference(Movie.class, movieId),
                LocalDateTime.parse(time, formatter));

        rentalValidator.validate(rental);
        log.trace("Adding rental {}", rental);
        clientRepository.findByIdWithRentals(clientId).orElseThrow(RuntimeException::new).getRentals().add(rental);
//        movieRepository.findByIdWithRentals(movieId).orElseThrow(RuntimeException::new).getRentals().add(rental);
    }

    private void checkRentalID(int movieId, int clientId) {
        movieRepository.findById(movieId)
                .orElseThrow(() -> new ElementNotFoundException(String.format(
                        "Movie %d does not exist",
                        movieId
                )));
        clientRepository.findById(clientId)
                .orElseThrow(() -> new ElementNotFoundException(String.format(
                        "Client %d does not exist",
                        clientId
                )));
    }

    /**
     * This function deletes a rental from the repository
     */
    @Transactional
    public void deleteRental(int rentalId) {
        Optional<Rental> optionalRental = clientRepository.findAllClientsWithRentals()
                .stream()
                .map(client -> client.getRentals())
                .flatMap(rentals -> rentals.stream())
                .filter(rental -> rental.getId().equals(rentalId))
                .findFirst();
        optionalRental.ifPresent(
                rental -> {
                    Optional<Client> client = clientRepository.findByIdWithRentals(rental.getClient().getId());
                    client.ifPresent(c -> c.getRentals().remove(rental));
                }
        );
    }

    /**
     * This function returns an iterable collection of the current state of the rentals in the repository
     *
     * @return all: an iterable collection of rentals
     */
    public Iterable<Rental> getRentals() {
        log.trace("Fetching all rentals");
        return clientRepository.findAllClientsWithRentals()
                .stream()
                .map(client -> client.getRentals())
                .reduce(new ArrayList<>(), (a, b) -> {
                    a.addAll(b);
                    return a;
                });
    }

    public Iterable<Rental> getRentalsPaginated(int page, int size) {
//        Pageable pageable = PageRequest.of(page, size);
//        return rentalRepository.findAll(pageable);
        return clientRepository.findAllClientsWithRentals()
                .stream()
                .map(Client::getRentals)
                .reduce(new ArrayList<>(), (a, b) -> {
                    a.addAll(b);
                    return a;
                });
    }

    /**
     * This function updates a rental based on the movie ID and client ID with its new time
     *
     * @param time:     date and time - the object of updation
     * @throws ElementNotFoundException if the movie or client does not exist in the repository or
     *                                  if the rental is nowhere to be found
     */
    @Transactional
    public void updateRental(int rentalId, String time) {
        LocalDateTime dateTime;
        try {
            dateTime = LocalDateTime.parse(time, formatter);
        } catch (DateTimeParseException e) {
            throw new DateTimeInvalidException("Date and time invalid");
        }
        clientRepository.findAllClientsWithRentals().stream()
                .map(client -> client.getRentals())
                .reduce(new ArrayList<>(), (a, b) -> {
                    a.addAll(b);
                    return a;
                })
                .stream()
                .filter(rental -> rental.getId().equals(rentalId))
                .findFirst()
                .orElseThrow(RuntimeException::new)
                .setTime(dateTime);
    }

    public Iterable<Rental> filterRentalsByMovieName(String name) {
        String regex = ".*" + name + ".*";
        log.trace("Filtering rentals by the movie name {}", name);
        return StreamSupport.stream(rentalRepository.findAll().spliterator(), false)
                .filter(rental -> movieRepository.findById(rental.getMovie().getId())
                        .filter(t -> t.getName()
                                .matches(regex))
                        .isPresent())
                .collect(Collectors.toUnmodifiableList());
    }

    public Iterable<Rental> filterRentalsByDate(LocalDateTime time) {
        log.trace("Filtering rentals by date {}", time);

        Specification<Rental> specification = new RentalDateSpecification(time);
        Iterable<Rental> all = rentalRepository.findAll(specification);
        log.trace("filtered by date {}", all);
        return all;
    }

    public Iterable<Rental> filterRentalsByDatePaginated(LocalDateTime time, int page, int size) {
        log.trace("Filtering rentals by date {} page = {} size = {}", time, page, size);
        Specification<Rental> specification = new RentalDateSpecification(time);
        Pageable pageable = PageRequest.of(page, size);
        Iterable<Rental> all = rentalRepository.findAll(specification, pageable);
        log.trace("filtered by date {}", all);
        return all;
    }
}
