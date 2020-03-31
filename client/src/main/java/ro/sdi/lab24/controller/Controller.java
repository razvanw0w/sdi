package ro.sdi.lab24.controller;

import ro.sdi.lab24.model.dto.ClientGenre;
import ro.sdi.lab24.model.dto.RentedMovieStatistic;

import java.util.concurrent.Future;

public interface Controller {
    Future<Iterable<RentedMovieStatistic>> getTop10RentedMovies();

    Future<Iterable<ClientGenre>> getClientGenres();
}
