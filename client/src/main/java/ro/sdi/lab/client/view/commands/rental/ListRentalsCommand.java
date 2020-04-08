package ro.sdi.lab.client.view.commands.rental;

import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import picocli.CommandLine.Command;
import ro.sdi.lab.client.view.Console;
import ro.sdi.lab.client.view.FutureResponse;
import ro.sdi.lab.client.view.ResponseMapper;

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
                            return "List of rentals\n" +
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
