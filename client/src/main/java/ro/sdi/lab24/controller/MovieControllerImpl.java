package ro.sdi.lab24.controller;

import ro.sdi.lab24.model.Movie;
import ro.sdi.lab24.model.Sort;
import ro.sdi.lab24.model.serialization.csv.MovieCSVSerializer;
import ro.sdi.lab24.model.serialization.csv.SortCSVSerializer;
import ro.sdi.lab24.networking.IntegerSerializer;
import ro.sdi.lab24.networking.Message;
import ro.sdi.lab24.networking.StringSerializer;
import ro.sdi.lab24.networking.TCPClient;
import ro.sdi.lab24.serialization.NetworkSerializer;

import java.util.concurrent.ExecutorService;

public class MovieControllerImpl implements MovieController {
    private ExecutorService executorService;
    private NetworkSerializer<Movie> movieSerializer;
    private NetworkSerializer<Sort> sortSerializer;
    private IntegerSerializer integerSerializer;
    private StringSerializer stringSerializer;

    public MovieControllerImpl(ExecutorService executorService) {
        this.executorService = executorService;
        this.movieSerializer = NetworkSerializer.from(new MovieCSVSerializer());
        this.sortSerializer = NetworkSerializer.from(new SortCSVSerializer());
        this.integerSerializer = new IntegerSerializer();
        this.stringSerializer = new StringSerializer();
    }

    @Override
    public void addMovie(int id, String name, String genre, int rating) {
        Runnable runnable = () -> {
            Message message = new Message("MovieController:addMovie");
            message.addString(integerSerializer.encode(id));
            message.addString(stringSerializer.encode(name));
            message.addString(stringSerializer.encode(genre));
            message.addString(integerSerializer.encode(rating));
            Message response = TCPClient.sendAndReceive(message);
        };
        executorService.submit(runnable);
    }

    @Override
    public void deleteMovie(int id) {
        Runnable runnable = () -> {
            Message message = new Message("MovieController:deleteMovie");
            message.addString(integerSerializer.encode(id));
            Message response = TCPClient.sendAndReceive(message);
        };
        executorService.submit(runnable);
    }

    @Override
    public Iterable<Movie> getMovies() {
        Runnable runnable = () -> {
            Message message = new Message("MovieController:getMovies");
            Message response = TCPClient.sendAndReceive(message);
        };
        executorService.submit(runnable);
        return null;
    }

    @Override
    public void updateMovie(int id, String name, String genre, Integer rating) {
        Runnable runnable = () -> {
            Message message = new Message("MovieController:updateMovie");
            message.addString(integerSerializer.encode(id));
            message.addString(stringSerializer.encode(name));
            message.addString(stringSerializer.encode(genre));
            message.addString(integerSerializer.encode(rating));
            Message response = TCPClient.sendAndReceive(message);
        };
        executorService.submit(runnable);
    }

    @Override
    public Iterable<Movie> filterMoviesByGenre(String genre) {
        Runnable runnable = () -> {
            Message message = new Message("MovieController:filterMoviesByGenre");
            message.addString(stringSerializer.encode(genre));
            Message response = TCPClient.sendAndReceive(message);
        };
        executorService.submit(runnable);
        return null;
    }

    @Override
    public Iterable<Movie> sortMovies(Sort criteria) {
        Runnable runnable = () -> {
            Message message = new Message("MovieController:sortMovies");
            message.addString(sortSerializer.encode(criteria));
            Message response = TCPClient.sendAndReceive(message);
        };
        executorService.submit(runnable);
        return null;
    }
}
