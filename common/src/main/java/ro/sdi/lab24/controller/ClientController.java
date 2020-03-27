package ro.sdi.lab24.controller;

import java.util.Optional;

import ro.sdi.lab24.model.Client;

public interface ClientController
{
    void addClient(int id, String name);

    void deleteClient(int id);

    Iterable<Client> getClients();

    void updateClient(int id, String name);

    Iterable<Client> filterClientsByName(String name);

    Optional<Client> findOne(int clientId);
}
