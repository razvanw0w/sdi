package ro.sdi.lab24.client.view.commands.report;

import picocli.CommandLine.Command;
import ro.sdi.lab24.client.view.Console;
import ro.sdi.lab24.core.exception.ProgramException;
import ro.sdi.lab24.core.service.dto.RentedMovieStatistic;

@Command(description = "Top 10 rented movies", name = "topmovies")
public class ReportTopMoviesCommand implements Runnable {
    @Override
    public void run() {
        try {
            Iterable<RentedMovieStatistic> movies = Console.controller.getTop10RentedMovies();
            if (!movies.iterator().hasNext()) {
                System.out.println("No such movies found!");
            }
            movies.forEach(
                    movieStatistic -> System.out.printf(
                            "%s - rented %d times\n",
                            movieStatistic.getMovieName(),
                            movieStatistic.getNumberOfRentals()
                    )
            );
        } catch (ProgramException e) {
            Console.handleException(e);
        }
    }
}