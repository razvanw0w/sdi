package ro.sdi.lab.client.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ro.sdi.lab.common.controller.ClientController;
import ro.sdi.lab.common.model.Client;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;

@Service
public class ClientControllerImpl implements FutureClientController {
    public static final Logger log = LoggerFactory.getLogger(ClientControllerImpl.class);

    @Autowired
    private ExecutorService executorService;

    @Autowired
    private ClientController clientController;

    @Override
    public Future<Void> addClient(int id, String name) {
        Callable<Void> callable = () ->
        {
            clientController.addClient(id, name);
            return null;
        };
        return executorService.submit(callable);
    }

    @Override
    public Future<Void> deleteClient(int id) {
        Callable<Void> callable = () ->
        {
            clientController.deleteClient(id);
            return null;
        };
        return executorService.submit(callable);
    }

    @Override
    public Future<Iterable<Client>> getClients() {
        Callable<Iterable<Client>> callable = () ->
        {
            return clientController.getClients();
        };
        return executorService.submit(callable);
    }

    @Override
    public Future<Void> updateClient(int id, String name) {
        Callable<Void> callable = () ->
        {
            clientController.updateClient(id, name);
            return null;
        };
        return executorService.submit(callable);
    }

    @Override
    public Future<Iterable<Client>> filterClientsByName(String name) {
        Callable<Iterable<Client>> callable = () ->
        {
            return clientController.filterClientsByName(name);
        };
        return executorService.submit(callable);
    }
}
