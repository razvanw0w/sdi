package ro.sdi.lab24.core.validation;

import ro.sdi.lab24.core.exception.ValidatorException;
import ro.sdi.lab24.core.model.Client;

import java.util.Optional;

public class ClientValidator implements Validator<Client> {
    /**
     * This function validates an entity which is supposed to be a client
     * In order for a client to be valid, its ID must be an non-negative number and its name must contain
     * only alphabetic characters
     *
     * @param entity: the supposed-to-be client
     * @throws ValidatorException : thrown in case a validation error occurs with all the occured errors
     */
    @Override
    public void validate(Client entity) throws ValidatorException {
        Optional.of(entity).filter(client -> client.getName().matches("^[a-zA-Z]+$")).orElseThrow(() -> new ValidatorException(String.format("Client %d has an invalid name", entity.getId())));
    }
}
