package ro.sdi.lab24.view.commands.movie;

import picocli.CommandLine.Command;
import ro.sdi.lab24.exception.ProgramException;
import ro.sdi.lab24.view.Console;

import static picocli.CommandLine.Parameters;

@Command(description = "Update movie", name = "update")
public class UpdateMovieCommand implements Runnable
{
    @Parameters(index = "0", description = "Movie id")
    int id;

    @Parameters(index = "1", description = "Movie name")
    String name;

    @Parameters(index = "3", description = "Movie genre")
    String genre;

    @Parameters(index = "4", description = "Movie rating")
    int rating;

    @Override
    public void run()
    {
        try
        {
            Console.movieController.updateMovie(id, name, genre, rating);
            System.out.println("Movie updated!");
        }
        catch (ProgramException e)
        {
            Console.handleException(e);
        }
    }
}
