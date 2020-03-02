package ro.sdi.lab24.validation;

import ro.sdi.lab24.exception.ValidatorException;
import ro.sdi.lab24.model.Movie;

import java.util.Optional;

public class MovieValidator implements Validator<Movie> {
    /**
     * This function validates an entity which is supposed to be a movie
     * In order for a movie to be valid, its ID must be an non-negative number and its name must contain
     * only alphabetic characters
     *
     * @param entity: the supposed-to-be movie
     * @throws ValidatorException : thrown in case a validation error occurs with all the occured errors
     */
    @Override
    public void validate(Movie entity) throws ValidatorException {
        Optional.of(entity).filter(movie -> movie.getId() >= 0).orElseThrow(() -> new ValidatorException(String.format("movie %d has an invalid ID", entity.getId())));
        Optional.of(entity).filter(movie -> movie.getName().matches("^[a-zA-Z0-9]+$")).orElseThrow(() -> new ValidatorException(String.format("movie %d has an invalid name", entity.getId())));
    }
}
