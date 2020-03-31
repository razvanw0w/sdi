package ro.sdi.lab24.view.commands.rental;

import picocli.CommandLine.Command;
import picocli.CommandLine.Parameters;
import ro.sdi.lab24.view.Console;
import ro.sdi.lab24.view.FutureResponse;
import ro.sdi.lab24.view.ResponseMapper;

@Command(description = "Update rental", name = "update")
public class UpdateRentalCommand implements Runnable
{
    @Parameters(index = "0", description = "Movie id")
    int movieId;

    @Parameters(index = "1", description = "Client id")
    int clientId;

    @Parameters(index = "2", description = "Rental time: \"dd-MM-yyyy HH:mm\"")
    String time;

    @Override
    public void run() {
        int movieId = this.movieId;
        int clientId = this.clientId;
        String time = this.time;
        Console.responseBuffer.add(
                new FutureResponse<>(
                        Console.rentalController.addRental(movieId, clientId, time),
                        new ResponseMapper<>(response -> String.format("Rental %d,%d updated (new time: %s)!", movieId, clientId, time))
                )
        );
    }
}
