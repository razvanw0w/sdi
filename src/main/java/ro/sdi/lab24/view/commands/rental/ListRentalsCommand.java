package ro.sdi.lab24.view.commands.rental;

import picocli.CommandLine.Command;
import ro.sdi.lab24.validation.ProgramException;
import ro.sdi.lab24.view.Console;

@Command(description = "List rentals", name = "list")
public class ListRentalsCommand implements Runnable
{
    @Override
    public void run()
    {
        try
        {
            Console.rentalController.getRentals().forEach(
                    rental -> System.out.printf(
                            "%d %d %s",
                            rental.getId().getMovieId(),
                            rental.getId().getClientId(),
                            rental.getTime()
                    )
            );
        }
        catch (ProgramException e)
        {
            Console.handleException(e);
        }
    }
}
