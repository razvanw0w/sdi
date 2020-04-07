package ro.sdi.lab.client.controller;

import ro.sdi.lab.common.model.dto.ClientGenre;
import ro.sdi.lab.common.model.dto.RentedMovieStatistic;

import java.util.concurrent.Future;

public interface FutureController {
    Future<Iterable<RentedMovieStatistic>> getTop10RentedMovies();

    Future<Iterable<ClientGenre>> getClientGenres();
}
