package ro.sdi.lab.common.controller;

import ro.sdi.lab.common.model.dto.ClientGenre;
import ro.sdi.lab.common.model.dto.RentedMovieStatistic;

public interface Controller
{
    Iterable<RentedMovieStatistic> getTop10RentedMovies();

    Iterable<ClientGenre> getClientGenres();
}
