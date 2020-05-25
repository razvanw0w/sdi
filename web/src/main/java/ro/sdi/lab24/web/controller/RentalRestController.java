package ro.sdi.lab24.web.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.*;
import ro.sdi.lab24.core.exception.AlreadyExistingElementException;
import ro.sdi.lab24.core.exception.ElementNotFoundException;
import ro.sdi.lab24.core.model.Rental;
import ro.sdi.lab24.core.service.RentalService;
import ro.sdi.lab24.web.converter.RentalConverter;
import ro.sdi.lab24.web.dto.RentalDTO;
import ro.sdi.lab24.web.dto.RentalsDTO;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@RestController
public class RentalRestController {
    private static final Logger log = LoggerFactory.getLogger(RentalRestController.class);
    public static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");

    @Autowired
    private RentalService rentalService;

    @Autowired
    private RentalConverter rentalConverter;

    @RequestMapping(value = "/rentals", method = RequestMethod.GET)
    public RentalsDTO getRentals() {
        Iterable<Rental> rentals = rentalService.getRentals();
        log.trace("fetch rentals: {}", rentals);
        return new RentalsDTO(rentalConverter.toDTOList(rentals));
    }

    @RequestMapping(value = "/rentals/page={page}&size={size}", method = RequestMethod.GET)
    public RentalsDTO getRentals(@PathVariable int page, @PathVariable int size) {
        Iterable<Rental> rentals = rentalService.getRentalsPaginated(page, size);
        log.trace("fetch rentals: {} page = {} size = {}", rentals, page, size);
        return new RentalsDTO(rentalConverter.toDTOList(rentals));
    }

    @RequestMapping(value = "/rentals", method = RequestMethod.POST)
    public ResponseEntity<?> addRental(@RequestBody RentalDTO dto) {
        try {
            rentalService.addRental(
                    dto.getMovieId(),
                    dto.getClientId(),
                    dto.getTime()
            );
        } catch (AlreadyExistingElementException e) {
            log.trace("rental already exists");
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        log.trace("rental added");
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @RequestMapping(value = "/rentals/{rentalId}", method = RequestMethod.DELETE)
    public ResponseEntity<?> deleteRental(@PathVariable int rentalId) {
        try {
            rentalService.deleteRental(rentalId);
        } catch (ElementNotFoundException e) {
            log.trace("rental id = {} could not be deleted", rentalId);
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        log.trace("rental id = {} deleted", rentalId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @RequestMapping(value = "/rentals/{rentalId}", method = RequestMethod.PUT)
    public ResponseEntity<?> updateRental(
            @PathVariable int rentalId,
            @RequestBody RentalDTO dto
    ) {

        try {
            rentalService.updateRental(rentalId, dto.getTime());
        } catch (ElementNotFoundException e) {
            log.trace("rental id = {} could not be updated", rentalId);
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        log.trace("rental id = {} updated", rentalId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @RequestMapping(value = "/rentals/filter/{name}", method = RequestMethod.GET)
    public RentalsDTO filterRentalsByMovieName(@PathVariable String name) {
        log.trace("filtered rentals by movie name = {}", name);
        return new RentalsDTO(
                rentalConverter.toDTOList(
                        rentalService.filterRentalsByMovieName(name)
                )
        );
    }

    @RequestMapping(value = "/rentals/filter/date/{date}", method = RequestMethod.GET)
    public RentalsDTO filterRentalsByDate(@PathVariable String date) {
        log.trace("filtered rentals by date = {}", date);
        String time = date + " 00:00";
        return new RentalsDTO(rentalConverter.toDTOList(
                rentalService.filterRentalsByDate(LocalDateTime.parse(time, formatter))
        )
        );
    }

    @RequestMapping(value = "/rentals/filter-paginated/date/{date}&page={page}&size={size}", method = RequestMethod.GET)
    public RentalsDTO filterRentalsByDate(@PathVariable String date, @PathVariable int page, @PathVariable int size) {
        log.trace("filtered rentals by date = {} page = {} size = {}", date, page, size);
        String time = date + " 00:00";
        return new RentalsDTO(rentalConverter.toDTOList(
                rentalService.filterRentalsByDate(LocalDateTime.parse(time, formatter))
        )
        );
    }
}
