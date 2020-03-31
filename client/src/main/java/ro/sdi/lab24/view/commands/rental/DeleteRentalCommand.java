package ro.sdi.lab24.view.commands.rental;

import picocli.CommandLine;
import picocli.CommandLine.Command;
import ro.sdi.lab24.view.Console;
import ro.sdi.lab24.view.FutureResponse;
import ro.sdi.lab24.view.ResponseMapper;

@Command(description = "Delete rental", name = "delete")
public class DeleteRentalCommand implements Runnable
{
    @CommandLine.Parameters(index = "0", description = "Movie id")
    int movieId;

    @CommandLine.Parameters(index = "1", description = "Client id")
    int clientId;

    @Override
    public void run() {
        Console.responseBuffer.add(
                new FutureResponse<>(
                        Console.rentalController.deleteRental(movieId, clientId),
                        new ResponseMapper<>(response -> String.format("Rental %d,%d deleted!", movieId, clientId))
                )
        );
    }
}
