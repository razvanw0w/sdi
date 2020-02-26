package ro.sdi.lab24.view.commands.rental;

import picocli.CommandLine.Command;
import picocli.CommandLine.Parameters;
import ro.sdi.lab24.view.Console;

@Command(description = "Update rental", name = "update")
public class UpdateRentalCommand implements Runnable
{
    @Parameters(index = "0", description = "Movie id")
    int movieId;

    @Parameters(index = "1", description = "Client id")
    int clientId;

    @Parameters(index = "2", description = "Rental time")
    String time;

    @Override
    public void run()
    {
        Console.rentalController.updateRental(movieId, clientId, time);
    }
}
