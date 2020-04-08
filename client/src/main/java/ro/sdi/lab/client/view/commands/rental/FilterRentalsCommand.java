package ro.sdi.lab.client.view.commands.rental;

import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import ro.sdi.lab.client.view.Console;
import ro.sdi.lab.client.view.FutureResponse;
import ro.sdi.lab.client.view.ResponseMapper;

import static picocli.CommandLine.Command;
import static picocli.CommandLine.Parameters;

@Command(description = "Filter rentals by movie name", name = "filter")
public class FilterRentalsCommand implements Runnable
{
    @Parameters(index = "0", description = "Movie name")
    String name;

    @Override
    public void run() {
        String name = this.name;
        Console.responseBuffer.add(
                new FutureResponse<>(
                        Console.rentalController.filterRentalsByMovieName(name),
                        new ResponseMapper<>(response -> {
                            if (!response.iterator().hasNext()) {
                                return "No rentals found!";
                            }
                            return String.format("Rentals filtered  by movie name = %s\n", name) +
                                    StreamSupport.stream(response.spliterator(), false)
                                                 .map(movie -> String.format("%d %d %s",
                                                                             movie.getId()
                                                                                  .getMovieId(),
                                                                             movie.getId()
                                                                                  .getClientId(),
                                                                             movie.getTime()
                                                                                  .format(Console.dateFormatter)
                                                 ))
                                                 .collect(Collectors.joining("\n", "", "\n"));
                        })
                )
        );
    }
}
