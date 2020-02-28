package ro.sdi.lab24.controller;

import ro.sdi.lab24.exception.AlreadyExistingElementException;
import ro.sdi.lab24.exception.ElementNotFoundException;
import ro.sdi.lab24.model.Client;
import ro.sdi.lab24.repository.Repository;

public class ClientController
{
    Repository<Integer, Client> clientRepository;

    public ClientController(Repository<Integer, Client> clientRepository)
    {
        this.clientRepository = clientRepository;
    }

    /**
     * This function adds a client to the repository
     * @param id: the ID of the client
     * @param name: the name of the client
     * @throws AlreadyExistingElementException if the client (the ID) is already there
     */
    public void addClient(int id, String name)
    {
        Client client = new Client(id, name);
        clientRepository.save(client).ifPresent(opt -> {throw new AlreadyExistingElementException("client" + Integer.toString(id) + " already exists");});
    }

    /**
     * This function removes a client from the repository based on their ID
     * @param id: the ID of the client
     * @throws ElementNotFoundException if the client isn't found in the repository based on their ID
     */
    public void deleteClient(int id)
    {
        clientRepository.delete(id).orElseThrow(() -> {return new ElementNotFoundException("client " + Integer.toString(id) + " does not exist");});
    }

    /**
     * This function returns an iterable collection of the current state of the clients in the repository
     * @return all: an iterable collection of clients
     */
    public Iterable<Client> getClients()
    {
        return clientRepository.findAll();
    }

    /**
     * This function updated a client based on their ID with a new name
     * @param id: the client's ID
     * @param name: the new name of the client
     * @throws ElementNotFoundException if the client isn't found in the repository based on their ID
     */
    public void updateClient(int id, String name)
    {
        Client client = new Client(id, name);
        clientRepository.update(client).orElseThrow(() -> {return new ElementNotFoundException("client " + Integer.toString(id) + " does not exist");});
    }
}
