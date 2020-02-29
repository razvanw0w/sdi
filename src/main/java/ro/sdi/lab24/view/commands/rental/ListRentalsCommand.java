package ro.sdi.lab24.view.commands.rental;

import picocli.CommandLine.Command;
import ro.sdi.lab24.exception.ProgramException;
import ro.sdi.lab24.model.Rental;
import ro.sdi.lab24.view.Console;

@Command(description = "List rentals", name = "list")
public class ListRentalsCommand implements Runnable
{
    @Override
    public void run()
    {
        try
        {
            Iterable<Rental> rentals = Console.rentalController.getRentals();
            if (!rentals.iterator().hasNext())
            {
                System.out.println("No clients!");
            }
            rentals.forEach(
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
