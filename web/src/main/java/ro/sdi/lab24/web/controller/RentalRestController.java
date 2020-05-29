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
import ro.sdi.lab24.core.service.ClientService;
import ro.sdi.lab24.web.converter.RentalConverter;
import ro.sdi.lab24.web.dto.RentalDTO;
import ro.sdi.lab24.web.dto.RentalsDTO;

import java.time.format.DateTimeFormatter;

@RestController
public class RentalRestController {
    private static final Logger log = LoggerFactory.getLogger(RentalRestController.class);
    public static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");

    @Autowired
    private ClientService clientService;

    @Autowired
    private RentalConverter rentalConverter;

    @RequestMapping(value = "/rentals", method = RequestMethod.GET)
    public RentalsDTO getRentals() {
        Iterable<Rental> rentals = clientService.getRentals();
        log.trace("fetch rentals: {}", rentals);
        return new RentalsDTO(rentalConverter.toDTOList(rentals));
    }

    @RequestMapping(value = "/rentals", method = RequestMethod.POST)
    public ResponseEntity<?> addRental(@RequestBody RentalDTO dto) {
        try {
            clientService.addRental(
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

    @RequestMapping(value = "/rentals/{rentalId}", method = RequestMethod.PUT)
    public ResponseEntity<?> updateRental(
            @PathVariable int rentalId,
            @RequestBody RentalDTO dto
    ) {

        try {
            clientService.updateRental(rentalId, dto.getTime());
        } catch (ElementNotFoundException e) {
            log.trace("rental id = {} could not be updated", rentalId);
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        log.trace("rental id = {} updated", rentalId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @RequestMapping(value = "/rentals/{rentalId}", method = RequestMethod.DELETE)
    public ResponseEntity<?> deleteRental(@PathVariable int rentalId) {
        try {
            clientService.deleteRental(rentalId);
        } catch (ElementNotFoundException e) {
            log.trace("rental id = {} could not be deleted", rentalId);
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        log.trace("rental id = {} deleted", rentalId);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
