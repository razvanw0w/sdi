package ro.sdi.lab24.view.commands.movie;

import picocli.CommandLine.Command;
import picocli.CommandLine.Parameters;
import ro.sdi.lab24.exception.ProgramException;
import ro.sdi.lab24.view.Console;

@Command(description = "Delete a movie", name = "delete")
public class DeleteMovieCommand implements Runnable
{
    @Parameters(index = "0", description = "Movie id")
    int id;

    @Override
    public void run()
    {
        try
        {
            Console.movieController.deleteMovie(id);
            System.out.println("Movie deleted!");
        }
        catch (ProgramException e)
        {
            Console.handleException(e);
        }
    }
}
