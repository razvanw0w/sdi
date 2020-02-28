package ro.sdi.lab24.validation;

import ro.sdi.lab24.model.Client;

public class ClientValidator implements Validator<Client>
{
    /**
     * This function validates an entity which is supposed to be a client
     * In order for a client to be valid, its ID must be an non-negative number and its name must contain
     * only alphabetic characters
     * @param entity: the supposed-to-be client
     * @throws ValidatorException: thrown in case a validation error occurs with all the occured errors
     */
    @Override
    public void validate(Client entity) throws ValidatorException
    {
        StringBuilder errorMessages = new StringBuilder();
        if (entity.getId() < 0) {
            errorMessages.append("Client ").append(entity.toString()).append(" has an invalid ID\n");
        }
        if (!entity.getName().matches("^[a-zA-Z]+$")) {
            errorMessages.append("Client ").append(entity.toString()).append(" has an invalid name\n");
        }
        if (errorMessages.length() > 0) {
            throw new ValidatorException(errorMessages.toString());
        }
    }
}
