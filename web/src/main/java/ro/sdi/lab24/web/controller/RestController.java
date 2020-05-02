package ro.sdi.lab24.web.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import ro.sdi.lab24.core.service.ReportService;
import ro.sdi.lab24.web.converter.ClientGenreConverter;
import ro.sdi.lab24.web.converter.RentedMovieStatisticConverter;
import ro.sdi.lab24.web.dto.ClientGenresDTO;
import ro.sdi.lab24.web.dto.RentedMovieStatisticsDTO;

@org.springframework.web.bind.annotation.RestController
public class RestController {
    private static final Logger log = LoggerFactory.getLogger(RestController.class);

    @Autowired
    private ReportService reportService;

    @Autowired
    private RentedMovieStatisticConverter rentedMovieStatisticConverter;

    @Autowired
    private ClientGenreConverter clientGenreConverter;

    @RequestMapping(value = "/reports/movies", method = RequestMethod.GET)
    public RentedMovieStatisticsDTO getTop10RentedMovies() {
        log.trace("fetched top 10 rented movies");
        return new RentedMovieStatisticsDTO(
                rentedMovieStatisticConverter.toDTOList(
                        reportService.getTop10RentedMovies()
                )
        );
    }

    @RequestMapping(value = "/reports/genres", method = RequestMethod.GET)
    public ClientGenresDTO getClientGenres() {
        log.trace("fetched favourite rented genre for each client");
        return new ClientGenresDTO(
                clientGenreConverter.toDTOList(
                        reportService.getClientGenres()
                )
        );
    }
}
