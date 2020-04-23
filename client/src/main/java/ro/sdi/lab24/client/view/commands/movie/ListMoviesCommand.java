package ro.sdi.lab24.client.view.commands.movie;

import picocli.CommandLine.Command;
import ro.sdi.lab24.client.view.Console;
import ro.sdi.lab24.exception.ProgramException;
import ro.sdi.lab24.model.Movie;

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
                System.out.println("No movies found!");
            }
            movies.forEach(
                    movie -> System.out.printf(
                            "%d %s %s %d\n",
                            movie.getId(),
                            movie.getName(),
                            movie.getGenre(),
                            movie.getRating()
                    )
            );
        }
        catch (ProgramException e)
        {
            Console.handleException(e);
        }
    }
}
