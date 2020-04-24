package ro.sdi.lab24.client.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import ro.sdi.lab24.core.controller.dto.ClientGenre;
import ro.sdi.lab24.core.controller.dto.RentedMovieStatistic;
import ro.sdi.lab24.web.converter.ClientGenreConverter;
import ro.sdi.lab24.web.converter.RentedMovieStatisticConverter;
import ro.sdi.lab24.web.dto.ClientGenresDTO;
import ro.sdi.lab24.web.dto.RentedMovieStatisticsDTO;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class RestTemplateController {
    public static final String URL = "http://localhost:8080/api/reports";
    public static final Logger log = LoggerFactory.getLogger(RestTemplateController.class);
    @Autowired
    private RestTemplate restTemplate;
    @Autowired
    private RentedMovieStatisticConverter rentedMovieStatisticConverter;
    @Autowired
    private ClientGenreConverter clientGenreConverter;

    public Iterable<RentedMovieStatistic> getTop10RentedMovies() {
        RentedMovieStatisticsDTO dto = restTemplate.getForObject(
                URL + "/movies",
                RentedMovieStatisticsDTO.class
        );
        assert dto != null;
        List<RentedMovieStatistic> rentedMovieStatistics = dto.getRentedMovieStatistics()
                .stream()
                .map(rentedMovieStatisticConverter::toModel)
                .collect(Collectors.toList());
        log.trace("rented movie statistic: {}", rentedMovieStatistics);
        return rentedMovieStatistics;
    }

    /**
     * Retrieves the most rented genre for each client, or the empty string if the client did not rent any movies
     */
    public Iterable<ClientGenre> getClientGenres() {
        ClientGenresDTO dto = restTemplate.getForObject(
                URL + "/genres",
                ClientGenresDTO.class
        );
        assert dto != null;
        List<ClientGenre> clientGenres = dto.getClientGenres()
                .stream()
                .map(clientGenreConverter::toModel)
                .collect(Collectors.toList());
        log.trace("client genres: {}", clientGenres);
        return clientGenres;
    }
}
