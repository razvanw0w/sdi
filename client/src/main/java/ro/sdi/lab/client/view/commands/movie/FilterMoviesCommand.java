package ro.sdi.lab.client.view.commands.movie;

import ro.sdi.lab.client.view.Console;
import ro.sdi.lab.client.view.FutureResponse;
import ro.sdi.lab.client.view.ResponseMapper;

import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static picocli.CommandLine.Command;
import static picocli.CommandLine.Parameters;

@Command(description = "Filter movie by genre", name = "filter")
public class FilterMoviesCommand implements Runnable
{
    @Parameters(index = "0", description = "Movie genre")
    String genre;

    @Override
    public void run() {
        String genre = this.genre;
        Console.responseBuffer.add(
                new FutureResponse<>(
                        Console.movieController.filterMoviesByGenre(genre),
                        new ResponseMapper<>(response -> {
                            if (!response.iterator().hasNext()) {
                                return "No movies found!";
                            }
                            return String.format("Movies filtered by genre = %s\n", genre) +
                                    StreamSupport.stream(response.spliterator(), false)
                                            .map(movie -> String.format("%d %s %s %d", movie.getId(), movie.getName(), movie.getGenre(), movie.getRating()))
                                            .collect(Collectors.joining("\n", "", "\n"));
                        })
                )
        );
    }
}
