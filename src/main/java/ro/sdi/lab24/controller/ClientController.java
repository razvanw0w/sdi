package ro.sdi.lab24.controller;

import ro.sdi.lab24.model.Client;
import ro.sdi.lab24.repository.Repository;

public class ClientController
{
    Repository<Integer, Client> clientRepository;

    public ClientController(Repository<Integer, Client> clientRepository)
    {
        this.clientRepository = clientRepository;
    }

    public void addClient(int id, String name)
    {

    }

    public void deleteClient(int id)
    {

    }

    public Iterable<Client> getClients()
    {
        return null;
    }

    public void updateClient(int id, String name)
    {
    }

    //TODO Razvan
}
