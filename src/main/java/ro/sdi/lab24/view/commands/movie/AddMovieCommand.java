package ro.sdi.lab24.view.commands.movie;

import picocli.CommandLine.Command;
import picocli.CommandLine.Parameters;
import ro.sdi.lab24.validation.ProgramException;
import ro.sdi.lab24.view.Console;

@Command(description = "Adds a movie", name = "add")
public class AddMovieCommand implements Runnable
{
    @Parameters(index = "0", description = "Movie id")
    int id;

    @Parameters(index = "1", description = "Movie name")
    String name;

    @Override
    public void run()
    {
        try
        {
            Console.movieController.addMovie(id, name);
        }
        catch (ProgramException e)
        {
            Console.handleException(e);
        }
    }
}
