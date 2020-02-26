package ro.sdi.lab24.view.commands.rental;

import picocli.CommandLine.Command;
import ro.sdi.lab24.validation.ProgramException;
import ro.sdi.lab24.view.Console;

@Command(name = "rental", description = "Manage rentals", subcommands = {
        AddRentalCommand.class,
        ListRentalsCommand.class,
        UpdateRentalCommand.class,
        DeleteRentalCommand.class,
})
public class RentalCommand implements Runnable
{
    @Override
    public void run()
    {
        try
        {
            System.out.println("Use a subcommand!");
        }
        catch (ProgramException e)
        {
            Console.handleException(e);
        }
    }
}
