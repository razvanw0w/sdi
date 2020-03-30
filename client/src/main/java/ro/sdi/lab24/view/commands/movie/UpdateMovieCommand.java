package ro.sdi.lab24.view.commands.movie;

import picocli.CommandLine.Command;
import ro.sdi.lab24.view.Console;
import ro.sdi.lab24.view.FutureResponse;
import ro.sdi.lab24.view.ResponseMapper;

import static picocli.CommandLine.Option;
import static picocli.CommandLine.Parameters;

@Command(description = "Update movie", name = "update")
public class UpdateMovieCommand implements Runnable
{
    @Parameters(index = "0", description = "Movie id")
    int id;

    @Option(names = {"-n", "--name"}, description = "Movie name")
    String name = null;

    @Option(names = {"-g", "--genre"}, description = "Movie genre")
    String genre = null;

    @Option(names = {"-r", "--rating"}, description = "Movie rating")
    Integer rating = null;

    @Override
    public void run() {
        Console.responseBuffer.add(
                new FutureResponse<>(
                        Console.movieController.updateMovie(id, name, genre, rating),
                        new ResponseMapper<>(response -> "Movie updated!")
                )
        );
    }
}
