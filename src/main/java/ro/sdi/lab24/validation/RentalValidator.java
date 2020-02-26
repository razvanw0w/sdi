package ro.sdi.lab24.validation;

import ro.sdi.lab24.model.Rental;

public class RentalValidator implements Validator<Rental>
{
    /**
     * This function validates an entity which is supposed to be a rental
     * In order for a rental to be valid, its IDs must be non-negative numbers and its name must contain
     * only alphabetic characters
     * @param entity: the supposed-to-be rental
     * @throws ValidatorException
     */
    @Override
    public void validate(Rental entity) throws ValidatorException
    {
        StringBuilder errorMessages = new StringBuilder();
        Rental.RentalID rentalID = entity.getId();
        if (rentalID.getClientId() < 0) {
            errorMessages.append("Rental ").append(entity.toString()).append("has an invalid client ID\n");
        }
        if (rentalID.getMovieId() < 0) {
            errorMessages.append("Rental ").append(entity.toString()).append("has an invalid movie ID\n");
        }
        if (errorMessages.length() > 0) {
            throw new ValidatorException(errorMessages.toString());
        }
    }
}
