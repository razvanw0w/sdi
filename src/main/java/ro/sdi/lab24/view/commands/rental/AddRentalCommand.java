package ro.sdi.lab24.view.commands.rental;

import picocli.CommandLine.Command;
import picocli.CommandLine.Parameters;
import ro.sdi.lab24.validation.ProgramException;
import ro.sdi.lab24.view.Console;

@Command(description = "Add a rental", name = "add")
public class AddRentalCommand implements Runnable
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
        try
        {
            Console.rentalController.addRental(movieId, clientId, time);
        }
        catch (ProgramException e)
        {
            Console.handleException(e);
        }
    }
}
