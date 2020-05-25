package ro.sdi.lab24.core.validation;

import ro.sdi.lab24.core.exception.ValidatorException;
import ro.sdi.lab24.core.model.Rental;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.time.LocalDateTime;
import java.util.Optional;

public class RentalValidator implements Validator<Rental> {
    @PersistenceContext
    EntityManager entityManager;

    /**
     * This function validates an entity which is supposed to be a rental
     * In order for a rental to be valid, its IDs must be non-negative numbers and the rental date
     * should be less than today's date
     *
     * @param entity: the supposed-to-be rental
     * @throws ValidatorException : thrown in case a validation error occurs with all the occured errors
     */
    @Override
    public void validate(Rental entity) throws ValidatorException
    {
        Optional.of(entity).filter(rental -> rental.getTime().compareTo(LocalDateTime.now()) <= 0).orElseThrow(() -> new ValidatorException(String.format("Rental %s has an invalid movie ID", entity.toString())));
    }
}
