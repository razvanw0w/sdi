package ro.sdi.lab24.view.commands.rental;

import picocli.CommandLine;

@CommandLine.Command(description = "Manage rentals", subcommands = {
        AddRentalCommand.class,
        ListRentalsCommand.class,
        UpdateRentalCommand.class,
        DeleteRentalCommand.class,
})
public class RentalCommand
{
}
