package ro.sdi.lab24.controller;

import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import ro.sdi.lab24.exception.AlreadyExistingElementException;
import ro.sdi.lab24.exception.ElementNotFoundException;
import ro.sdi.lab24.model.Client;
import ro.sdi.lab24.repository.Repository;
import ro.sdi.lab24.validation.Validator;

public class ClientControllerImpl implements ClientController
{
    Repository<Integer, Client> clientRepository;
    Validator<Client> clientValidator;
    EntityDeletedListener<Client> entityDeletedListener = null;

    public ClientControllerImpl(
            Repository<Integer, Client> clientRepository,
            Validator<Client> clientValidator
    )
    {
        this.clientRepository = clientRepository;
        this.clientValidator = clientValidator;
    }

    public void setEntityDeletedListener(EntityDeletedListener<Client> entityDeletedListener)
    {
        this.entityDeletedListener = entityDeletedListener;
    }

    /**
     * This function adds a client to the repository
     *
     * @param id:   the ID of the client
     * @param name: the name of the client
     * @throws AlreadyExistingElementException if the client (the ID) is already there
     */
    @Override
    public void addClient(int id, String name)
    {
        Client client = new Client(id, name);
        clientValidator.validate(client);
        clientRepository
                .save(client)
                .ifPresent(opt ->
                           {
                               throw new AlreadyExistingElementException(String.format(
                                       "Client %d already exists",
                                       id
                               ));
                           });
    }

    /**
     * This function removes a client from the repository based on their ID
     *
     * @param id: the ID of the client
     * @throws ElementNotFoundException if the client isn't found in the repository based on their ID
     */
    @Override
    public void deleteClient(int id)
    {
        clientRepository
                .delete(id)
                .ifPresentOrElse(
                        entity -> Optional
                                .ofNullable(entityDeletedListener)
                                .ifPresent(listener -> listener.onEntityDeleted(entity)),
                        () ->
                        {
                            throw new ElementNotFoundException(String.format(
                                    "Client %d does not exist",
                                    id
                            ));
                        }
                );
    }

    /**
     * This function returns an iterable collection of the current state of the clients in the repository
     *
     * @return all: an iterable collection of clients
     */
    @Override
    public Iterable<Client> getClients()
    {
        return clientRepository.findAll();
    }

    /**
     * This function updated a client based on their ID with a new name
     *
     * @param id:   the client's ID
     * @param name: the new name of the client
     * @throws ElementNotFoundException if the client isn't found in the repository based on their ID
     */
    @Override
    public void updateClient(int id, String name)
    {
        Client client = new Client(id, name);
        clientValidator.validate(client);
        clientRepository
                .update(client)
                .orElseThrow(() -> new ElementNotFoundException(String.format(
                        "Client %d does not exist",
                        id
                )));
    }

    @Override
    public Iterable<Client> filterClientsByName(String name)
    {
        String regex = ".*" + name + ".*";
        return StreamSupport
                .stream(clientRepository.findAll().spliterator(), false)
                .filter(client -> client.getName().matches(regex))
                .collect(Collectors.toUnmodifiableList());
    }

    @Override
    public Optional<Client> findOne(int clientId)
    {
        return clientRepository.findOne(clientId);
    }
}
