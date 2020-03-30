package ro.sdi.lab24.view.commands.rental;

import ro.sdi.lab24.view.Console;
import ro.sdi.lab24.view.FutureResponse;
import ro.sdi.lab24.view.ResponseMapper;

import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static picocli.CommandLine.Command;
import static picocli.CommandLine.Parameters;

@Command(description = "Filter rentals by movie name", name = "filter")
public class FilterRentalsCommand implements Runnable
{
    @Parameters(index = "0", description = "Movie name")
    String name;

    @Override
    public void run() {
        Console.responseBuffer.add(
                new FutureResponse<>(
                        Console.rentalController.filterRentalsByMovieName(name),
                        new ResponseMapper<>(response -> {
                            if (!response.iterator().hasNext()) {
                                return "No rentals found!";
                            }
                            return StreamSupport.stream(response.spliterator(), false)
                                    .map(movie -> String.format("%d %d %s", movie.getId().getMovieId(), movie.getId().getClientId(), movie.getTime().format(Console.dateformatter)))
                                    .collect(Collectors.joining("\n", "", "\n"));
                        })
                )
        );
    }
}
