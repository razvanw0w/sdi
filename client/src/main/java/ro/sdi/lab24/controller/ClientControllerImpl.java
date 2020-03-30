package ro.sdi.lab24.controller;

import ro.sdi.lab24.model.Client;
import ro.sdi.lab24.networking.Message;
import ro.sdi.lab24.networking.NetworkingUtils;
import ro.sdi.lab24.networking.TCPClient;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import java.util.stream.Collectors;

public class ClientControllerImpl implements ClientController {
    private ExecutorService executorService;

    public ClientControllerImpl(ExecutorService executorService) {
        this.executorService = executorService;
    }

    @Override
    public Future<Void> addClient(int id, String name) {
        Callable<Void> callable = () ->
        {
            Message message = new Message("ClientController:addClient");

            message.addString(NetworkingUtils.serialize(id));
            message.addString(NetworkingUtils.serialize(name));

            Message response = TCPClient.sendAndReceive(message);
            if (NetworkingUtils.isSuccess(response)) {
                return null;
            }
            NetworkingUtils.checkException(response);
            throw new RuntimeException("Received response was invalid");
        };
        return executorService.submit(callable);
    }

    @Override
    public Future<Void> deleteClient(int id) {
        Callable<Void> callable = () -> {
            Message message = new Message("ClientController:deleteClient");
            message.addString(NetworkingUtils.serialize(id));
            Message response = TCPClient.sendAndReceive(message);
            if (NetworkingUtils.isSuccess(response)) {
                return null;
            }
            NetworkingUtils.checkException(response);
            throw new RuntimeException("Received response was invalid");
        };
        return executorService.submit(callable);
    }

    @Override
    public Future<Iterable<Client>> getClients() {
        Callable<Iterable<Client>> callable = () -> {
            Message message = new Message("ClientController:getClients");
            Message response = TCPClient.sendAndReceive(message);
            if (NetworkingUtils.isSuccess(response)) {
                return response.getBody().stream()
                        .map(string -> NetworkingUtils.deserializeByType(string, Client.class))
                        .collect(Collectors.toUnmodifiableList());
            }
            NetworkingUtils.checkException(response);
            throw new RuntimeException("Received response was invalid");
        };
        return executorService.submit(callable);
    }

    @Override
    public Future<Void> updateClient(int id, String name) {
        Callable<Void> callable = () -> {
            Message message = new Message("ClientController:updateClient");
            message.addString(NetworkingUtils.serialize(id));
            message.addString(NetworkingUtils.serialize(name));
            Message response = TCPClient.sendAndReceive(message);
            if (NetworkingUtils.isSuccess(response)) {
                return null;
            }
            NetworkingUtils.checkException(response);
            throw new RuntimeException("Received response was invalid");
        };
        return executorService.submit(callable);
    }

    @Override
    public Future<Iterable<Client>> filterClientsByName(String name) {
        Callable<Iterable<Client>> callable = () -> {
            Message message = new Message("ClientController:filterClientsByName");
            message.addString(NetworkingUtils.serialize(name));
            Message response = TCPClient.sendAndReceive(message);
            if (NetworkingUtils.isSuccess(response)) {
                return response.getBody().stream()
                        .map(string -> NetworkingUtils.deserializeByType(string, Client.class))
                        .collect(Collectors.toUnmodifiableList());
            }
            NetworkingUtils.checkException(response);
            throw new RuntimeException("Received response was invalid");
        };
        return executorService.submit(callable);
    }
}
