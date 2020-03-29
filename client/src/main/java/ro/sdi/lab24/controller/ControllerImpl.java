package ro.sdi.lab24.controller;

import ro.sdi.lab24.model.dto.ClientGenre;
import ro.sdi.lab24.model.dto.RentedMovieStatistic;
import ro.sdi.lab24.networking.Message;
import ro.sdi.lab24.networking.TCPClient;

import java.util.concurrent.ExecutorService;

public class ControllerImpl implements Controller {
    private ExecutorService executorService;

    public ControllerImpl(ExecutorService executorService) {
        this.executorService = executorService;
    }

    @Override
    public Iterable<RentedMovieStatistic> getTop10RentedMovies() {
        Runnable runnable = () -> {
            Message message = new Message("Controller:getTop10RentedMovies");
            Message response = TCPClient.sendAndReceive(message);
        };
        executorService.submit(runnable);
        return null;
    }

    @Override
    public Iterable<ClientGenre> getClientGenres() {
        Runnable runnable = () -> {
            Message message = new Message("Controller:getClientGenres");
            Message response = TCPClient.sendAndReceive(message);
        };
        executorService.submit(runnable);
        return null;
    }
}
