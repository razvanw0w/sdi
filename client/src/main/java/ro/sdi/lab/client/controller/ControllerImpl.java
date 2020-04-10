package ro.sdi.lab.client.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ro.sdi.lab.common.controller.Controller;
import ro.sdi.lab.common.model.dto.ClientGenre;
import ro.sdi.lab.common.model.dto.RentedMovieStatistic;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;

@Service
public class ControllerImpl implements FutureController {
    public static final Logger log = LoggerFactory.getLogger(ControllerImpl.class);

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
