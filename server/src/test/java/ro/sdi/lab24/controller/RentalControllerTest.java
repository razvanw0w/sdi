package ro.sdi.lab24.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Iterator;
import java.util.stream.StreamSupport;

import ro.sdi.lab24.exception.AlreadyExistingElementException;
import ro.sdi.lab24.exception.ElementNotFoundException;
import ro.sdi.lab24.model.Client;
import ro.sdi.lab24.model.Movie;
import ro.sdi.lab24.model.Rental;
import ro.sdi.lab24.repository.MemoryRepository;
import ro.sdi.lab24.repository.Repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class RentalControllerTest {
    private RentalController controller;
    private Repository<Integer, Client> clientRepository;
    private Repository<Integer, Movie> movieRepository;
    private DateTimeFormatter formatter;

    private long length(Iterable<Rental> list) {
        return StreamSupport.stream(list.spliterator(), false).count();
    }

    @BeforeEach
    void setUp() {
        clientRepository = new MemoryRepository<Integer, Client>();
        movieRepository = new MemoryRepository<Integer, Movie>();
        clientRepository.save(new Client(1, "a"));
        clientRepository.save(new Client(2, "b"));
        clientRepository.save(new Client(3, "c"));
        movieRepository.save(new Movie(1, "x", "g1", 100));
        movieRepository.save(new Movie(2, "y", "g2", 75));
        movieRepository.save(new Movie(3, "z", "g1", 30));
        formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");
        /*controller = new RentalControllerImpl(clientRepository,
                                              movieRepository,
                                              new MemoryRepository<Rental.RentalID, Rental>(),
                                              new RentalValidator());*/
    }

    @Test
    void addRental() {
        assertThrows(ElementNotFoundException.class, () -> controller.addRental(4, 3, "01-01-2000 10:00"));
        assertThrows(ElementNotFoundException.class, () -> controller.addRental(3, 4, "01-01-2000 10:00"));

        Rental rental1 = new Rental(1, 1, LocalDateTime.parse("20-02-2010 10:00", formatter));
        controller.addRental(1, 1, "20-02-2010 10:00");
        assertEquals(length(controller.getRentals()), 1);
        assertEquals(controller.getRentals().iterator().next(), rental1);

        Rental rental2 = new Rental(1, 2, LocalDateTime.parse("10-10-2010 09:00", formatter));
        controller.addRental(1, 2, "10-10-2010 09:00");
        assertEquals(length(controller.getRentals()), 2);
        Iterator<Rental> iterator = controller.getRentals().iterator();
        assertEquals(iterator.next(), rental1);
        assertEquals(iterator.next(), rental2);

        assertThrows(AlreadyExistingElementException.class, () -> controller.addRental(1, 1, "01-01-2019 10:00"));
    }

    @Test
    void deleteRental() {
        Rental rental1 = new Rental(1, 1, LocalDateTime.parse("20-02-2010 10:00", formatter));
        controller.addRental(1, 1, "20-02-2010 10:00");
        Rental rental2 = new Rental(1, 2, LocalDateTime.parse("10-10-2010 09:00", formatter));
        controller.addRental(1, 2, "10-10-2010 09:00");

        assertThrows(ElementNotFoundException.class, () -> controller.deleteRental(4, 3));
        assertThrows(ElementNotFoundException.class, () -> controller.deleteRental(3, 4));

        controller.deleteRental(1, 1);
        assertEquals(length(controller.getRentals()), 1);
        assertEquals(controller.getRentals().iterator().next(), rental2);

        assertThrows(ElementNotFoundException.class, () -> controller.deleteRental(1, 1));
    }

    @Test
    void getRentals() {
        Rental rental1 = new Rental(1, 1, LocalDateTime.parse("20-02-2010 10:00", formatter));
        controller.addRental(1, 1, "20-02-2010 10:00");
        Rental rental2 = new Rental(1, 2, LocalDateTime.parse("10-10-2010 09:00", formatter));
        controller.addRental(1, 2, "10-10-2010 09:00");

        assertEquals(2, length(controller.getRentals()));
        Iterator<Rental> iterator = controller.getRentals().iterator();
        assertEquals(rental1, iterator.next());
        assertEquals(rental2, iterator.next());

        controller.deleteRental(1, 1);
        assertEquals(1, length(controller.getRentals()));
        assertEquals(controller.getRentals().iterator().next(), rental2);
    }

    @Test
    void updateRental() {
        Rental rental1 = new Rental(1, 1, LocalDateTime.parse("20-02-2010 10:00", formatter));
        controller.addRental(1, 1, "20-02-2010 10:00");
        Rental rental2 = new Rental(1, 2, LocalDateTime.parse("10-10-2010 09:00", formatter));
        controller.addRental(1, 2, "10-10-2010 09:00");
        Rental rentalUpdate = new Rental(1, 1, LocalDateTime.parse("24-12-2020 10:00", formatter));

        assertThrows(ElementNotFoundException.class, () -> controller.updateRental(4, 3, "02-02-2010 10:00"));
        assertThrows(ElementNotFoundException.class, () -> controller.updateRental(3, 4, "02-01-2010 10:00"));

        assertEquals(controller.getRentals().iterator().next(), rental1);
        controller.updateRental(1, 1, "24-12-2019 10:00");
        assertEquals(controller.getRentals().iterator().next(), rentalUpdate);

        assertThrows(ElementNotFoundException.class, () -> controller.updateRental(2, 1, "20-02-2019 10:30"));
    }
}