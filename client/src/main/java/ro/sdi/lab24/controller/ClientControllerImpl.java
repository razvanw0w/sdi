package ro.sdi.lab24.controller;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;

import ro.sdi.lab24.model.Client;
import ro.sdi.lab24.networking.Message;
import ro.sdi.lab24.networking.NetworkingUtils;
import ro.sdi.lab24.networking.TCPClient;

public class ClientControllerImpl implements ClientController {
    private ExecutorService executorService;

    public ClientControllerImpl(ExecutorService executorService) {
        this.executorService = executorService;
    }

    @Override
    public Future<Void> addClient(int id, String name)
    { //TODO this does not compile because you need to create new interfaces
        //TODO and yes, the class Void exists :P use the appropriate class wherever necessary
        Callable<Void> callable = () ->
        {
            Message message = new Message("ClientController:addClient");

            //TODO don't bother passing all the networkSerializers, use my NetworkUtils class (check the diff)
            message.addString(NetworkingUtils.serialize(id));
            message.addString(NetworkingUtils.serialize(name));

            Message response = TCPClient.sendAndReceive(message);
            if (NetworkingUtils.isSuccess(response))
            {
                return null;//TODO return the deserialized response here (use NetworkUtils class)
            }
            NetworkingUtils.checkException(response);
            throw new RuntimeException("Received response was invalid");
        };
        return executorService.submit(callable);
    }

    @Override
    public void deleteClient(int id) {
        Runnable runnable = () -> {
            Message message = new Message("ClientController:deleteClient");
            message.addString(integerSerializer.encode(id));
            Message response = TCPClient.sendAndReceive(message);
        };
        executorService.submit(runnable);
    }

    @Override
    public Iterable<Client> getClients() {
        Runnable runnable = () -> {
            Message message = new Message("ClientController:getClients");
            Message response = TCPClient.sendAndReceive(message);
        };
        executorService.submit(runnable);
        return null;
    }

    @Override
    public void updateClient(int id, String name) {
        Runnable runnable = () -> {
            Message message = new Message("ClientController:updateClient");
            message.addString(integerSerializer.encode(id));
            message.addString(stringSerializer.encode(name));
            Message response = TCPClient.sendAndReceive(message);
        };
        executorService.submit(runnable);
    }

    @Override
    public Iterable<Client> filterClientsByName(String name) {
        Runnable runnable = () -> {
            Message message = new Message("ClientController:filterClientsByName");
            message.addString(stringSerializer.encode(name));
            Message response = TCPClient.sendAndReceive(message);
        };
        executorService.submit(runnable);
        return null;
    }
}
