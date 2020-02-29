package ro.sdi.lab24.view.commands.rental;

import picocli.CommandLine.Command;
import ro.sdi.lab24.exception.ProgramException;
import ro.sdi.lab24.view.Console;

@Command(name = "rental", description = "Manage rentals", subcommands = {
        AddRentalCommand.class,
        ListRentalsCommand.class,
        UpdateRentalCommand.class,
        DeleteRentalCommand.class,
},mixinStandardHelpOptions = true)
public class RentalCommand implements Runnable
{
    @Override
    public void run()
    {
            System.out.println("Use a subcommand!  Type 'rental --help'");
    }
}
