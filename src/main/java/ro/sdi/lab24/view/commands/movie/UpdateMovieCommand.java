package ro.sdi.lab24.view.commands.movie;

import picocli.CommandLine;
import picocli.CommandLine.Command;
import ro.sdi.lab24.validation.ProgramException;
import ro.sdi.lab24.view.Console;

@Command(description = "Update movie", name = "update")
public class UpdateMovieCommand implements Runnable
{
    @CommandLine.Parameters(index = "0", description = "Movie id")
    int id;

    @CommandLine.Parameters(index = "1", description = "Movie name")
    String name;

    @Override
    public void run()
    {
        try
        {
            Console.movieController.updateMovie(id, name);
        }
        catch (ProgramException e)
        {
            Console.handleException(e);
        }
    }
}
