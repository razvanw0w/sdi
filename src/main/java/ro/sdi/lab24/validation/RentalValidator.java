package ro.sdi.lab24.validation;

import ro.sdi.lab24.model.Rental;

import java.time.LocalDateTime;

public class RentalValidator implements Validator<Rental>
{
    /**
     * This function validates an entity which is supposed to be a rental
     * In order for a rental to be valid, its IDs must be non-negative numbers and the rental date
     * should be less than today's date
     * @param entity: the supposed-to-be rental
     * @throws ValidatorException
     */
    @Override
    public void validate(Rental entity) throws ValidatorException
    {
        StringBuilder errorMessages = new StringBuilder();
        Rental.RentalID rentalID = entity.getId();
        if (rentalID.getClientId() < 0) {
            errorMessages.append("Rental ").append(entity.toString()).append(" has an invalid client ID\n");
        }
        if (rentalID.getMovieId() < 0) {
            errorMessages.append("Rental ").append(entity.toString()).append(" has an invalid movie ID\n");
        }
        if (entity.getTime().compareTo(LocalDateTime.now()) > 0) {
            errorMessages.append("Rental ").append(entity.toString()).append(" has an invalid date\n");
        }
        if (errorMessages.length() > 0) {
            throw new ValidatorException(errorMessages.toString());
        }
    }
}
