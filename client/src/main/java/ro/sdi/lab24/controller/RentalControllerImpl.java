package ro.sdi.lab24.controller;

import ro.sdi.lab24.model.Rental;
import ro.sdi.lab24.model.serialization.csv.RentalCSVSerializer;
import ro.sdi.lab24.networking.IntegerSerializer;
import ro.sdi.lab24.networking.Message;
import ro.sdi.lab24.networking.StringSerializer;
import ro.sdi.lab24.networking.TCPClient;
import ro.sdi.lab24.serialization.NetworkSerializer;

import java.time.format.DateTimeFormatter;
import java.util.concurrent.ExecutorService;

public class RentalControllerImpl implements RentalController {
    public DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");
    private ExecutorService executorService;
    private NetworkSerializer<Rental> networkSerializer;
    private IntegerSerializer integerSerializer;
    private StringSerializer stringSerializer;

    public RentalControllerImpl(ExecutorService executorService) {
        this.executorService = executorService;
        this.networkSerializer = NetworkSerializer.from(new RentalCSVSerializer());
        this.integerSerializer = new IntegerSerializer();
        this.stringSerializer = new StringSerializer();
    }

    @Override
    public void addRental(int movieId, int clientId, String time) {
        Runnable runnable = () -> {
            Message message = new Message("RentalController:addRental");
            message.addString(integerSerializer.encode(movieId));
            message.addString(integerSerializer.encode(clientId));
            message.addString(stringSerializer.encode(time));
            Message response = TCPClient.sendAndReceive(message);
        };
        executorService.submit(runnable);
    }

    @Override
    public void deleteRental(int movieId, int clientId) {
        Runnable runnable = () -> {
            Message message = new Message("RentalController:deleteRental");
            message.addString(integerSerializer.encode(movieId));
            message.addString(integerSerializer.encode(clientId));
            Message response = TCPClient.sendAndReceive(message);
        };
        executorService.submit(runnable);
    }

    @Override
    public Iterable<Rental> getRentals() {
        Runnable runnable = () -> {
            Message message = new Message("RentalController:getRentals");
            Message response = TCPClient.sendAndReceive(message);
        };
        executorService.submit(runnable);
        return null;
    }

    @Override
    public void updateRental(int movieId, int clientId, String time) {
        Runnable runnable = () -> {
            Message message = new Message("RentalController:updateRental");
            message.addString(integerSerializer.encode(movieId));
            message.addString(integerSerializer.encode(clientId));
            message.addString(stringSerializer.encode(time));
            Message response = TCPClient.sendAndReceive(message);
        };
        executorService.submit(runnable);
    }

    @Override
    public Iterable<Rental> filterRentalsByMovieName(String name) {
        Runnable runnable = () -> {
            Message message = new Message("RentalController:filterRentalsByMovieName");
            message.addString(stringSerializer.encode(name));
            Message response = TCPClient.sendAndReceive(message);
        };
        executorService.submit(runnable);
        return null;
    }
}
