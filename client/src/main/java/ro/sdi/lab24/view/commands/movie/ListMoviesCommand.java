package ro.sdi.lab24.view.commands.movie;

import picocli.CommandLine.Command;
import ro.sdi.lab24.view.Console;
import ro.sdi.lab24.view.FutureResponse;
import ro.sdi.lab24.view.ResponseMapper;

import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Command(description = "List all movies", name = "list")
public class ListMoviesCommand implements Runnable
{
    @Override
    public void run() {
        Console.responseBuffer.add(
                new FutureResponse<>(
                        Console.movieController.getMovies(),
                        new ResponseMapper<>(response -> {
                            if (!response.iterator().hasNext()) {
                                return "No movies found!";
                            }
                            return "List of movies\n" +
                                    StreamSupport.stream(response.spliterator(), false)
                                            .map(movie -> String.format("%d %s %s %d", movie.getId(), movie.getName(), movie.getGenre(), movie.getRating()))
                                            .collect(Collectors.joining("\n", "", "\n"));
                        })
                )
        );
    }
}
