package ro.sdi.lab24.validation;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import ro.sdi.lab24.exception.ValidatorException;
import ro.sdi.lab24.model.Client;

import static org.junit.jupiter.api.Assertions.*;

class ClientValidatorTest {
    private static ClientValidator validator;

    @BeforeAll
    static void setUp() {
        validator = new ClientValidator();
    }

    @Test
    void validate() {
        Client client1 = new Client(1, "abc");
        Client client2 = new Client(-1, "abc");
        Client client3 = new Client(1, "sabaoisdjf0awd-");
        Client client4 = new Client(-1, "abc0abc");

        assertDoesNotThrow(() -> {validator.validate(client1);});
        assertThrows(ValidatorException.class, () -> {validator.validate(client2);});
        assertThrows(ValidatorException.class, () -> {validator.validate(client3);});
        assertThrows(ValidatorException.class, () -> {validator.validate(client4);});
    }
}