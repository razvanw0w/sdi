package ro.sdi.lab24.view.commands.rental;

import picocli.CommandLine.Command;
import ro.sdi.lab24.view.Console;
import ro.sdi.lab24.view.FutureResponse;
import ro.sdi.lab24.view.ResponseMapper;

import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Command(description = "List rentals", name = "list")
public class ListRentalsCommand implements Runnable
{
    @Override
    public void run() {
        Console.responseBuffer.add(
                new FutureResponse<>(
                        Console.rentalController.getRentals(),
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
