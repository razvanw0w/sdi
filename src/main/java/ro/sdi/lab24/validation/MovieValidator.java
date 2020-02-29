package ro.sdi.lab24.validation;

import ro.sdi.lab24.exception.ValidatorException;
import ro.sdi.lab24.model.Movie;

public class MovieValidator implements Validator<Movie>
{
    /**
     * This function validates an entity which is supposed to be a movie
     * In order for a movie to be valid, its ID must be an non-negative number and its name must contain
     * only alphabetic characters
     * @param entity: the supposed-to-be movie
     * @throws ValidatorException : thrown in case a validation error occurs with all the occured errors
     */
    @Override
    public void validate(Movie entity) throws ValidatorException
    {
        StringBuilder errorMessages = new StringBuilder();
        if (entity.getId() < 0) {
            errorMessages.append("Movie ").append(entity.toString()).append(" has an invalid ID\n");
        }
        if (!entity.getName().matches("^[a-zA-Z]+$")) {
            errorMessages.append("Movie ").append(entity.toString()).append(" has an invalid name\n");
        }
        if (errorMessages.length() > 0) {
            throw new ValidatorException(errorMessages.toString());
        }
    }
}
