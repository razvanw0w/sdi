package ro.sdi.lab24.controller;

import ro.sdi.lab24.model.dto.ClientGenre;
import ro.sdi.lab24.model.dto.RentedMovieStatistic;

public interface Controller
{
    Iterable<RentedMovieStatistic> getTop10RentedMovies();

    Iterable<ClientGenre> getClientGenres();
}
