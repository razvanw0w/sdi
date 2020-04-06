package ro.sdi.lab24.controller;

import org.springframework.beans.factory.annotation.Autowired;
import ro.sdi.lab24.model.dto.ClientGenre;
import ro.sdi.lab24.model.dto.RentedMovieStatistic;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;

public class ControllerImpl implements FutureController {
    @Autowired
    private ExecutorService executorService;

    @Autowired
    private Controller controller;

    @Override
    public Future<Iterable<RentedMovieStatistic>> getTop10RentedMovies() {
        Callable<Iterable<RentedMovieStatistic>> callable = () -> {
            return controller.getTop10RentedMovies();
        };
        return executorService.submit(callable);
    }

    @Override
    public Future<Iterable<ClientGenre>> getClientGenres() {
        Callable<Iterable<ClientGenre>> callable = () -> {
            return controller.getClientGenres();
        };
        return executorService.submit(callable);
    }
}
