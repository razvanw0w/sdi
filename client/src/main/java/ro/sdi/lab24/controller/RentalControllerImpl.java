package ro.sdi.lab24.controller;

import ro.sdi.lab24.model.Rental;
import ro.sdi.lab24.networking.Message;
import ro.sdi.lab24.networking.NetworkingUtils;
import ro.sdi.lab24.networking.TCPClient;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import java.util.stream.Collectors;

public class RentalControllerImpl implements RentalController {
    private ExecutorService executorService;

    public RentalControllerImpl(ExecutorService executorService) {
        this.executorService = executorService;
    }

    @Override
    public Future<Void> addRental(int movieId, int clientId, String time) {
        Callable<Void> callable = () -> {
            Message message = new Message("RentalController:addRental");
            message.addString(NetworkingUtils.serialize(movieId));
            message.addString(NetworkingUtils.serialize(clientId));
            message.addString(NetworkingUtils.serialize(time));
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
    public Future<Void> deleteRental(int movieId, int clientId) {
        Callable<Void> callable = () -> {
            Message message = new Message("RentalController:deleteRental");
            message.addString(NetworkingUtils.serialize(movieId));
            message.addString(NetworkingUtils.serialize(clientId));
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
    public Future<Iterable<Rental>> getRentals() {
        Callable<Iterable<Rental>> callable = () -> {
            Message message = new Message("RentalController:getRentals");
            Message response = TCPClient.sendAndReceive(message);
            if (NetworkingUtils.isSuccess(response)) {
                return response.getBody().stream()
                        .map(string -> NetworkingUtils.deserializeByType(string, Rental.class))
                        .collect(Collectors.toUnmodifiableList());
            }
            NetworkingUtils.checkException(response);
            throw new RuntimeException("Received response was invalid");
        };
        return executorService.submit(callable);
    }

    @Override
    public Future<Void> updateRental(int movieId, int clientId, String time) {
        Callable<Void> callable = () -> {
            Message message = new Message("RentalController:updateRental");
            message.addString(NetworkingUtils.serialize(movieId));
            message.addString(NetworkingUtils.serialize(clientId));
            message.addString(NetworkingUtils.serialize(time));
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
    public Future<Iterable<Rental>> filterRentalsByMovieName(String name) {
        Callable<Iterable<Rental>> callable = () -> {
            Message message = new Message("RentalController:filterRentalsByMovieName");
            message.addString(NetworkingUtils.serialize(name));
            Message response = TCPClient.sendAndReceive(message);
            if (NetworkingUtils.isSuccess(response)) {
                return response.getBody().stream()
                        .map(string -> NetworkingUtils.deserializeByType(string, Rental.class))
                        .collect(Collectors.toUnmodifiableList());
            }
            NetworkingUtils.checkException(response);
            throw new RuntimeException("Received response was invalid");
        };
        return executorService.submit(callable);
    }
}
