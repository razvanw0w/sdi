package ro.sdi.lab24.web.converter;

import org.springframework.stereotype.Component;
import ro.sdi.lab24.core.model.Movie;
import ro.sdi.lab24.web.dto.MovieDTO;

@Component
public class MovieConverter implements Converter<Movie, MovieDTO> {
    @Override
    public Movie toModel(MovieDTO movieDTO) {
        return new Movie(movieDTO.getId(), movieDTO.getName(), movieDTO.getGenre(), movieDTO.getRating());
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
