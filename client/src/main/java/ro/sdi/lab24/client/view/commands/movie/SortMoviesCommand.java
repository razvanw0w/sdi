package ro.sdi.lab24.client.view.commands.movie;

import picocli.CommandLine;
import ro.sdi.lab24.client.view.Console;
import ro.sdi.lab24.client.view.commands.movie.utils.SortingCriteria;
import ro.sdi.lab24.exception.ProgramException;
import ro.sdi.lab24.exception.SortingException;
import ro.sdi.lab24.model.Movie;

@CommandLine.Command(description = "sort movies", name = "sort")
public class SortMoviesCommand implements Runnable {
    @CommandLine.Parameters(arity = "2..6", description = "one to three sorting criteria")
    String[] criteriaStrings;

    private SortingCriteria[] convertStringsToCriteria() {
        if (criteriaStrings.length % 2 == 1)
            throw new SortingException("invalid sorting options");
        SortingCriteria[] criteria = new SortingCriteria[criteriaStrings.length / 2];
        for (int i = 0; i < criteriaStrings.length; i += 2) {
            criteria[i / 2] = new SortingCriteria(criteriaStrings[i], criteriaStrings[i + 1]);
        }
        return criteria;
    }

    @Override
    public void run() {
        try {
            SortingCriteria[] criteria = convertStringsToCriteria();
            Iterable<Movie> movies = Console.movieController.sortMovies(criteria);
            if (!movies.iterator().hasNext()) {
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
        } catch (ProgramException e) {
            Console.handleException(e);
        }
    }
}
