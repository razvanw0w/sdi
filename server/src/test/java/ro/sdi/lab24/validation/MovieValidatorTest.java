package ro.sdi.lab24.validation;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import ro.sdi.lab.common.exception.ValidatorException;
import ro.sdi.lab.common.model.Movie;
import ro.sdi.lab.server.validation.MovieValidator;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

class MovieValidatorTest {
    private static MovieValidator validator;

    @BeforeAll
    static void setUp() {
        validator = new MovieValidator();
    }

    @Test
    void validate() {
        Movie movie1 = new Movie(1, "abc", "g1", 100);
        Movie movie2 = new Movie(-1, "abc", "g1", 100);
        Movie movie3 = new Movie(1, "sabaoisdjf0awd", "g1", 100);
        Movie movie4 = new Movie(-1, "abc!abc", "g1", 100);
        Movie movie5 = new Movie(-1, "abc", "g1", -1);
        Movie movie6 = new Movie(-1, "abc", "g1", 101);
        Movie movie7 = new Movie(1, "abc-", "g1", -1);
        Movie movie8 = new Movie(1, "abc-", "g1", 101);
        Movie movie9 = new Movie(-1, "abc-", "g1", 101);

        assertDoesNotThrow(() -> {
            validator.validate(movie1);
        });
        assertThrows(ValidatorException.class, () -> {
            validator.validate(movie2);
        });
        assertDoesNotThrow(() -> validator.validate(movie3));
        assertThrows(ValidatorException.class, () -> {
            validator.validate(movie4);
        });
        assertThrows(ValidatorException.class, () -> {
            validator.validate(movie5);
        });
        assertThrows(ValidatorException.class, () -> {
            validator.validate(movie6);
        });
        assertThrows(ValidatorException.class, () -> {
            validator.validate(movie7);
        });
        assertThrows(ValidatorException.class, () -> {
            validator.validate(movie8);
        });
        assertThrows(ValidatorException.class, () -> {
            validator.validate(movie9);
        });
    }
}