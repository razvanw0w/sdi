package ro.sdi.lab24.validation;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import ro.sdi.lab24.exception.ValidatorException;
import ro.sdi.lab24.model.Rental;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class RentalValidatorTest {
    private static RentalValidator validator;

    @BeforeAll
    static void setUp() {
        validator = new RentalValidator();
    }

    @Test
    void validate() {
        Rental rental1 = new Rental(1, 0, LocalDateTime.now());
        Rental rental2 = new Rental(-1, 0, LocalDateTime.now());
        Rental rental3 = new Rental(2, -1, LocalDateTime.now());
        Rental rental4 = new Rental(1, 0, LocalDateTime.of(2021, 1, 1, 1, 1));
        Rental rental5 = new Rental(-1, -1, LocalDateTime.now());
        Rental rental6 = new Rental(-1, 0, LocalDateTime.of(2021, 1, 1, 1, 1));
        Rental rental7 = new Rental(1, -1, LocalDateTime.of(2021, 1, 1, 1, 1));
        Rental rental8 = new Rental(-1, -1, LocalDateTime.of(2021, 1, 1, 1, 1));

        assertDoesNotThrow(() -> {validator.validate(rental1);});
        assertThrows(ValidatorException.class, () -> {validator.validate(rental2);});
        assertThrows(ValidatorException.class, () -> {validator.validate(rental3);});
        assertThrows(ValidatorException.class, () -> {validator.validate(rental4);});
        assertThrows(ValidatorException.class, () -> {validator.validate(rental5);});
        assertThrows(ValidatorException.class, () -> {validator.validate(rental6);});
        assertThrows(ValidatorException.class, () -> {validator.validate(rental7);});
        assertThrows(ValidatorException.class, () -> {validator.validate(rental8);});
    }
}