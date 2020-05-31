package ro.sdi.lab24.web.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import ro.sdi.lab24.core.model.Movie;
import ro.sdi.lab24.core.service.MovieService;
import ro.sdi.lab24.web.converter.MovieConverter;
import ro.sdi.lab24.web.dto.MovieDTO;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.initMocks;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class MovieRestControllerTest {
    private MockMvc mockMvc;

    @InjectMocks
    private MovieRestController movieRestController;

    @Mock
    private MovieService movieService;

    @Mock
    private MovieConverter movieConverter;

    private Movie movie1;
    private Movie movie2;
    private MovieDTO movieDTO1;
    private MovieDTO movieDTO2;

    @Before
    public void setup() throws Exception {
        initMocks(this);
        this.mockMvc = MockMvcBuilders.standaloneSetup(movieRestController).build();
        initData();
    }

    private void initData() {
        movie1 = Movie.builder().name("m1").genre("g").rating(80).build();
        movie1.setId(1);
        movie2 = Movie.builder().name("m2").genre("g").rating(75).build();
        movie2.setId(2);

        movieDTO1 = createMovieDTO(movie1);
        movieDTO2 = createMovieDTO(movie2);
    }

    private MovieDTO createMovieDTO(Movie movie) {
        return MovieDTO.builder()
                .id(movie.getId())
                .name(movie.getName())
                .genre(movie.getGenre())
                .rating(movie.getRating())
                .build();
    }

    private String toJsonString(MovieDTO movieDTO) {
        try {
            return new ObjectMapper().writeValueAsString(movieDTO);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void getMovies() throws Exception {
        List<Movie> movies = Arrays.asList(movie1, movie2);
        List<MovieDTO> movieDTOS = Arrays.asList(movieDTO1, movieDTO2);

        when(movieService.getMovies()).thenReturn(movies);
        when(movieConverter.toDTOList(movies)).thenReturn(movieDTOS);

        ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders.get("/movies"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$.movies", hasSize(2)))
                .andExpect(jsonPath("$.movies[0].name", is("m1")))
                .andExpect(jsonPath("$.movies[0].genre", is("g")))
                .andExpect(jsonPath("$.movies[0].rating", is(80)))
                .andExpect(jsonPath("$.movies[1].name", is("m2")))
                .andExpect(jsonPath("$.movies[1].genre", is("g")))
                .andExpect(jsonPath("$.movies[1].rating", is(75)));
        String contentAsString = resultActions.andReturn().getResponse().getContentAsString();

        verify(movieService, times(1)).getMovies();
        verify(movieConverter, times(1)).toDTOList(movies);
        verifyNoMoreInteractions(movieService, movieConverter);
    }

    @Test
    public void addMovie() throws Exception {
        Movie newMovie = Movie.builder().name("new").genre("g").rating(100).build();
        newMovie.setId(5);
        MovieDTO newMovieDTO = createMovieDTO(newMovie);

        when(movieConverter.toModel(newMovieDTO)).thenReturn(newMovie);
        mockMvc.perform(MockMvcRequestBuilders
                .post("/movies", newMovieDTO)
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(toJsonString(newMovieDTO)))
                .andExpect(status().isOk());
        verify(movieService, times(1)).addMovie(newMovie.getName(), newMovie.getGenre(), newMovie.getRating());
        verify(movieConverter, times(1)).toModel(newMovieDTO);
        verifyNoMoreInteractions(movieService, movieConverter);
    }

    @Test
    public void deleteMovie() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                .delete("/movies/{id}", movie1.getId())
                .contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());
        verify(movieService, times(1)).deleteMovie(movie1.getId());
    }

    @Test
    public void updateMovie() throws Exception {
        when(movieConverter.toModel(movieDTO1)).thenReturn(movie1);
        mockMvc.perform(MockMvcRequestBuilders
                .put("/movies/{id}", movie1.getId(), movieDTO1)
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(toJsonString(movieDTO1)))
                .andExpect(status().isOk());
        verify(movieService, times(1)).updateMovie(movie1.getId(), movie1.getName(), movie1.getGenre(), movie1.getRating());
        verify(movieConverter, times(1)).toModel(movieDTO1);
        verifyNoMoreInteractions(movieService, movieConverter);
    }
}
