package ro.sdi.lab.common.controller;

import ro.sdi.lab.common.model.Client;

public interface ClientController
{
    void addClient(int id, String name);

    void deleteClient(int id);

    Iterable<Client> getClients();

    void updateClient(int id, String name);

    Iterable<Client> filterClientsByName(String name);
}
