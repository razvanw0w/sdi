package ro.sdi.lab24.view.commands.movie;

import picocli.CommandLine.Command;
import picocli.CommandLine.Parameters;
import ro.sdi.lab24.view.Console;
import ro.sdi.lab24.view.FutureResponse;
import ro.sdi.lab24.view.ResponseMapper;

@Command(description = "Adds a movie", name = "add")
public class AddMovieCommand implements Runnable
{
    @Parameters(index = "0", description = "Movie id")
    int id;

    @Parameters(index = "1", description = "Movie name")
    String name;

    @Parameters(index = "2", description = "Movie genre")
    String genre;

    @Parameters(index = "3", description = "Movie rating")
    int rating;

    @Override
    public void run() {
        Console.responseBuffer.add(
                new FutureResponse<>(
                        Console.movieController.addMovie(id, name, genre, rating),
                        new ResponseMapper<>(response -> String.format("Movie %d,%s,%s,%d added!", id, name, genre, rating))
                )
        );
    }
}
