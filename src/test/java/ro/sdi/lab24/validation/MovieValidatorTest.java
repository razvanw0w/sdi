package ro.sdi.lab24.validation;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import ro.sdi.lab24.model.Movie;

import static org.junit.jupiter.api.Assertions.*;

class MovieValidatorTest {
    private static MovieValidator validator;

    @BeforeAll
    static void setUp() {
        validator = new MovieValidator();
    }

    @Test
    void validate() {
        Movie movie1 = new Movie(1, "abc");
        Movie movie2 = new Movie(-1, "abc");
        Movie movie3 = new Movie(1, "sabaoisdjf0awd-");
        Movie movie4 = new Movie(-1, "abc0abc");

        assertDoesNotThrow(() -> {validator.validate(movie1);});
        assertThrows(ValidatorException.class, () -> {validator.validate(movie2);});
        assertThrows(ValidatorException.class, () -> {validator.validate(movie3);});
        assertThrows(ValidatorException.class, () -> {validator.validate(movie4);});
    }
}