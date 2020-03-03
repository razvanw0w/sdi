package ro.sdi.lab24.view.commands.movie;

import java.util.Optional;

import picocli.CommandLine.Command;
import ro.sdi.lab24.exception.ProgramException;
import ro.sdi.lab24.view.Console;

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
    public void run()
    {
        try
        {
            Console.movieController.updateMovie(
                    id,
                    Optional.ofNullable(name),
                    Optional.ofNullable(genre),
                    Optional.ofNullable(rating)
            );
            System.out.println("Movie updated!");
        }
        catch (ProgramException e)
        {
            Console.handleException(e);
        }
    }
}
