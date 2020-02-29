package ro.sdi.lab24.view.commands.movie;

import picocli.CommandLine.Command;
import ro.sdi.lab24.exception.ProgramException;
import ro.sdi.lab24.model.Movie;
import ro.sdi.lab24.view.Console;

@Command(description = "List all movies", name = "list")
public class ListMoviesCommand implements Runnable
{
    @Override
    public void run()
    {
        try
        {

            Iterable<Movie> movies = Console.movieController.getMovies();
            if (!movies.iterator().hasNext())
            {
                System.out.println("No clients!");
            }
            movies.forEach(
                    movie -> System.out.printf("%d %s", movie.getId(), movie.getName())
            );
        }
        catch (ProgramException e)
        {
            Console.handleException(e);
        }
    }
}
