package ro.sdi.lab24.client.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import ro.sdi.lab24.core.exception.AlreadyExistingElementException;
import ro.sdi.lab24.core.exception.DateTimeInvalidException;
import ro.sdi.lab24.core.exception.ElementNotFoundException;
import ro.sdi.lab24.core.model.Rental;
import ro.sdi.lab24.web.converter.RentalConverter;
import ro.sdi.lab24.web.dto.RentalsDTO;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class RentalRestTemplateController {
    public static final String url = "http://localhost:8080/api/rentals";
    public static final Logger log = LoggerFactory.getLogger(RentalRestTemplateController.class);
    public DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");
    @Autowired
    private RestTemplate restTemplate;
    @Autowired
    private RentalConverter rentalConverter;

    public void addRental(int movieId, int clientId, String time) {
        Rental rental;
        try {
            rental = new Rental(movieId, clientId, LocalDateTime.parse(time, formatter));
        } catch (DateTimeParseException e) {
            throw new DateTimeInvalidException("Invalid date and time for rental!");
        }

        try {
            restTemplate.postForEntity(url, rentalConverter.toDTO(rental), Object.class);
            log.trace("rental {} added", rental);
        } catch (RestClientException e) {
            log.trace("rental {} could not be added", rental);
            throw new AlreadyExistingElementException(e.getMessage());
        }
    }

    public void deleteRental(int movieId, int clientId) {
        try {
            restTemplate.delete(String.format("%s/%d&%d", url, movieId, clientId));
            log.trace("rental with id = {}&{} deleted", movieId, clientId);
        } catch (RestClientException e) {
            log.trace("rental with id = {}&{} was not deleted", movieId, clientId);
            throw new ElementNotFoundException("Rental does not exist!");
        }
    }

    public Iterable<Rental> getRentals() {
        RentalsDTO dto = restTemplate.getForObject(url, RentalsDTO.class);
        assert dto != null;
        List<Rental> rentals = dto.getRentals()
                .stream()
                .map(rentalConverter::toModel)
                .collect(Collectors.toList());
        log.trace("fetched rentals :{}", rentals);
        return rentals;
    }

    public void updateRental(int movieId, int clientId, String time) {
        Rental rental;
        try {
            rental = new Rental(movieId, clientId, LocalDateTime.parse(time, formatter));
        } catch (DateTimeParseException e) {
            throw new DateTimeInvalidException("Invalid date and time for rental!");
        }
        try {
            restTemplate.put(String.format("%s/%d&%d", url, movieId, clientId), rentalConverter.toDTO(rental));
            log.trace("updated rental id = {}&{}: {}", movieId, clientId, rental);
        } catch (RestClientException e) {
            log.trace("rental {} was not updated", rental);
            throw new ElementNotFoundException("Rental does not exist!");
        }
    }

    public Iterable<Rental> filterRentalsByMovieName(String name) {
        RentalsDTO dto = restTemplate.getForObject(url + "/filter/" + name, RentalsDTO.class);
        assert dto != null;
        List<Rental> rentals = dto.getRentals()
                .stream()
                .map(rentalConverter::toModel)
                .collect(Collectors.toList());
        log.trace("rentals filtered by name = {}: {}", name, rentals);
        return rentals;
    }
}
