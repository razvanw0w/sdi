package ro.sdi.lab24.controller;

import ro.sdi.lab24.exception.AlreadyExistingElementException;
import ro.sdi.lab24.exception.ElementNotFoundException;
import ro.sdi.lab24.model.Client;
import ro.sdi.lab24.repository.Repository;
import ro.sdi.lab24.validation.Validator;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public class ClientController
{
    Repository<Integer, Client> clientRepository;
    Validator<Client> clientValidator;

    public ClientController(Repository<Integer, Client> clientRepository, Validator<Client> clientValidator) {
        this.clientRepository = clientRepository;
        this.clientValidator = clientValidator;
    }

    /**
     * This function adds a client to the repository
     *
     * @param id:   the ID of the client
     * @param name: the name of the client
     * @throws AlreadyExistingElementException if the client (the ID) is already there
     */
    public void addClient(int id, String name)
    {
        Client client = new Client(id, name);
        clientValidator.validate(client);
        clientRepository.save(client).ifPresent(opt ->
        {
            throw new AlreadyExistingElementException(String.format("Client %d already exists", id));
        });
    }

    /**
     * This function removes a client from the repository based on their ID
     *
     * @param id: the ID of the client
     * @throws ElementNotFoundException if the client isn't found in the repository based on their ID
     */
    public void deleteClient(int id)
    {
        clientRepository.delete(id).orElseThrow(() -> new ElementNotFoundException(String.format("Client %d does not exist", id)));
    }

    /**
     * This function returns an iterable collection of the current state of the clients in the repository
     *
     * @return all: an iterable collection of clients
     */
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
    public void updateClient(int id, String name) {
        Client client = new Client(id, name);
        clientValidator.validate(client);
        clientRepository.update(client).orElseThrow(() -> new ElementNotFoundException(String.format("Client %d does not exist", id)));
    }

    public Iterable<Client> filterClientsByName(String name) {
        String regex = ".*" + name + ".*";
        List<Client> list = StreamSupport.stream(clientRepository.findAll().spliterator(), false)
                .filter(client -> client.getName().matches(regex))
                .collect(Collectors.toList());

        return Collections.unmodifiableCollection(list);
    }
}
