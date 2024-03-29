package ro.sdi.lab24.core.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
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
import ro.sdi.lab24.core.model.specification.ClientNameSpecification;
import ro.sdi.lab24.core.repository.ClientRepository;
import ro.sdi.lab24.core.validation.Validator;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Optional;

@Service
public class ClientService {
    private static final Logger log = LoggerFactory.getLogger(ClientService.class);

    @Autowired
    ClientRepository clientRepository;

    @Autowired
    Validator<Client> clientValidator;

    public DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");
    @PersistenceContext
    EntityManager entityManager;
    @Autowired
    Validator<Rental> rentalValidator;

    EntityDeletedListener<Client> entityDeletedListener = null;

    public void setEntityDeletedListener(EntityDeletedListener<Client> entityDeletedListener) {
        this.entityDeletedListener = entityDeletedListener;
    }

    /**
     * This function adds a client to the repository
     *
     * @param name: the name of the client
     * @throws AlreadyExistingElementException if the client (the ID) is already there
     */
    public void addClient(String name, int fidelity) {
        Client client = Client.builder().name(name).fidelity(fidelity).build();
        clientValidator.validate(client);
        log.trace("Adding client {}", client);
        clientRepository.save(client);
    }

    /**
     * This function removes a client from the repository based on their ID
     *
     * @param id: the ID of the client
     * @throws ElementNotFoundException if the client isn't found in the repository based on their ID
     */
    public void deleteClient(int id) {
        log.trace("Removing client with id {}", id);
        clientRepository.findById(id).orElseThrow(() -> new ElementNotFoundException(String.format(
                "Client %d does not exist",
                id
        )));
        clientRepository.deleteById(id);
    }

    /**
     * This function returns an iterable collection of the current state of the clients in the repository
     *
     * @return all: an iterable collection of clients
     */
    public Iterable<Client> getClients() {
        log.trace("Fetching all clients");
//        log.trace("name test {}", clientRepository.findByExactName("Razvan"));
//        log.trace("fidelity test {}", clientRepository.findByExactFidelity(3));
        return clientRepository.findAll();
    }

    public Page<Client> getClientsPaginated(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return clientRepository.findAll(pageable);
    }

    /**
     * This function updated a client based on their ID with a new name
     *
     * @param id:   the client's ID
     * @param name: the new name of the client
     * @throws ElementNotFoundException if the client isn't found in the repository based on their ID
     */
    public void updateClient(int id, String name, int fidelity) {

        Client client = clientRepository.findById(id).orElseThrow(() -> new ElementNotFoundException(String.format(
                "Client %d does not exist",
                id
        )));
        log.trace("Updating client {}", client);
        client.setName(name);
        client.setFidelity(fidelity);
        clientValidator.validate(client);
        clientRepository.save(client);
    }

    public Iterable<Client> filterClientsByName(String name) {
        log.trace("Filtering clients by the name {}", name);
        /*String regex = ".*" + name + ".*";
        return StreamSupport
                .stream(clientRepository.findAll().spliterator(), false)
                .filter(client -> client.getName().matches(regex))
                .collect(Collectors.toUnmodifiableList());*/
        return clientRepository.findByExactName(name);
    }

    public Iterable<Client> filterClientsByFidelity(Integer fidelity) {
        log.trace("Filtering clients by fidelity {}", fidelity);
        return clientRepository.findByExactFidelity(fidelity);
    }

    @Transactional
    public void addRental(int movieId, int clientId, String time) {
        Rental rental = new Rental(
                entityManager.getReference(Client.class, clientId),
                entityManager.getReference(Movie.class, movieId),
                LocalDateTime.parse(time, formatter));

        rentalValidator.validate(rental);
        log.trace("Adding rental {}", rental);
        clientRepository.findByIdWithRentals(clientId).orElseThrow(RuntimeException::new).getRentals().add(rental);
//        movieRepository.findByIdWithRentals(movieId).orElseThrow(RuntimeException::new).getRentals().add(rental);
    }

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

    public Iterable<Client> filterClientsByNamePaginated(String name, int page, int size) {
        log.trace("Filtering clients by the name {}", name);
        /*String regex = ".*" + name + ".*";
        return StreamSupport
                .stream(clientRepository.findAll().spliterator(), false)
                .filter(client -> client.getName().matches(regex))
                .collect(Collectors.toUnmodifiableList());*/
        Specification<Client> specification = new ClientNameSpecification(name);
        Pageable pageable = PageRequest.of(page, size);
        return clientRepository.findAll(specification, pageable);
    }

    public Optional<Client> findOne(int clientId) {
        return clientRepository.findById(clientId);
    }
}
