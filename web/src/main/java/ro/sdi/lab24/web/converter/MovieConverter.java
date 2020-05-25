package ro.sdi.lab24.web.converter;

import org.springframework.stereotype.Component;
import ro.sdi.lab24.core.model.Movie;
import ro.sdi.lab24.web.dto.MovieDTO;

@Component
public class MovieConverter implements Converter<Movie, MovieDTO> {
    @Override
    public Movie toModel(MovieDTO movieDTO) {
        Movie movie = Movie.builder()
                .name(movieDTO.getName())
                .genre(movieDTO.getGenre())
                .rating(movieDTO.getRating()).build();
        movie.setId(movie.getId());
        return movie;
    }

    @Override
    public MovieDTO toDTO(Movie movie) {
        return MovieDTO.builder()
                .id(movie.getId())
                .name(movie.getName())
                .genre(movie.getGenre())
                .rating(movie.getRating())
                .build();
    }
}
