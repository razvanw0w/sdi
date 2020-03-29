package ro.sdi.lab24.controller;

import ro.sdi.lab24.model.Client;
import ro.sdi.lab24.model.serialization.csv.ClientCSVSerializer;
import ro.sdi.lab24.networking.IntegerSerializer;
import ro.sdi.lab24.networking.Message;
import ro.sdi.lab24.networking.StringSerializer;
import ro.sdi.lab24.networking.TCPClient;
import ro.sdi.lab24.serialization.NetworkSerializer;

import java.util.concurrent.ExecutorService;

public class ClientControllerImpl implements ClientController {
    private ExecutorService executorService;
    private NetworkSerializer<Client> networkSerializer;
    private IntegerSerializer integerSerializer;
    private StringSerializer stringSerializer;

    public ClientControllerImpl(ExecutorService executorService) {
        this.executorService = executorService;
        this.networkSerializer = NetworkSerializer.from(new ClientCSVSerializer());
        this.integerSerializer = new IntegerSerializer();
        this.stringSerializer = new StringSerializer();
    }

    @Override
    public void addClient(int id, String name) {
        Runnable runnable = () -> {
            Message message = new Message("ClientController:addClient");
            message.addString(integerSerializer.encode(id));
            message.addString(stringSerializer.encode(name));
            Message response = TCPClient.sendAndReceive(message);
        };
        executorService.submit(runnable);
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
