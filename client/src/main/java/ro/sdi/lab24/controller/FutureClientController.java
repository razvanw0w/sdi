package ro.sdi.lab24.controller;

import ro.sdi.lab24.model.Client;

import java.util.concurrent.Future;

public interface FutureClientController {
    Future<Void> addClient(int id, String name);

    Future<Void> deleteClient(int id);

    Future<Iterable<Client>> getClients();

    Future<Void> updateClient(int id, String name);

    Future<Iterable<Client>> filterClientsByName(String name);
}
