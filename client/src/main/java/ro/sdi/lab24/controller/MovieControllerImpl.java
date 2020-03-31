package ro.sdi.lab24.controller;

import ro.sdi.lab24.model.Movie;
import ro.sdi.lab24.model.Sort;
import ro.sdi.lab24.networking.Message;
import ro.sdi.lab24.networking.NetworkingUtils;
import ro.sdi.lab24.networking.TCPClient;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import java.util.stream.Collectors;

public class MovieControllerImpl implements MovieController {
    private ExecutorService executorService;

    public MovieControllerImpl(ExecutorService executorService) {
        this.executorService = executorService;
    }

    @Override
    public Future<Void> addMovie(int id, String name, String genre, int rating) {
        Callable<Void> callable = () -> {
            Message message = new Message("MovieController:addMovie");
            message.addString(NetworkingUtils.serialize(id));
            message.addString(NetworkingUtils.serialize(name));
            message.addString(NetworkingUtils.serialize(genre));
            message.addString(NetworkingUtils.serialize(rating));
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
    public Future<Void> deleteMovie(int id) {
        Callable<Void> callable = () -> {
            Message message = new Message("MovieController:deleteMovie");
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
    public Future<Iterable<Movie>> getMovies() {
        Callable<Iterable<Movie>> callable = () -> {
            Message message = new Message("MovieController:getMovies");
            Message response = TCPClient.sendAndReceive(message);
            if (NetworkingUtils.isSuccess(response)) {
                return response.getBody().stream()
                        .map(string -> NetworkingUtils.deserializeByType(string, Movie.class))
                        .collect(Collectors.toUnmodifiableList());
            }
            NetworkingUtils.checkException(response);
            throw new RuntimeException("Received response was invalid");
        };
        return executorService.submit(callable);
    }

    @Override
    public Future<Void> updateMovie(int id, String name, String genre, Integer rating) {
        Callable<Void> callable = () -> {
            Message message = new Message("MovieController:updateMovie");
            message.addString(NetworkingUtils.serialize(id));
            message.addString(NetworkingUtils.serialize(name));
            message.addString(NetworkingUtils.serialize(genre));
            message.addString(NetworkingUtils.serialize(rating));
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
    public Future<Iterable<Movie>> filterMoviesByGenre(String genre) {
        Callable<Iterable<Movie>> callable = () -> {
            Message message = new Message("MovieController:filterMoviesByGenre");
            message.addString(NetworkingUtils.serialize(genre));
            Message response = TCPClient.sendAndReceive(message);
            if (NetworkingUtils.isSuccess(response)) {
                return response.getBody().stream()
                        .map(string -> NetworkingUtils.deserializeByType(string, Movie.class))
                        .collect(Collectors.toUnmodifiableList());
            }
            NetworkingUtils.checkException(response);
            throw new RuntimeException("Received response was invalid");
        };
        return executorService.submit(callable);
    }

    @Override
    public Future<Iterable<Movie>> sortMovies(Sort criteria) {
        Callable<Iterable<Movie>> callable = () -> {
            Message message = new Message("MovieController:sortMovies");
            message.addString(NetworkingUtils.serialize(criteria));
            Message response = TCPClient.sendAndReceive(message);
            if (NetworkingUtils.isSuccess(response)) {
                return response.getBody().stream()
                        .map(string -> NetworkingUtils.deserializeByType(string, Movie.class))
                        .collect(Collectors.toUnmodifiableList());
            }
            NetworkingUtils.checkException(response);
            throw new RuntimeException("Received response was invalid");
        };
        return executorService.submit(callable);
    }
}
