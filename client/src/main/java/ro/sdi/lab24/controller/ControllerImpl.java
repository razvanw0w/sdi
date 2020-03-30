package ro.sdi.lab24.controller;

import ro.sdi.lab24.model.dto.ClientGenre;
import ro.sdi.lab24.model.dto.RentedMovieStatistic;
import ro.sdi.lab24.networking.Message;
import ro.sdi.lab24.networking.NetworkingUtils;
import ro.sdi.lab24.networking.TCPClient;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import java.util.stream.Collectors;

public class ControllerImpl implements Controller {
    private ExecutorService executorService;

    public ControllerImpl(ExecutorService executorService) {
        this.executorService = executorService;
    }

    @Override
    public Future<Iterable<RentedMovieStatistic>> getTop10RentedMovies() {
        Callable<Iterable<RentedMovieStatistic>> callable = () -> {
            Message message = new Message("Controller:getTop10RentedMovies");
            Message response = TCPClient.sendAndReceive(message);
            if (NetworkingUtils.isSuccess(response)) {
                return response.getBody().stream()
                        .map(string -> NetworkingUtils.deserializeByType(string, RentedMovieStatistic.class))
                        .collect(Collectors.toUnmodifiableList());
            }
            NetworkingUtils.checkException(response);
            throw new RuntimeException("Received response was invalid");
        };
        return executorService.submit(callable);
    }

    @Override
    public Future<Iterable<ClientGenre>> getClientGenres() {
        Callable<Iterable<ClientGenre>> callable = () -> {
            Message message = new Message("Controller:getClientGenres");
            Message response = TCPClient.sendAndReceive(message);
            if (NetworkingUtils.isSuccess(response)) {
                return response.getBody().stream()
                        .map(string -> NetworkingUtils.deserializeByType(string, ClientGenre.class))
                        .collect(Collectors.toUnmodifiableList());
            }
            NetworkingUtils.checkException(response);
            throw new RuntimeException("Received response was invalid");
        };
        return executorService.submit(callable);
    }
}
